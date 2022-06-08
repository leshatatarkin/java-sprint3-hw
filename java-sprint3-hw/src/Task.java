public class Task {
    protected String name;
    protected String description;
    protected int id;
    protected String status;

    public Task(String name, String description) { // конструктор при создании объекта (id генерируется первый свободный)
        this.name = name;
        this.description = description;
        this.status = "NEW";
    }

    public Task(String name, String description, int id) { // конструктор при обновлении объекта по id (без обновления status)
        this.name = name;
        this.description = description;
        this.id = id;
    }

    public Task(String name, String description, int id, String status) { // конструктор при обновлении объекта по id (id известен)
        this.name = name;
        this.description = description;
        this.status = status;
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
