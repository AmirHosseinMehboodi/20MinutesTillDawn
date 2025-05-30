package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Graphics;
import io.github.some_example_name.Controller.ProfileMenuController;
import io.github.some_example_name.Main;
import io.github.some_example_name.View.ProfileMenu;
import org.lwjgl.glfw.GLFWDropCallback;

import java.util.Arrays;

import static org.lwjgl.glfw.GLFW.glfwSetDropCallback;

/** Launches the desktop (LWJGL3) application. */
public class Lwjgl3Launcher {
    public static void main(String[] args) {
        if (StartupHelper.startNewJvmIfRequired()) return; // This handles macOS support and helps on Windows.
        createApplication();
    }

    private static Lwjgl3Application createApplication() {
        return new Lwjgl3Application(new Main() {
            @Override
            public void create() {
                super.create();
                // Set up drag and drop after the application is created
                setupDragAndDrop();
            }
        }, getDefaultConfiguration());
    }

    private static void setupDragAndDrop() {
        // Get the window handle from LibGDX
        Lwjgl3Graphics graphics = (Lwjgl3Graphics) Gdx.graphics;
        long windowHandle = graphics.getWindow().getWindowHandle();

        // Create and set the drop callback
        GLFWDropCallback dropCallback = new GLFWDropCallback() {
            @Override
            public void invoke(long window, int count, long names) {
                // Convert the native string array to Java strings
                String[] files = new String[count];
                for (int i = 0; i < count; i++) {
                    files[i] = GLFWDropCallback.getName(names, i);
                }

                // Call your existing method
                ProfileMenuController.filesDropped(files);
            }
        };

        // Set the callback on your window
        glfwSetDropCallback(windowHandle, dropCallback);
    }

    private static Lwjgl3ApplicationConfiguration getDefaultConfiguration() {
        Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
        configuration.setTitle("Until Dawn");
        //// Vsync limits the frames per second to what your hardware can display, and helps eliminate
        //// screen tearing. This setting doesn't always work on Linux, so the line after is a safeguard.
        configuration.useVsync(true);
        //// Limits FPS to the refresh rate of the currently active monitor, plus 1 to try to match fractional
        //// refresh rates. The Vsync setting above should limit the actual FPS to match the monitor.
        configuration.setForegroundFPS(Lwjgl3ApplicationConfiguration.getDisplayMode().refreshRate + 1);
        //// If you remove the above line and set Vsync to false, you can get unlimited FPS, which can be
        //// useful for testing performance, but can also be very stressful to some hardware.
        //// You may also need to configure GPU drivers to fully disable Vsync; this can cause screen tearing.

        configuration.setWindowedMode(1920, 1080);
//        configuration.setFullscreenMode(Lwjgl3ApplicationConfiguration.getDisplayMode());
        //// You can change these files; they are in lwjgl3/src/main/resources/ .
        //// They can also be loaded from the root of assets/ .
        configuration.setWindowIcon("libgdx128.png", "libgdx64.png", "libgdx32.png", "libgdx16.png");
        return configuration;
    }
}
