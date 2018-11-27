
import java.util.LinkedList;
import java.util.List;


public class Message {
    // 传递的自构造数据结构
    
    private String ask_one = "True";
    private int sender = 0;
    private int to = 0;
    private int message_id = 0;
    private String state = "None";
    private String timestamp = "";
    private List<String> schedule = new LinkedList<>();
    private double r;
    private int[] index;
    
    public Message(){}
    public Message(int sender, int to, double r, int[] index){
        this.sender = sender;
        this.to = to;
        this.r = r;
        this.index = index;
    }
    public int[] getIndex(){
        return index;
    }
    public void setR(double r){
        this.r = r;
    }
    public double getR(){
        return this.r;
    }
    public String getAsk_one() {
        return ask_one;
    }

    public void setAsk_one(String ask_one) {
        this.ask_one = ask_one;
    }

    public int getSender() {
        return sender;
    }

    public void setSender(int sender) {
        this.sender = sender;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public int getMessage_id() {
        return message_id;
    }

    public void setMessage_id(int message_id) {
        this.message_id = message_id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public List<String> getSchedule() {
        return schedule;
    }

    public void setSchedule(List<String> schedule) {
        this.schedule = schedule;
    }
    
}
