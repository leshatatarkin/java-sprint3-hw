public class Main {
    public static void main(String[] args) {

        Manager manager = new Manager();
        //создаем объекты
        manager.create(new Task("Таск 1", "описание Таск 1"));
        manager.create(new Task("Таск 2", "описание Таск 2"));

        manager.create(new Epic("Эпик 1", "описание Эпик 1"));
        manager.create(new Subtask("Сабтаск 1", "описание Сабтаск 1", 3));
        manager.create(new Subtask("Сабтаск 2", "описание Сабтаск 2", 3));

        manager.create(new Epic("Эпик 2", "описание Эпик 1"));
        manager.create(new Subtask("Сабтаск 1", "описание Сабтаск 1", 6));

        //печатаем все объекты
        manager.printAllTypeTask();
        //изменяем имя, описание, статус у Task с id 2
        manager.updateTask(new Task("Обновленное имя", "Обновленное описание", 2, "DONE"));
        //изменяем имя, описание у Epic с id 3
        manager.updateEpic(new Epic("Обновленное имя", "Обновленное описание", 3));
        //изменяем имя, описание, статус, Epic у Subtask с id 4
        manager.updateSubtask(new Subtask("Обновленное имя", "Обновленное описание", 4, "DONE", 3));
        //изменяем имя, описание, статус у Subtask с id 7
        manager.updateSubtask(new Subtask("Обновленное имя", "Обновленное описание", 7, "NEW", 6));
        //изменяем имя, описание, статус у Subtask с id 7
        manager.updateSubtask(new Subtask("Обновленное имя", "Обновленное описание", 5, "DONE", 6));
        //печатаем все объекты
        manager.printAllTypeTask();
        //удаляем Task с id 2
        manager.deleteById(2);
        //удаляем Epic с id 3
        manager.deleteById(3);
        //печатаем все объекты
        manager.printAllTypeTask();
    }
}
