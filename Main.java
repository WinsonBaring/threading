import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        SharedMemory sharedMemory = new SharedMemory();
        
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a message: ");
        String message = scanner.nextLine();
        
        Producer producer = new Producer(sharedMemory, message);
        Consumer consumer = new Consumer(sharedMemory);
        
        consumer.start();
        producer.start();

        scanner.close();
    }
}