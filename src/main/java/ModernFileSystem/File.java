package ModernFileSystem;

class File implements Entry {
    private final String name;
    private final StringBuilder content = new StringBuilder();

    public File(String name) { this.name = name; }

    @Override
    public String getName() { return name; }
    @Override
    public boolean isFile() { return true; }

    public void addContent(String data) { content.append(data); }
    public String getContent() { return content.toString(); }
}