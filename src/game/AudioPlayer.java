package game;

// Imports
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class AudioPlayer implements Runnable {

	// Variables
	public Clip clip;

	private FloatControl gainControl;

	float currDB = 1F;
	float targetDB = 0F;
	float fadePerStep = 0.1F; // .1 works for applets, 1 is okay for apps
	boolean fading = false;
	Thread t;

	// Constructor
	public AudioPlayer(String s) {

		try {

			// Loads the audio in
			AudioInputStream ais = AudioSystem.getAudioInputStream(getClass().getResourceAsStream(s));

			AudioFormat baseFormat = ais
					.getFormat(); /* Obtains the audio format of the sound data in this audio input stream. */

			AudioFormat decodeFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, baseFormat.getSampleRate(), 16,
					baseFormat.getChannels(), baseFormat.getChannels() * 2, baseFormat.getSampleRate(),
					false); /* Creates a new AudioPlayer with the decoded audio */

			AudioInputStream dais = AudioSystem.getAudioInputStream(decodeFormat,
					ais); /*
							 * Obtains an audio input stream of the indicated format, by converting the
							 * provided audio input stream.
							 */

			clip = AudioSystem.getClip(); /*
											 * Obtains a clip that can be used for playing back an audio file or an
											 * audio stream.
											 */

			clip.open(dais); /*
								 * Opens the clip with the format and audio data present in the provided audio
								 * input stream.
								 */

			gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

		} catch (Exception e) {

			e.printStackTrace(); // Prints errors

		}

	}

	public void play() {

		// Returns if there is no clip
		if (clip == null) {

			return;

		}

		// Plays clip
		stop();
		clip.setFramePosition(0);
		clip.start();

	}

	public void stop() {

		// Stops the clip
		if (clip.isRunning()) {

			clip.stop();

		}

	}

	public void close() {

		// Frees system memory
		stop();
		clip.close();

	}

	/**
	 * Set the volume to a value between 0 and 1.
	 */
	public void setVolume(double value) {
		// value is between 0 and 1
		value = (value <= 0.0) ? 0.0001 : ((value > 1.0) ? 1.0 : value);
		try {
			float dB = (float) (Math.log(value) / Math.log(10.0) * 20.0);
			gainControl.setValue(dB);
			currDB = dB;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Fade the volume to a new value. To shift volume while sound is playing, ie.
	 * to simulate motion to or from an object, the volume has to change smoothly in
	 * a short period of time. Unfortunately this makes an annoying clicking noise,
	 * mostly noticeable in the browser. I reduce the click by fading the volume in
	 * small increments with delays in between. This means that you can't change the
	 * volume very quickly. The fade has to to take a second or two to prevent
	 * clicks.
	 */

	public void shiftVolumeTo(double value) {
		// value is between 0 and 1
		value = (value <= 0.0) ? 0.0001 : ((value > 1.0) ? 1.0 : value);
		targetDB = (float) (Math.log(value) / Math.log(10.0) * 20.0);
		if (!fading) {
			t = new Thread(this); // start a thread to fade volume
			t.start(); // calls run() below
		}
	}

	/**
	 * Run by thread, this will step the volume up or down to a target level.
	 * Applets need fadePerStep=.1 to minimize clicks. Apps can get away with
	 * fadePerStep=1.0 for a faster fade with no clicks.
	 */
	public void run() {

		fading = true; // prevent running twice on same sound

		if (currDB > targetDB) {
			while (currDB > targetDB) {
				currDB -= fadePerStep;

				gainControl.setValue(currDB);
				try {
					Thread.sleep(10);
				} catch (Exception e) {
				}
			}
		} else if (currDB < targetDB) {
			while (currDB < targetDB) {
				currDB += fadePerStep;

				gainControl.setValue(currDB);
				try {
					Thread.sleep(10);
				} catch (Exception e) {
				}
			}
		}

		fading = false;
		currDB = targetDB; // now sound is at this volume level
		t.interrupt();
	}

}
