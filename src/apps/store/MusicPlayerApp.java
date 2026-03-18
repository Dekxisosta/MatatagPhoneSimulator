package apps.store;

import apps.base.*;
import manager.base.Manager;
import manager.system.FileManager;
import model.base.File;
import model.preinstalled.Audio;
import ui.base.UI;

import java.util.*;

final class PredefinedSongs {
    static final List<Audio> ALL = new ArrayList<>(Arrays.asList(
            new Audio("Elma",                      "mp3", "Yorushika",  4_100_000L),
            new Audio("Say It",                    "mp3", "Yorushika",  3_900_000L),
            new Audio("Hitchcock",                 "mp3", "Yorushika",  4_300_000L),
            new Audio("Ghost",                     "mp3", "Yorushika",  3_800_000L),
            new Audio("Matasaburo",                "mp3", "Yorushika",  4_500_000L),
            new Audio("That's Why I Gave Up Music","mp3", "Yorushika",  4_200_000L),
            new Audio("Sunny Place",               "mp3", "Yorushika",  3_700_000L),
            new Audio("Replicant",                 "mp3", "Yorushika",  4_000_000L),
            new Audio("En Route",                  "mp3", "Yorushika",  4_600_000L),
            new Audio("Thoughtful",                "mp3", "Yorushika",  3_600_000L)
    ));
}

final class MusicPlayerAppConfig {
    static final String NAME = "Music Player";
    static final String DEVELOPER = "InPlay Corp.";
    static final String VERSION = "v1.0.0";
    static final String APP_TYPE = "STORE";
    static final double MEM_USED = 5.0;
    static final String[] COMMAND_NAMES = {
            "Play / Pause",
            "Next Track",
            "Previous Track",
            "Now Playing",
            "View Queue"
    };
    static final int[] COMMAND_DURATIONS = { 1, 1, 1, 1, 1 };
}

class AudioManager extends Manager {
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

class MusicPlayerUI extends UI {

    public void showNowPlaying(Audio track) {
        if (track == null) { System.out.println("No track playing."); return; }
        System.out.println("▶ Now Playing:");
        System.out.println(track.getAudioDetails());
    }

    public void showPaused(Audio track) {
        if (track == null) { System.out.println("No track loaded."); return; }
        System.out.println("⏸ Paused:");
        System.out.println(track.getAudioDetails());
    }

    public void showQueue(List<Audio> queue, Audio current) {
        System.out.println("--- Queue ---");
        for (int i = 0; i < queue.size(); i++) {
            String marker = queue.get(i).equals(current) ? "▶ " : "  ";
            System.out.println(String.format("%s[%d] %s",
                    marker, i + 1,
                    queue.get(i).getFileName()));
        }
    }
}

public class MusicPlayerApp extends App<AudioManager, MusicPlayerUI> {
    private Runnable[] runnables = {
            () -> {
                if (manager.isPlaying()) {
                    manager.pause();
                    ui.showPaused(manager.getCurrentTrack());
                } else {
                    manager.play();
                    ui.showNowPlaying(manager.getCurrentTrack());
                }
            },
            () -> {
                manager.next();
                ui.showNowPlaying(manager.getCurrentTrack());
            },
            () -> {
                manager.previous();
                ui.showNowPlaying(manager.getCurrentTrack());
            },
            () -> ui.showNowPlaying(manager.getCurrentTrack()),
            () -> {
                List<Audio> queue = manager.getQueue();
                ui.showQueue(queue, manager.getCurrentTrack());
            }
    };

    private Command.BooleanSupplier[] checks = {
            () -> !manager.isEmpty(),
            () -> !manager.isEmpty(),
            () -> !manager.isEmpty(),
            () -> !manager.isEmpty(),
            () -> !manager.isEmpty()
    };

    public MusicPlayerApp(FileManager fileManager) {
        super(
                new MusicPlayerUI(),
                new AudioManager(fileManager),
                MusicPlayerAppConfig.NAME,
                MusicPlayerAppConfig.DEVELOPER,
                MusicPlayerAppConfig.VERSION,
                MusicPlayerAppConfig.APP_TYPE,
                MusicPlayerAppConfig.MEM_USED
        );
        configureCommands(
                MusicPlayerAppConfig.COMMAND_NAMES,
                MusicPlayerAppConfig.COMMAND_DURATIONS,
                runnables,
                checks
        );
        preloadSongs(fileManager);
    }
    private void preloadSongs(FileManager fileManager) {
        for (Audio song : PredefinedSongs.ALL)
            fileManager.addFile(song);
    }
}