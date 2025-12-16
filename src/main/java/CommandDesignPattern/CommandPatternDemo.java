package CommandDesignPattern;

public class CommandPatternDemo {

    public static void main(String[] args) {

        TextEditor editor = new TextEditor();
        CommandManager manager = new CommandManager();

        manager.execute(new WriteCommand(editor, "Hello "));
        manager.execute(new WriteCommand(editor, "World"));

        System.out.println(editor.getText()); // Hello World

        manager.undo();
        System.out.println(editor.getText()); // Hello

        manager.undo();
        System.out.println(editor.getText()); // ""

        manager.redo();
        System.out.println(editor.getText()); // Hello

        manager.redo();
        System.out.println(editor.getText()); // Hello World
    }
}

