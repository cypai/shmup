package com.pipai.shmup.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.pipai.shmup.ShmupGame;

public final class DesktopLauncher {

    private DesktopLauncher() {
    }

    // @cs.suppress [UncommentedMain] this is the main entry point
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 800;
        config.height = 600;
        config.resizable = false;
        new LwjglApplication(new ShmupGame(), config);
    }
}
