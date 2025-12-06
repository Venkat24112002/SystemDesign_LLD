package FileSystem;

import java.util.ArrayList;
import java.util.List;

public class Directory implements FileSystem{

    private String directoryName;
    private List<FileSystem> fileSystemList;

    public Directory(String name) {
        this.directoryName = name;
        fileSystemList = new ArrayList<>();
    }

    public void add(FileSystem fileSystemObj) {
        fileSystemList.add(fileSystemObj);
    }

    public void display() {
        System.out.println("Directory name" + directoryName);

        for(FileSystem FileOrDirectory : fileSystemList) {
            FileOrDirectory.display();
        }
    }
}
