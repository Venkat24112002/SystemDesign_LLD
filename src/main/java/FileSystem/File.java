package FileSystem;

public class File implements FileSystem{
    private String name;

    public File(String name) {
        this.name = name;
    }

    public void display() {
        System.out.println("its a file" + name);
    }
}
