package libranet;

import java.time.Duration;
public interface Playable {
    void play();
    void pause();
    void stop();
    Duration getPlaybackDuration();
}