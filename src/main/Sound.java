package main;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound {
    Clip clip;
    FloatControl fc;
    int volumeScale = 3;
    float volume;
final String relative="res/sound/";
final String absolute ="C:/Users/phare/Desktop/Java2D/res/sound/";

    public Sound() {}

    public void setFile(String path) {
        
        try {
            File filePath = new File(relative+path);
            File absolutefilePath = new File(absolute+path);
            if (filePath.exists()) {
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(filePath);
            clip = AudioSystem.getClip();
            clip.open(audioInput);
            fc = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
            checkVolume();
        
            }
            else if (absolutefilePath.exists()){
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(absolutefilePath);
                clip = AudioSystem.getClip();
                clip.open(audioInput);
                fc = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
                checkVolume();
            }else {
                System.out.println("path don't exists");
            }

            
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void play() {
        clip.start();
    }
    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stop() {
        clip.stop();
        clip.close();
    }
    public void checkVolume() {
        switch(volumeScale) {
            case 0: volume = -80f; break;
            case 1: volume = -20f; break;
            case 2: volume = -12f; break;
            case 3: volume = -5f; break;
            case 4: volume = 1f; break;
            case 5: volume = 6f; break;
        }
        fc.setValue(volume);
    }
}

