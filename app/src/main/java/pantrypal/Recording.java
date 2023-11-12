package pantrypal;

import javax.sound.sampled.*;

//import com.google.common.io.Files;

//import java.io.ByteArrayOutputStream;
import java.io.File;
//import java.io.IOException;
//import java.nio.file.Path;
//import java.nio.file.Paths;




public class Recording {

    private AudioFormat audioFormat;
    private TargetDataLine targetDataLine;

    private AudioFormat getAudioFormat() {
        // the number of samples of audio per second.
        // 44100 represents the typical sample rate for CD-quality audio.
        float sampleRate = 44100;

        // the number of bits in each sample of a sound that has been digitized.
        int sampleSizeInBits = 16;

        // the number of audio channels in this format (1 for mono, 2 for stereo).
        int channels = 2;

        // whether the data is signed or unsigned.
        boolean signed = true;

        // whether the audio data is stored in big-endian or little-endian order.
        boolean bigEndian = false;

        return new AudioFormat(
                sampleRate,
                sampleSizeInBits,
                channels,
                signed,
                bigEndian);
    }

    private void startRecording() {
        {
            Thread t = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            // the format of the TargetDataLine
                            DataLine.Info dataLineInfo = new DataLine.Info(
                                    TargetDataLine.class,
                                    audioFormat);
                            // the TargetDataLine used to capture audio data from the microphone
                            targetDataLine = (TargetDataLine) AudioSystem.getLine(dataLineInfo);
                            targetDataLine.open(audioFormat);
                            targetDataLine.start();

                            // the AudioInputStream that will be used to write the audio data to a file
                            AudioInputStream audioInputStream = new AudioInputStream(
                                    targetDataLine);

                            // the file that will contain the audio data
                            File originalFile = new File("Recording.wav");
                            if (originalFile.exists() && !originalFile.isDirectory()) {
                                originalFile.delete();
                            }
                            File audioFile = new File("Recording.wav");
                            AudioSystem.write(
                                    audioInputStream,
                                    AudioFileFormat.Type.WAVE,
                                    audioFile);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                }
                
            );
            t.start();

        }
    }

    private void stopRecording() {
        targetDataLine.stop();
        targetDataLine.close();
    }

    public void createRecording() {
        audioFormat = this.getAudioFormat();
        this.startRecording();
        try {
            Thread.sleep(5000);
        }
        catch (Exception e) {
            System.out.println("??");
        }
        this.stopRecording();
    }
}
