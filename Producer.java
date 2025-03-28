class Producer extends Thread {
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