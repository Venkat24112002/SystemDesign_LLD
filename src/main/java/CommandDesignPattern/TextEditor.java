package CommandDesignPattern;

//Reciever

public class TextEditor {

    private StringBuilder text = new StringBuilder();

    public void insert(String value){
        text.append(value);
    }

    public void delete(int length){
        int start = text.length()-length;
        text.delete(start, text.length());
    }

    public String getText() {
        return text.toString();
    }
}
