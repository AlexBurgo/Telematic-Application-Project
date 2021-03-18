

package upf.at.app.bot.utils;

//structure of telegram getupdates
public class Message {
    private Chat chat;
    private String text;

    public Message() {
    }

    public Message(Chat chat, String text) {
        this.chat = chat;
        this.text = text;
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    
    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    @Override
    public String toString() {
        return "Message [chat=" + chat + ", text=" + text + "]";
    }
}
