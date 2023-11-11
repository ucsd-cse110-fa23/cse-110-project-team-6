package ppal;

import javax.sound.sampled.*;

public class Recording {
    public static void main(String[] args) {
        try {
            AudioFormat format = new AudioFormat(16000, 8, 2, true, true);
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
            if (!AudioSystem.isLineSupported(info)) {
                System.out.println("Line not supported");
            }
            TargetDataLine targetLine = (TargetDataLine) AudioSystem.getLine(info);
            targetLine.open();
            //System.out.println("Starting recording...");
            targetLine.start();
            Thread thread = new Thread() {
                @Override
                public void run() {
                    AudioInputStream audioStream = new AudioInputStream(targetLine);
                    Path originalFile = Paths.get("Recording.wav");
                    Files.delete(originalFile);
                    File wavFile = new File("Recording.wav");
                    try {
                        AudioSystem.write(audioStream, AudioFileFormat.Type.WAVE, wavFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };
            thread.start();
            thread.sleep(5000);
            targetLine.stop();
            targetLine.close();
            //System.out.println("Finished");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
