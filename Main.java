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
    
    static class SharedMemory {
        private int buffer_size;
        private char[] buffer;
        private int in;
        private int out;
        private boolean isComplete;

        public SharedMemory() {
            buffer_size = 5;
            buffer = new char[buffer_size];
            in = 0;
            out = 0;
            isComplete = false;
        }

        public boolean isFull() {
            return ((in + 1) % buffer_size) == out;
        }

        public boolean isEmpty() {
            return in == out;
        }

        public void addChar(char c) {
            buffer[in] = c;
            in = (in + 1) % buffer_size;
        }

        public char getChar() {
            char c = buffer[out];
            out = (out + 1) % buffer_size;
            return c;
        }
        
        public void setComplete() {
            isComplete = true;
        }
        
        public boolean isComplete() {
            return isComplete && isEmpty();
        }
    }
    
    static class Producer extends Thread {
        private SharedMemory sharedMemory;
        private String message;

        public Producer(SharedMemory sm, String msg) {
            this.sharedMemory = sm;
            this.message = msg;
        }

        @Override
        public void run() {
            for (int i = 0; i < message.length(); i++) {
                while (sharedMemory.isFull()) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                sharedMemory.addChar(message.charAt(i));
                System.out.println("Producer added: " + message.charAt(i));
            }
            sharedMemory.setComplete();
            System.out.println("Producer finished");
        }
    }
    
    static class Consumer extends Thread {
        private SharedMemory sharedMemory;

        public Consumer(SharedMemory sm) {
            this.sharedMemory = sm;
        }

        @Override
        public void run() {
            while (true) {
                while (sharedMemory.isEmpty()) {
                    if (sharedMemory.isComplete()) {
                        System.out.println("\nConsumer finished");
                        return;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                char c = sharedMemory.getChar();
                System.out.print(c);
                System.out.flush();
                System.out.println("\nConsumer read: " + c);
            }
        }
    }
}