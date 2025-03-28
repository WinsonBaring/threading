import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        SharedMemory sharedMemory = new SharedMemory();
        
        Scanner scanner = new Scanner(System.in);
        System.out.println("Producer-Consumer Simulation");
        System.out.print("Enter a message: ");
        String message = scanner.nextLine();
        
        Producer producer = new Producer(sharedMemory, message);
        Consumer consumer = new Consumer(sharedMemory);
        
        System.out.println("Starting threads...");
        consumer.start();
        producer.start();
        
        // Wait for threads to complete
        try {
            producer.join();
            consumer.join();
            System.out.println("Simulation completed successfully.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        scanner.close();
    }
}