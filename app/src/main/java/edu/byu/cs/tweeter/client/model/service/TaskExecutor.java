package edu.byu.cs.tweeter.client.model.service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.byu.cs.tweeter.client.backgroundTask.BackgroundTask;

public class TaskExecutor {
    static void executeTask(BackgroundTask task) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(task);
    }
    
    static void executeTasks(List<BackgroundTask> tasks) {
        ExecutorService executor = Executors.newFixedThreadPool(tasks.size());
        for (BackgroundTask task : tasks) {
            executor.execute(task);
        }
    }
}
