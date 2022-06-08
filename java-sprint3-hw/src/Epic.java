import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Integer> idSubtask; // список id подзадач, которые входят в эпик

    public Epic(String name, String description) { // конструктор при создании объекта (id генерируется первый свободный)
        super(name, description);
        idSubtask = new ArrayList<>();
    }

    public Epic(String name, String description, int id) { // конструктор при обновлении объекта по id (id известен)
        super(name, description, id);
        idSubtask = new ArrayList<>();
    }

    public ArrayList<Integer> getIdSubtask() {
        return idSubtask;
    }

    public void setIdSubtask(ArrayList<Integer> idSubtask) {
        this.idSubtask = idSubtask;
    }

    public String getStatus() {
        return status;
    }
}
