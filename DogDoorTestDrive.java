import java.util.Timer;
import java.util.TimerTask;

public class DogDoorTestDrive {
    public static void main(String[] args){
        DogDoor door = new DogDoor();
        Remote remote = new Remote(door);
        System.out.println("Fido barks to go outside.");
        System.out.println("...but he's stuck inside!");
        System.out.println("...so Todd grabs the remote control.");
        remote.pressButton();
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

    public DogDoor() {
        this.open = false;
    }

    public void open() {
        System.out.println("The dog door opens.");
        open = true;
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

        final Timer timer = new Timer();
        timer.schedule(new TimerTask(){
            public void run() {
                door.close();
                timer.cancel();
            }
        }, 5000);
    }
}