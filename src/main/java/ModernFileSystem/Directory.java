package ModernFileSystem;

import java.util.*;

class Directory implements Entry {
    private final String name;
    private final Map<String, Entry> children = new HashMap<>();

    public Directory(String name) { this.name = name; }

    @Override
    public String getName() { return name; }
    @Override
    public boolean isFile() { return false; }

    public Map<String, Entry> getChildren() { return children; }

    // Sorted names for output
    public List<String> listNames() {
        List<String> names = new ArrayList<>(children.keySet());
        Collections.sort(names);
        return names;
    }
}