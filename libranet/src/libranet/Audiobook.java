package libranet;


import java.time.Duration;

public class Audiobook extends LibraryItem implements Playable {
    private final Duration playbackDuration;
    private boolean playing = false;

    public Audiobook(int id, String title, String author, int playbackMinutes) {
        super(id, title, author);
        if (playbackMinutes <= 0) {
            throw new IllegalArgumentException("Playback duration must be positive minutes");
        }
        this.playbackDuration = Duration.ofMinutes(playbackMinutes);
    }

    @Override
    public void play() {
        if (!available) {
            throw new IllegalStateException("Cannot play: audiobook is currently borrowed or unavailable.");
        }
        playing = true;
        System.out.println("Playing audiobook: " + title + " by " + author);
    }

    @Override
    public void pause() {
        if (!playing) {
            System.out.println("Audiobook is not currently playing.");
            return;
        }
        playing = false;
        System.out.println("Paused audiobook: " + title);
    }

    @Override
    public void stop() {
        if (playing) {
            playing = false;
            System.out.println("Stopped audiobook: " + title);
        } else {
            System.out.println("Audiobook is not currently playing.");
        }
    }

    @Override
    public Duration getPlaybackDuration() {
        return playbackDuration;
    }

    public boolean isPlaying() {
        return playing;
    }
}
