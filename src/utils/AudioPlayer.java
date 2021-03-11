package utils;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioPlayer {

    private Clip clip;
    private final boolean isLoop;
    private long ClipTimePosition;

    public AudioPlayer(String path, Boolean isLoop) {
        this.isLoop = isLoop;
        try {
            AudioInputStream audio = AudioSystem.getAudioInputStream(new File(path));
            AudioFormat baseFormat = audio.getFormat();

            AudioFormat decodeFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, baseFormat.getSampleRate(), 16,
                    baseFormat.getChannels(), baseFormat.getChannels()*2, baseFormat.getSampleRate(), false);

            AudioInputStream decoded = AudioSystem.getAudioInputStream(decodeFormat, audio);

            clip = AudioSystem.getClip();
            clip.open(decoded);

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e){
            e.printStackTrace();
        }
    }

    public void play(){
        if(clip == null) return;
        if(isLoop){
            clip.loop(-1);
        }
        clip.start();
    }

    public void pause(){
        ClipTimePosition = clip.getMicrosecondPosition();
        clip.stop();
    }

    public void resume(){
        clip.setMicrosecondPosition(ClipTimePosition);
        if(isLoop){
            clip.loop(-1);
        }
        clip.start();
    }

    public void playWithStart(){
        if(clip == null) return;
        clip.setFramePosition(0);
        if(isLoop){
            clip.loop(-1);
        }
        clip.start();
    }

    public void reset(){
        clip.setFramePosition(0);
    }

    public void stop(){
        clip.stop();
    }

    public void setGain(float volume){
        if(clip.isControlSupported(FloatControl.Type.MASTER_GAIN)){
            FloatControl control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            control.setValue(volume);
        }
    }

    public void close(){
        stop();
        clip.close();
    }
}
