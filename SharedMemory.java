class SharedMemory {
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