class Consumer extends Thread {
    private SharedMemory sharedMemory;

    public Consumer(SharedMemory sm) {
        this.sharedMemory = sm;
    }

    @Override
    public void run() {
        while (true) {
            while (sharedMemory.isEmpty()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.print(sharedMemory.getChar());
            System.out.flush();
        }
    }
}