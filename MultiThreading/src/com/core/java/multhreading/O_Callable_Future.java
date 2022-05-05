package com.core.java.multhreading;

import java.util.Random;
import java.util.concurrent.*;

class AppCF{
    private String name;
    private int id;

    public AppCF(){
        name = "Manoj Paul";
        id = 10032;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AppCF(String name, int id) {
        this.name = name;
        this.id = id;
    }
}
public class O_Callable_Future {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();

        Future<AppCF> future = executorService.submit(new Callable<AppCF>() {
            @Override
            public AppCF call() throws Exception {
                Random random = new Random();
                int duration = random.nextInt(5000);
                if (duration>2000)
                    throw new Exception("Sleeping for too long.....");
                System.out.println("Starting..........");
                Thread.sleep(duration);
                System.out.println("Completed..........");

                AppCF appCF = new AppCF("Sanatani Kapoor", 10909);
                return appCF;
            }
        });
        executorService.shutdown();
        executorService.awaitTermination(1,TimeUnit.HOURS);

        try {
            System.out.println("The Name is: "+future.get().getName()+", id:: "+future.get().getId());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            Exception ex = (Exception) e.getCause();
            System.out.println(ex.getMessage());;
        }
    }
}
