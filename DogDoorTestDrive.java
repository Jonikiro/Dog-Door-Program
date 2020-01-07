import java.util.Timer;
import java.util.TimerTask;

public class DogDoorTestDrive {
    public static void main(String[] args){
        DogDoor door = new DogDoor();
        Remote remote = new Remote(door);
        BarkRecognizer recognizer = new BarkRecognizer(door);
        System.out.println("Fido barks to go outside.");
        recognizer.recognize("bark");
        System.out.println("\nFido has gone outside...");
        System.out.println("\nFido's all done...");

        try {
            Thread.currentThread();
            Thread.sleep(10000);
        } catch (InterruptedException e) {}
        
        System.out.println("Fido scratches at the door.");
        System.out.println("...but he's stuck outside!");
        System.out.println("...so Gina grabs the remote control.");
        remote.pressButton();
        System.out.println("\nFido's back inside...");
    }
}

class DogDoor {
    private boolean open;
    private int closingTime = 5000;

    public DogDoor() {
        this.open = false;
    }

    public void setClosingTime(int closingTime) {
        this.closingTime = closingTime;
    }

    public void open() {
        System.out.println("The dog door opens.");
        open = true;

        final Timer timer = new Timer();
        timer.schedule(new TimerTask(){
            public void run() {
                close();
                timer.cancel();
            }
        }, closingTime);
    }

    public void close() {
        System.out.println("The dog door closes.");
        open = false;
    }

    public boolean isOpen() {
        return open;
    }
}

class Remote {
    private DogDoor door;

    public Remote(DogDoor door){
        this.door = door;
    }

    public void pressButton() {
        System.out.println("Pressing the remote control button...");
        if (door.isOpen()) {
            door.close();
        } else {
            door.open();
        }
    }
}

class BarkRecognizer {
    private DogDoor door;

    public BarkRecognizer(DogDoor door) {
        this.door = door;
    }

    public void recognize(String bark) {
        System.out.println("  BarkRecognizer: Heard a \'" + bark + "\'");
        door.open();
    }
}