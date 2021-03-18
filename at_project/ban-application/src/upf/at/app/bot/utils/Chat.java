

package upf.at.app.bot.utils;

//structure of telegram getUpdates
public class Chat{
    private int id;

    public Chat() {
    }

    public Chat(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Chat [id=" + id + "]";
    }   
}