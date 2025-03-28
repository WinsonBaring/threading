class Consumer extends Thread {
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