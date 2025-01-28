import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.util.Scanner;

public class main {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        taskManager taskManager = new taskManager();
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        int op;
        do {
            System.out.print("\n##-- Task Management Menu --##\n");
            System.out.print("|----------------------------|\n");
            System.out.print("| Option 1 - Add Task       |\n");
            System.out.print("| Option 2 - Delete Task    |\n");
            System.out.print("| Option 3 - List all tasks |\n");
            System.out.print("| Option 4 - Change Status  |\n");
            System.out.print("| Option 5 - List done tasks|\n");
            System.out.print("| Option 6 - List pending   |\n");
            System.out.print("| Option 7 - List finished  |\n");
            System.out.print("| Option 8 - Exit           |\n");
            System.out.print("|----------------------------|\n");
            System.out.print("Enter an option: ");

            op = sc.nextInt();
            sc.nextLine();
            switch (op) {
                case 1:
                    System.out.println("Enter a description for the new task:");
                    String description = sc.nextLine();
                    taskManager.addTask(description);
                    taskManager.sendToJson();
                    System.out.println("Task added successfully!");
                    break;

                case 2:
                    System.out.println("Enter the ID of the task to delete:");
                    String id = sc.nextLine();
                    taskManager.deleteTask(id);
                    taskManager.sendToJson();
                    System.out.println("Task deleted successfully!");
                    break;

                case 3:
                    System.out.println("Listing all tasks:");
                    taskManager.listAllTasks();
                    break;

                case 4:
                    System.out.println("Enter the ID of the task to edit:");
                    id = sc.nextLine();
                    System.out.println("Enter the new status for the task: [DONE, PENDING, FINISHED]");
                    String status = sc.nextLine();
                    taskManager.changeStatus(id, status);
                    taskManager.sendToJson();
                    break;

                case 5:
                    System.out.println("Listing all done tasks:");
                    taskManager.listAllDoneTask();
                    break;

                case 6:
                    System.out.println("Listing all pending tasks:");
                    taskManager.listPendingTasks();
                    break;

                case 7:
                    System.out.println("Listing all finished tasks:");
                    taskManager.listFinishedTasks();
                    break;
                case 8:
                    System.out.println("Exiting the system...");
                    break;

                default:
                    System.out.println("Invalid option! Please try again.");
            }
        } while (op != 8);

        sc.close();
    }
}
