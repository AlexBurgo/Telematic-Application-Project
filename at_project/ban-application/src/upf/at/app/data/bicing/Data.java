

package upf.at.app.data.bicing;

//data structure, where the list of stations is stored
public class Data {
    private Stations data;
    
    public Data() {
    }
    
    public Data(Stations data) {
        this.data = data;
    }

    public Stations getData() {
        return data;
    }

    public void setData(Stations data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Data [data=" + data + "]";
    }   
}