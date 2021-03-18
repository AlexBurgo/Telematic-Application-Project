

package upf.at.app.bot.utils;
//part of the structure used to read from getUpdates in telegram api
public class Update {
    private long update_id;
    private Message message;


    public Update() {
    }

    public Update(long update_id, Message message) {
        this.update_id = update_id;
        this.message = message;
    }

    public long getUpdate_id() {
        return update_id;
    }

    public void setUpdate_id(long update_id) {
        this.update_id = update_id;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Update [message=" + message + ", update_id=" + update_id + "]";
    }

    
}
