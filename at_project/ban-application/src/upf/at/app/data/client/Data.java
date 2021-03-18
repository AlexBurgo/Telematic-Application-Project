

package upf.at.app.data.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.List;
//Class that holds the list of clients
public class Data {
    private List<Client> clients;
    private File storage; //File where we store subscribed clients

    public Data() {
        try {
            boolean created = createStorage(); //try to create the storage
            if(!created){ //if it was already created, we try to pull the information
                FileInputStream fi = new FileInputStream(storage);
                ObjectInputStream oi = new ObjectInputStream(fi);

                clients = (LinkedList<Client>) oi.readObject();

                oi.close();
                fi.close();
            } else { //if it was newly created, there is no need to read since there is nothing, 
            		//we create a new linkedlist
                clients = new LinkedList<>();
            }
        } catch (Exception e) {
            System.out.println("Error in clients storage file");
            clients = new LinkedList<>(); //in case of error, guarantee a list to store the clients
        }

    }

    public Data(List<Client> clients) {//constructor
        this.clients = clients;
    }

    public boolean createStorage() throws Exception{ //attemps to create a file in the desired locations
        boolean created;
        String path = this.getClass().getResource("/").getPath(); //classes folder path
        File folder = new File(path + "/upf/at/app/data/client/storage");//path to subdirectory on client package
        folder.mkdir();//create folder
        storage = new File(folder.getPath() + "/subcriptions.txt");//file to create
        try {
            created = storage.createNewFile(); //create file
        } catch (Exception e) {
            throw e; //throw the exception so the call in constructor crashes and goes to the catch, creating a new list
        }
        return created; //return if it was created or there was already one
    }

    public void addClient(Client c) { //add client to the list
        clients.add(c);	//add to the list
        updateStorage(); //update the storage in text file
    }

    public void updateStorage(){ //update the storage in text file
        try {
            FileOutputStream fo = new FileOutputStream(storage); //load the storage path, got in createStorage
            ObjectOutputStream oo = new ObjectOutputStream(fo);//prepare to write on it
            oo.writeObject(this.clients);//write the entire linkedlist, this guarantees that the updates are also stored
            oo.close();//close
            fo.close();//close
        } catch (Exception e) {
            System.out.println(e.getMessage());//crashed, print message
        }
    }

    public List<Client> getClients() { //getter for clients
        return clients;
    }

    @Override
    public String toString() { //tostring
        return "Data [clients=" + clients + "]";
    }

}