package dev.mariorez.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import dev.mariorez.GameBoot;

public class Lwjgl3Launcher {
    public static void main(String[] args) {
        createApplication();
    }

    private static Lwjgl3Application createApplication() {
        var gameBoot = new GameBoot();
        return new Lwjgl3Application(
            gameBoot,
            getDefaultConfiguration((int) gameBoot.sizes.windowWidth, (int) gameBoot.sizes.windowHeight)
        );
    }

    private static Lwjgl3ApplicationConfiguration getDefaultConfiguration(int windowWidth, int windowHeight) {
        Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
        configuration.setTitle("Treasure Quest");
        configuration.setWindowedMode(windowWidth, windowHeight);
        configuration.setWindowIcon("libgdx128.png", "libgdx64.png", "libgdx32.png", "libgdx16.png");
        /* Limits FPS to the refresh rate of the currently active monitor. */
        // configuration.useVsync(false); // default = true
        // configuration.setForegroundFPS(Lwjgl3ApplicationConfiguration.getDisplayMode().refreshRate);
        /* If you remove the above line and set Vsync to false, you can get unlimited FPS, which can be
        useful for testing performance, but can also be very stressful to some hardware.
        You may also need to configure GPU drivers to fully disable Vsync; this can cause screen tearing. */
        return configuration;
    }
}
