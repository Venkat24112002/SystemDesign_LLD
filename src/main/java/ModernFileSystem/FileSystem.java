package ModernFileSystem;//package net.engineeringdigest.journalApp.CompositeDesignPattern.ModernFileSystem;
//
//import java.util.Collections;
//import java.util.List;
//
//public class FileSystem {
//    // The root directory ("/")
//    private final Directory root;
//
//    /**
//     * Constructor: initializes the root directory
//     */
//    public FileSystem() {
//        root = new Directory(""); // root's name is "", representing "/"
//    }
//
//    /**
//     * Helper method to traverse to the Entry at the given path.
//     * @param path       The file or directory path (e.g., "/a/b/c.txt")
//     * @param createDirs If true, create directories if missing on the way (used by mkdir/addFile)
//     * @return The Entry at the end of the path (File or Directory); null if not found and createDirs == false
//     */
//    private Entry traverse(String path, boolean createDirs) {
//        if (path.equals("/")) return root;
//        String[] parts = path.split("/");
//        Entry curr = root;
//        // Start from index 1 because parts[0] is "" (split by leading "/")
//        for (int i = 1; i < parts.length; i++) {
//            if (parts[i].isEmpty()) continue; // skip empty segments, e.g., due to "//"
//            if (curr.isFile()) // Already at a file? Cannot go deeper.
//                throw new IllegalArgumentException("Not a directory: " + parts[i-1]);
//            Directory dir = (Directory) curr;
//            Entry next = dir.getChildren().get(parts[i]);
//            if (next == null) {
//                if (createDirs) {
//                    // Create directory if it doesn't exist
//                    next = new Directory(parts[i]);
//                    dir.getChildren().put(parts[i], next);
//                } else {
//                    // Path does not exist
//                    return null;
//                }
//            }
//            curr = next;
//        }
//        return curr;
//    }
//
//    /**
//     * List file or directory:
//     * - If path is a file: returns a list with its name.
//     * - If path is a directory: returns a sorted list of all names inside that directory.
//     */
//    public List<String> ls(String path) {
//        Entry e = traverse(path, false);
//        if (e == null) return Collections.emptyList();
//        if (e.isFile()) return List.of(e.getName());
//        return ((Directory)e).listNames();
//    }
//
//    /**
//     * Creates a directory and all missing parent directories as necessary.
//     * (Does nothing if already exists)
//     */
//    public void mkdir(String path) {
//        traverse(path, true);
//    }
//
//    /**
//     * Creates a file at filePath (overwriting if exists) and writes `content` to it.
//     * Ensures that all leading directories are created as needed.
//     */
//    public void addFile(String filePath, String content) {
//        // Extract parent directory path and file name
//        int slash = filePath.lastIndexOf('/');
//        String dirPath = (slash <= 0) ? "/" : filePath.substring(0, slash); // "/a/b/c.txt" => "/a/b"
//        String name = filePath.substring(slash + 1); // "c.txt"
//        // Traverse to parent directory, creating if missing
//        Directory dir = (Directory) traverse(dirPath, true);
//        File file = new File(name);
//        file.addContent(content); // Write content
//        dir.getChildren().put(name, file); // Add/overwrite file in parent dir
//    }
//
//    /**
//     * Appends content to an existing file. Throws error if path is not a file.
//     */
//    public void appendFile(String filePath, String content) {
//        Entry e = traverse(filePath, false);
//        if (e instanceof File) {
//            ((File) e).addContent(content);
//        } else {
//            throw new IllegalArgumentException("Not a file: " + filePath);
//        }
//    }
//
//    /**
//     * Returns the entire contents of a file at filePath.
//     * Throws error if path is not a file.
//     */
//    public String readFile(String filePath) {
//        Entry e = traverse(filePath, false);
//        if (e instanceof File) {
//            return ((File) e).getContent();
//        }
//        throw new IllegalArgumentException("Not a file: " + filePath);
//    }
//
//    /**
//     * Prints the directory/file tree recursively from root.
//     * [For debugging or visual understanding]
//     */
//    public void printTree() { printTree(root, ""); }
//    private void printTree(Entry node, String indent) {
//        if (node.isFile()) {
//            System.out.println(indent + "[F] " + node.getName());
//        } else {
//            System.out.println(indent + "[D] " + (node.getName().isEmpty() ? "/" : node.getName()));
//            for (String child : ((Directory) node).listNames()) {
//                printTree(((Directory) node).getChildren().get(child), indent + "  ");
//            }
//        }
//    }
//
//    /**
//     * DFS: Finds the first file (anywhere under root) with the given name.
//     * Returns File if exists; otherwise null.
//     */
//    public File getFileIfExists(String name) {
//        return getFileIfExists(root, name);
//    }
//    private File getFileIfExists(Entry node, String name) {
//        if (node.isFile()) {
//            return node.getName().equals(name) ? (File) node : null;
//        } else {
//            for (Entry child : ((Directory) node).getChildren().values()) {
//                File found = getFileIfExists(child, name);
//                if (found != null) return found;
//            }
//            return null;
//        }
//    }
//
//    // Extension Points: delete, move, copy, permissions, etc.
//}
