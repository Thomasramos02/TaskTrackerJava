import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class taskManager{
   private List<task> tasks;
   ObjectMapper mapper;

   public taskManager(){
       mapper = new ObjectMapper();   //construtor de taskManager com instancia de ObjectMapper, configurações do LocalDataTime do JACKSON DATA BIND(biblioteca)
       mapper.registerModule(new JavaTimeModule());
       mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
       tasks = LoadToJson();
   }
   public void sendToJson(){    //metodo que envia os dados para o Json
       try{
           mapper.writeValue(new File("target/task.json"), tasks);
       } catch (Exception e) {
           e.printStackTrace();
       }
   }

   private List<task> LoadToJson(){ //metodo que faz um Load(leitura) no Json
       try{
           File file = new File("target/task.json");
           if(file.exists()){
               return mapper.readValue(file, new TypeReference<List<task>>(){});
           }
       } catch (Exception e) {
           e.printStackTrace();
       }
       return new ArrayList<>();
   }

    public void addTask(String description) {  //metodo que adiciona as tarefas gerando um id novo a cada tarefa criada, status padrão de "pending"
        Scanner scanner = new Scanner(System.in);
        String newId = String.valueOf(tasks.size() + 1);
        String defaultStatus = "pending";
        task task = new task(newId,description,defaultStatus);

        tasks.add(task);
        sendToJson();
        System.out.println("Task added successfully: " + task.getDescription());
    }

    public void updateTask(String description, String status) { //metodo para atualizar a tarefa ja criada encontrando a por meio do ID
       String newId = String.valueOf(tasks.size() + 1);
       task task = new task(newId,description,status);
       tasks.add(task);
       LoadToJson();
       System.out.println("Task updated successfully: " + task.getDescription() + task.getStatus());
   }
   public void deleteTask(String id) throws IOException { // metodo que deleta a tarefa encontrando a pelo o id
       task taskToRemove = null;
       for( task t : tasks){
           if(t.getId().equals(id)){
               taskToRemove = t;
           }
       }
       if(taskToRemove != null){
           tasks.remove(taskToRemove);
           sendToJson();
       }
       System.out.println("A task with id " + id + " was removed.");
   }

   private task findTaskById(String id){ // metodo que encontra a tarefa pelo o Id
      return tasks.stream().filter(t -> t.getId().equals(id)).findFirst().orElse(null);

   }

   public void changeStatus(String id, String status) { //metodo que altera o status da tarefa ja criada
      task taskToChange = findTaskById(id);
      taskToChange.setStatus(status);
      sendToJson();
      System.out.println("Task status changed to " + taskToChange.getStatus());
   }

   public void listAllTasks(){ //metodo quie lista todas as tarefas
       task allTasks = new task();
       for(task t : tasks){
           LoadToJson();
           System.out.println(t);
       }
   }
   public void listAllDoneTask(){ // metodo que lista todas as tarefaas com status done
       tasks.stream().filter(task ->"done".equals(task.getStatus())).forEach(System.out::println);

   }

   public void listPendingTasks(){ //metodo que lista todas as tarefas com status pending
       tasks.stream().filter(task ->"pending".equals(task.getStatus())).forEach(System.out::println);
   }

   public void listFinishedTasks() { //meotodo que lista todas as tarefas com status finished
       tasks.stream().filter(task -> "finished".equals(task.getStatus())).forEach(System.out::println);
   }
}

