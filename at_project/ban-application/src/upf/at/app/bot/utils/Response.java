

package upf.at.app.bot.utils;

import java.util.List;

//getUpdates API telegram structure
public class Response {
    private List<Update> result;

    public Response() {
    }

    public Response(List<Update> result) {
        this.result = result;
    }

    public List<Update> getResult() {
        return result;
    }

    public void setResult(List<Update> result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "Response [result=" + result + "]";
    }
    
}