package CommandDesignPattern;

public class DeleteCommand implements Command{

    private final TextEditor editor;
    private String deletedText;

    public DeleteCommand(TextEditor editor, int length) {
        this.editor = editor;
        this.deletedText = editor.getText()
                .substring(editor.getText().length() - length);
    }

    @Override
    public void execute() {
        editor.delete(deletedText.length());
    }

    @Override
    public void undo() {
        editor.insert(deletedText);
    }
}
