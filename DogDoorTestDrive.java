import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

public class DogDoorTestDrive {
    public static void main(String[] args){
        DogDoor door = new DogDoor();
        door.addAllowedBark(new Bark("rowlf"));
        door.addAllowedBark(new Bark("rooowlf"));
        door.addAllowedBark(new Bark("rawlf"));
        door.addAllowedBark(new Bark("woof"));
        BarkRecognizer recognizer = new BarkRecognizer(door);
        Remote remote = new Remote(door);

        System.out.println("The owner's dog starts barking.");
        recognizer.recognize(new Bark("rowlf"));

        System.out.println("The owner's dog has gone outside...");

        try {
            Thread.currentThread();
            Thread.sleep(10000);
        } catch (InterruptedException e) {}

        System.out.println("The owner's dog is all done...");
        System.out.println("...but the dog is stuck outside!");

        Bark smallDogBark = new Bark("yip");
        System.out.println("A small dog starts barking nearby.");
        recognizer.recognize(smallDogBark);

        try {
            Thread.currentThread();
            Thread.sleep(10000);
        } catch (InterruptedException e) {}

        System.out.println("The owner's dog starts barking again.");
        recognizer.recognize(new Bark("rooowlf"));

        System.out.println("The owner's dog is back inside...");
    }
}

class DogDoor {
    private boolean open;
    private ArrayList<Bark> allowedBarks = new ArrayList<Bark>();
    private int closingTime = 5000;

    public DogDoor() {
        this.open = false;
    }

    public void setClosingTime(int closingTime) {
        this.closingTime = closingTime;
    }

    public void addAllowedBark(Bark allowedBarks) {
        this.allowedBarks.add(allowedBarks);
    }

    public ArrayList<Bark> getAllowedBarks() {
        return allowedBarks;
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

    public void recognize(Bark bark) {
        System.out.println("    BarkRecognizer: Heard a \'" + bark.getSound() + "\'");
        ArrayList<Bark> allowedBarks = door.getAllowedBarks();
        for (Iterator<Bark> i = allowedBarks.iterator(); i.hasNext();) {
            Bark allowedBark = i.next();
            if (allowedBark.equals(bark)) {
                System.out.println("Bark matches one of those registered with this door.");
                door.open();
                return;
            }
        }
        System.out.println("This dog is not allowed.");
    }
}

class Bark {
    private String sound;

    public Bark(String sound) {
        this.sound = sound;
    }

    public String getSound() {
        return sound;
    }

    public boolean equals(Object bark) {
        if (bark instanceof Bark) {
            Bark otherBark = (Bark) bark;
            if (this.sound.equalsIgnoreCase(otherBark.sound)){
                return true;
            }
        }
        return false;
    }
}