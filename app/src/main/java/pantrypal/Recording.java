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

    public void startRecording() {
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
            // t.run();
            // try{
            //     t.interrupt();
            // }
            // catch (Exception e){
            //     e.printStackTrace();
            // }
            // targetDataLine.stop();
            // targetDataLine.close();
        }
    }

    public void stopRecording() {
        targetDataLine.stop();
        targetDataLine.close();
    }

    // public void buildByteOutputStream(final ByteArrayOutputStream out, final TargetDataLine line, int frameSizeInBytes, final int bufferLengthInBytes) throws IOException {
    //     final byte[] data = new byte[bufferLengthInBytes];
    //     int numBytesRead;
    //     line.start();
    //     while ()
    // }

    // public void convertToAudioIStream(ByteArrayOutputStream out, int frameSizeInBytes) {
    //     // byte[] audioBytes = out.toByteArray();
    //     // ByteArrayInputStream bais = new ByteArrayInputStream(audioBytes);
    //     // AudioInputStream audioInputStream = new AudioInputStream(bais, audioFormat, audioBytes.length / frameSizeInBytes);
    //     // return audioInputStream;
    // }


    // public void createRecording() {
    //     try {
    //         AudioFormat format = new AudioFormat(16000, 8, 2, true, true);
    //         DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
    //         if (!AudioSystem.isLineSupported(info)) {
    //             System.out.println("Line not supported");
    //             return;
    //         }
            
    //         TargetDataLine targetLine = (TargetDataLine) AudioSystem.getLine(info);
    //         targetLine.open(format, targetLine.getBufferSize());
    //         System.out.println("Starting recording...");
            
    //         int frameSizeInBytes = format.getFrameSize();
    //         int bufferLengthInFrames = targetLine.getBufferSize() / 8;
    //         final int bufferLengthInBytes = bufferLengthInFrames * frameSizeInBytes;
    //         final ByteArrayOutputStream out = new ByteArrayOutputStream(bufferLengthInBytes);
    //         buildByteOutputStream(out, targetLine, frameSizeInBytes, bufferLengthInBytes);
    //         this.AudioInputStream = new AudioInputStream(targetLine);
    //         setAudioInputStream(convertToAudioIStream(out, frameSizeInBytes));
    //         AudioInputStream.reset();

    //         // ASLKDJFA;


    //         this.AudioInputStream = new AudioInputStream(targetLine);
            
            
    //         targetLine.start();
            
            
    //         Thread thread = new Thread() {
    //             @Override
    //             public void run() {
    //                 AudioInputStream audioStream = new AudioInputStream(targetLine);
    //                 // override old recording
    //                 File originalFile = new File("Recording.wav");
    //                 if (originalFile.exists() && !originalFile.isDirectory()) {
    //                     originalFile.delete();
    //                 }
    //                 File wavFile = new File("Recording.wav");
    //                 try {
    //                     AudioSystem.write(audioStream, AudioFileFormat.Type.WAVE, wavFile);
    //                 } catch (IOException e) {
    //                     e.printStackTrace();
    //                 }

    //             }
    //         };
    //         thread.start();
    //         thread.run();
    //         Thread.sleep(5000);
    //         try {
    //             thread.interrupt();
    //         }
    //         catch (Exception e) {
    //             e.printStackTrace();
    //         }
    //         targetLine.stop();
    //         targetLine.close();
            
    //         System.out.println("Finished");
    //     } catch (InterruptedException e) {
    //         e.printStackTrace();
    //     } catch (LineUnavailableException e) {
    //         e.printStackTrace();
    //     }
    // }

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
