import javazoom.jl.player.*;

public class MyAudioPlayer extends Thread {

    private String fileLocation;
    private boolean loop;
    private Player prehravac;

    public MyAudioPlayer(String fileLocation, boolean loop) {
        this.fileLocation = fileLocation;
        this.loop = loop;
    }

    public void run() {

        try {
            do {
                prehravac = new Player(getClass().getResource(fileLocation).openStream());
                prehravac.play();
            } while (loop);
        } catch (Exception ioe) {
            System.out.println(ioe);
        }
    }

    public void close(){
        loop = false;
        prehravac.close();
        this.interrupt();
    }
}