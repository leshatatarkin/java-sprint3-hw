import java.util.HashMap;

public class Manager {
    private int countId; // количество задач всего (кол-во id)
    HashMap<Integer, Task> Tasks; // коллекция задач
    HashMap<Integer, Epic> Epics; // коллекция эпиков
    HashMap<Integer, Subtask> Subtasks; // коллекция подзадач

    public Manager() {
        this.countId = 0;
        Tasks = new HashMap<>();
        Epics = new HashMap<>();
        Subtasks = new HashMap<>();
    }

    public int getCountId() {
        return countId;
    }

    private void setCountId(int countId) {
        this.countId = countId;
    }

    public void create(Object obj) { // 2.4 Создание
        String status = "NEW"; // при создании у всех типов задач статут по умолчанию "NEW"
        int id = getCountId(); // получаем крайний id (занятый)
        id++; // задаем новый id

        // Костыль :)
        String nameClass = String.valueOf(obj.getClass()); // получаем имя класса объекта obj
        /* Вопрос к ревьюеру: как правильно обработать класс передаваемого объекта, чтобы можно было
         * заполнить HashMap, исходя из типа объекта (Task, Epic, Subtask) и не колхозить в условиях ниже:
         *  "class Task", "class Epic"  и т.д. , потому что метод getClass() возвращает значение вида:
         * "class НазваниеКласса". Через instanceof не получается, т.к. Epic и Subtask наследуют от Task*/
        if (nameClass.equals("class Task")) {
            Task task = (Task) obj;
            task.id = id;
            Tasks.put(id, task);
        } else if (nameClass.equals("class Epic")) {
            Epic epic = (Epic) obj;
            epic.id = id;
            Epics.put(id, epic);
        } else if (nameClass.equals("class Subtask")) {
            Subtask subtask = (Subtask) obj;
            subtask.id = id;
            // вызов функции для внесения id сабтаска, в список сабтасков его эпика
            if (!Epics.containsKey(subtask.getIdEpic())) { // проверяем наличие Epic с введенным id
                System.out.println("Epic с id " + subtask.getIdEpic() + " не существует! Subtask не создан!");
                return;
            }
            addIdSubtaskToEpics(id, subtask.getIdEpic());
            Subtasks.put(id, subtask); //  добавляем сабтаск
            checkStatusSubtask(subtask.getIdEpic()); // обновляем статус у родительского эпика
        }
        setCountId(id); // устанавливаем новое значение переменной countId (после создания нового объекта)
    }

    public void printAllTypeTask() { // 2.1 Получение (вывод) всех задач
        printTask();
        printEpic();
        printSubtask();
    }

    public void updateSubtask(Subtask subtask) { // 2.5 Обновление Subtask
        if (Subtasks.containsKey(subtask.getId()) && Epics.containsKey(subtask.getIdEpic())) {
            Subtask oldSubtask = Subtasks.get(subtask.getId());
            Subtasks.put(subtask.getId(), subtask);
            addIdSubtaskToEpics(subtask.getId(), subtask.getIdEpic()); // добавляем сабтакс в список нового эпика
            deleteIdSubtaskFromEpics(subtask.getId(), oldSubtask.getIdEpic()); // удаляем сабтакс из списка старого эпика
            checkStatusSubtask(subtask.getIdEpic()); // обновляем статус у нового эпика
            checkStatusSubtask(oldSubtask.getIdEpic()); // обновляем статус у старого эпика
        } else {
            System.out.println("Ошибка обновления! Данные не сохранены! id Эпика / id Сабтаска не существует!");
        }
    }

    public void updateEpic(Epic epic) { // 2.5 Обновление Epic
        if (Epics.containsKey(epic.getId())) {
            Epic oldEpic = Epics.get(epic.getId());
            epic.setIdSubtask(oldEpic.getIdSubtask()); // сохраняем старый список сабтасков у эпика
            Epics.put(epic.getId(), epic);
            checkStatusSubtask(epic.getId()); // обновляем статус у  эпика
        } else {
            System.out.println("Ошибка обновления! Данные у задачи №" + epic.getId() + " не сохранены! id Эпика не существует!");
        }
    }

    public void updateTask(Task task) { // 2.5 Обновление Task
        if (Tasks.containsKey(task.getId())) {
            Tasks.put(task.getId(), task);
        } else {
            System.out.println("Ошибка обновления! Данные у задачи №" + task.getId() + " не сохранены! id Таска не существует!");
        }
    }

    public void deleteAll(String typeOfTask) { // 2.2 Удаление всех задач
        switch (typeOfTask) {
            case ("Task"):
                Tasks.clear();
                break;
            case ("Epic"):
                Epics.clear();
                break;
            case ("Subtask"):
                Subtasks.clear();
                break;
            default:
                System.out.println("Ошибка удаления! Такого типа не существует. Выберете из Task / Epic / Subtask");
                return;
        }
    }

    public Object getById(int id) { // 2.3 Получение по идентификатору
        if (Tasks.containsKey(id)) { // проверка на наличие переданного ключа
            for (Integer key : Tasks.keySet()) {
                if (key == id) {
                    System.out.print(Tasks.get(key).id + " ");
                    System.out.print(Tasks.get(key).name + " ");
                    System.out.print(Tasks.get(key).status + " ");
                    System.out.println();
                    return Tasks.get(key);
                }
            }
        } else if (Epics.containsKey(id)) {
            for (Integer key : Epics.keySet()) {
                if (key == id) {
                    System.out.print(Epics.get(key).id + " ");
                    System.out.print(Epics.get(key).name + " ");
                    System.out.print(Epics.get(key).status + " ");
                    System.out.println();
                    return Epics.get(key);
                }
            }
        } else if (Subtasks.containsKey(id)) {
            for (Integer key : Subtasks.keySet()) {
                if (key == id) {
                    System.out.print(Subtasks.get(key).id + " ");
                    System.out.print(Subtasks.get(key).name + " ");
                    System.out.print(Subtasks.get(key).status + " ");
                    System.out.println();
                    return Subtasks.get(key);
                }
            }
        }
        return null;
    }

    public void deleteById(int id) { // 2.6 Удаление по идентификатору
        if (Tasks.containsKey(id)) {
            Tasks.remove(id);
        } else if (Epics.containsKey(id)) {
            //сначала удаляем все сабтаски эпика
            Epic epic = Epics.get(id);
            for (int i = 0; i < epic.getIdSubtask().size(); i++) {
                deleteById(epic.getIdSubtask().get(i));
            }
            //удаляем сам эпик
            Epics.remove(id);
        } else if (Subtasks.containsKey(id)) {
            Subtasks.remove(id);
        } else {
            System.out.println("Ошибка удаления! Такого id не существует!");
        }
    }

    public void printSubtaskByIdEpic(int idEpic) { // 3.1 Получение списка всех подзадач определённого эпика
        System.out.println("Все Subtask для Epic'a №" + idEpic + ":");
        for (Subtask value : Subtasks.values()) {
            if (value.getIdEpic() == idEpic) {
                System.out.print(value.id + " \t");
                System.out.print(value.name + " \t");
                System.out.print(value.status + " \t");
                System.out.print(value.getIdEpic() + " \t");
                System.out.println();
            }
        }
    }

    private void addIdSubtaskToEpics(int idSubtask, int idEpic) { // добавление id сабтаска в список сабтасков эпика
        Epic epic = Epics.get(idEpic); // получаем объект Epic с указанным id
        epic.getIdSubtask().add(idSubtask); // добавляем id сабтаска в ArrayList Epic'а
    }

    private void deleteIdSubtaskFromEpics(int idSubtask, int idEpic) {
        Epic epic = Epics.get(idEpic); // получаем объект Epic с указанным id
        int position = 0;
        for (Integer valueId : epic.getIdSubtask()) {
            if (valueId == idSubtask) {
                break;
            }
            position++;
        }
        epic.getIdSubtask().remove(position);

    }

    private void printTask() { // 2.1 Получение всех задач (Task)
        System.out.println("***********Task**********");
        System.out.println("=========================");
        System.out.println("id/name/status");
        System.out.println("=========================");
        for (Task value : Tasks.values()) {
            System.out.print(value.id + " \t");
            System.out.print(value.name + " \t");
            System.out.print(value.status + " \t");
            System.out.println();
        }
        System.out.println();
    }

    private void printSubtask() { // 2.1 Получение всех задач (Subtask)
        System.out.println("*********Subtask*********");
        System.out.println("=========================");
        System.out.println("id/name/status/idEpic");
        System.out.println("=========================");
        for (Subtask value : Subtasks.values()) {
            System.out.print(value.id + " \t");
            System.out.print(value.name + " \t");
            System.out.print(value.status + " \t");
            System.out.print(value.getIdEpic() + " \t");
            System.out.println();
        }
        System.out.println();
    }

    private void printEpic() { // 2.1 Получение всех задач (Epic)
        System.out.println("***********Epic**********");
        System.out.println("=========================");
        System.out.println("id/name/status/idSubtasks");
        System.out.println("=========================");
        for (Epic value : Epics.values()) {
            System.out.print(value.id + " \t");
            System.out.print(value.name + " \t");
            System.out.print(value.status + " \t");
            System.out.print(value.getIdSubtask() + " \t");
            System.out.println();
        }
        System.out.println();
    }

    private void checkStatusSubtask(int idEpic) { // проверяем статусы всех сабтасков при обновлении каждого из них и меняем у эпика
        int countNew = 0;
        int countDone = 0;
        int countSubtasksOfEpic = 0;
        Epic epic = Epics.get(idEpic); // берем из HashMap Epic с необходимым id
        for (Subtask value : Subtasks.values()) {
            if (value.getIdEpic() == idEpic) {
                countSubtasksOfEpic++; // считаем количество сабтасков у определенного эпика
                if (value.status.equalsIgnoreCase("DONE")) {
                    countDone++;
                } else if (value.status.equalsIgnoreCase("NEW")) {
                    countNew++;
                }

            }
        }
        // меняем статус у родительского эпика в зависимости от статусов его сабтасков
        if (countDone == countSubtasksOfEpic) { // done
            epic.status = "DONE";
        } else if (countNew == countSubtasksOfEpic) { // new
            epic.status = "NEW";
        } else {
            epic.status = "IN_PROGRESS";
        }
    }


}
