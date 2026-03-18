package manager.preinstalled;

import manager.base.Manager;
import manager.system.FileManager;
import model.preinstalled.Audio;
import model.base.File;

import java.util.ArrayList;
import java.util.List;

public class AudioManager extends Manager {
    private final FileManager fileManager;
    private int currentIndex;
    private boolean isPlaying;

    public AudioManager(FileManager fileManager) {
        this.fileManager = fileManager;
        this.currentIndex = 0;
        this.isPlaying = false;
    }

    public List<Audio> getQueue() {
        List<Audio> queue = new ArrayList<>();
        for (File f : fileManager.getAudios()) {
            if (f instanceof Audio)
                queue.add((Audio) f);
        }
        return queue;
    }

    public Audio getCurrentTrack() {
        List<Audio> queue = getQueue();
        if (queue.isEmpty()) return null;
        if (currentIndex >= queue.size()) currentIndex = 0;
        return queue.get(currentIndex);
    }

    public void play() { isPlaying = true; }
    public void pause() { isPlaying = false; }
    public boolean isPlaying() { return isPlaying; }

    public void next() {
        List<Audio> queue = getQueue();
        if (queue.isEmpty()) return;
        currentIndex = (currentIndex + 1) % queue.size();
        isPlaying = true;
    }

    public void previous() {
        List<Audio> queue = getQueue();
        if (queue.isEmpty()) return;
        currentIndex = (currentIndex - 1 + queue.size()) % queue.size();
        isPlaying = true;
    }

    public boolean isEmpty() { return getQueue().isEmpty(); }
}