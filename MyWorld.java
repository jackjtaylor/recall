import greenfoot.*;

/**
 * Write a description of class MyWorld here.
 * 
 * @author Jack Taylor
 * @version v1
 */
public class MyWorld extends World {

    public MyWorld() {
        super(852, 480, 1);

        // This sets the resolution of the project.
        final int resolutionX = 852;
        final int resolutionY = 480;

        Frame frame = new Frame();
        addObject(frame, resolutionX / 2, resolutionY / 2);
    }
}
