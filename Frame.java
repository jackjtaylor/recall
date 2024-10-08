
import greenfoot.*;
import static java.lang.Math.*;
import java.awt.Robot; // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Frame here.
 * 
 * @author (your name)
 * @version (a version number or a date)
 */
public class Frame extends Actor {
    final int screenWidth = 1280;
    final int screenHeight = 720;
    GreenfootImage frame = new GreenfootImage(screenWidth, screenHeight);

    private int[][] mapLayout = {
            { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
            { 1, 0, 0, 0, 1, 1, 0, 0, 0, 1 },
            { 1, 0, 1, 1, 1, 0, 0, 0, 0, 1 },
            { 1, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
            { 1, 0, 0, 0, 1, 1, 0, 0, 0, 1 },
            { 1, 0, 1, 1, 1, 0, 0, 2, 0, 1 },
            { 1, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
            { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }
    };

    private int[][] texture1 = {
            { 1, 1, 1, 1, 1, 1, 1, 1 },
            { 0, 0, 0, 1, 0, 0, 0, 1 },
            { 1, 1, 1, 1, 1, 1, 1, 1 },
            { 0, 1, 0, 0, 0, 1, 0, 0 },
            { 1, 1, 1, 1, 1, 1, 1, 1 },
            { 0, 0, 0, 1, 0, 0, 0, 1 },
            { 1, 1, 1, 1, 1, 1, 1, 1 },
            { 0, 1, 0, 0, 0, 1, 0, 0 }
    };

    private int[][] texture2 = {
            { 2, 2, 2, 2, 2, 2, 2, 2 },
            { 2, 2, 2, 2, 2, 2, 2, 2 },
            { 2, 2, 2, 2, 2, 2, 2, 2 },
            { 2, 2, 2, 2, 2, 2, 2, 2 },
            { 2, 2, 2, 2, 2, 2, 2, 2 },
            { 2, 2, 2, 2, 2, 2, 2, 2 },
            { 2, 2, 2, 2, 2, 2, 2, 2 },
            { 2, 2, 2, 2, 2, 2, 2, 2 }
    };

    private int[][] texture3 = {
            { 3, 3, 3, 1, 1, 3, 3, 3 },
            { 3, 3, 3, 1, 1, 3, 3, 3 },
            { 3, 3, 3, 1, 1, 3, 3, 3 },
            { 3, 3, 3, 1, 1, 3, 3, 3 },
            { 3, 3, 3, 1, 1, 3, 3, 3 },
            { 3, 3, 3, 3, 3, 3, 3, 3 },
            { 3, 3, 3, 1, 1, 3, 3, 3 },
            { 3, 3, 3, 1, 1, 3, 3, 3 }
    };

    private int[][] colors = {
            { 198, 110, 65 },
            { 168, 70, 21 },
            { 255, 255, 255 },
            { 0, 0, 0 }
    };

    double playerX = 1;
    double playerY = 1;
    double playerAngle = 80;
    double playerFOV = 75;
    double playerRotationSpeed = 0.125;
    double playerMoveSpeed = 0.005;

    double rayIncrement = playerFOV / screenWidth;
    double rayPrecision = 50;
    int wallHeightMultiplier = 100;
    double resolutionMultiplier = 1;

    long lastTime = System.nanoTime();
    long currentTime = 0;
    int deltaTime = 0;

    double defaultMousePosX = 0;
    double defaultMousePosY = 0;
    double currentMousePos = 0;
    Robot robot;

    /**
     * Act - do whatever the Frame wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */

    public Frame() {
        setImage(frame);
    }

    public void act() {
        currentDeltaTime();
        movement();
        frame.clear();
        raycast();
        Greenfoot.delay(1);
        resetDeltaTime();
    }

    public void raycast() {
        double rayAngle = playerAngle - ((playerFOV * resolutionMultiplier) / 2);
        for (int ray = 0; ray < screenWidth * resolutionMultiplier; ray++) {
            double rayX = playerX;
            double rayY = playerY;
            double rayCos = Math.cos(Math.toRadians(rayAngle)) / rayPrecision;
            double raySin = Math.sin(Math.toRadians(rayAngle)) / rayPrecision;

            double distance = 0;
            double height = 0;

            while (true) {
                rayX += rayCos;
                rayY += raySin;
                distance += 1;
                if (mapLayout[(int) rayY][(int) rayX] != 0) {
                    distance = distance * Math.cos(Math.toRadians(rayAngle - playerAngle));
                    height = (250 / distance * wallHeightMultiplier);
                    double texturePositionX = Math.floor((8 * (rayX + rayY)) % 8);

                    double yIncrementer = (height * 2) / 8;
                    double y = (screenHeight / 2) - height;

                    for (int i = 0; i < 8; i++) {

                        Color texColor;

                        if (mapLayout[(int) rayY][(int) rayX] == 2) {
                            texColor = new Color(
                                    (int) Math.min(
                                            colors[texture2[i][(int) texturePositionX]][0]
                                                    / Math.max(Math.pow(distance * 0.005, 2), 0.01),
                                            colors[texture2[i][(int) texturePositionX]][0]),
                                    (int) Math.min(
                                            colors[texture2[i][(int) texturePositionX]][1]
                                                    / Math.max(Math.pow(distance * 0.005, 2), 0.01),
                                            colors[texture2[i][(int) texturePositionX]][1]),
                                    (int) Math.min(
                                            colors[texture2[i][(int) texturePositionX]][2]
                                                    / Math.max(Math.pow(distance * 0.005, 2), 0.01),
                                            colors[texture2[i][(int) texturePositionX]][2]));
                        } else {
                            texColor = new Color(
                                    (int) Math.min(
                                            colors[texture1[i][(int) texturePositionX]][0]
                                                    / Math.max(Math.pow(distance * 0.01, 2), 0.01),
                                            colors[texture1[i][(int) texturePositionX]][0]),
                                    (int) Math.min(
                                            colors[texture1[i][(int) texturePositionX]][1]
                                                    / Math.max(Math.pow(distance * 0.01, 2), 0.01),
                                            colors[texture1[i][(int) texturePositionX]][1]),
                                    (int) Math.min(
                                            colors[texture1[i][(int) texturePositionX]][2]
                                                    / Math.max(Math.pow(distance * 0.01, 2), 0.01),
                                            colors[texture1[i][(int) texturePositionX]][2]));
                        }

                        frame.setColor(texColor);

                        frame.drawLine(ray, (int) y, ray, (int) y + (int) (Math.round(yIncrementer)));
                        y += yIncrementer;
                    }

                    break;
                }
            }

            rayAngle += rayIncrement;
        }

    }

    public void currentDeltaTime() {
        currentTime = System.nanoTime();
        deltaTime = (int) ((currentTime - lastTime) / 1000000);
    }

    public void resetDeltaTime() {
        lastTime = currentTime;
    }

    public void movement() {
        if (Greenfoot.isKeyDown("w") || Greenfoot.isKeyDown("up")) {
            double playerCos = Math.cos(Math.toRadians(playerAngle)) * playerMoveSpeed * deltaTime;
            double playerSin = Math.sin(Math.toRadians(playerAngle)) * playerMoveSpeed * deltaTime;

            double newX = playerX + playerCos;
            double newY = playerY + playerSin;

            if (mapLayout[(int) Math.floor(newY)][(int) Math.floor(newX)] == 0) {
                playerX = newX;
                playerY = newY;
            }
        }
        if (Greenfoot.isKeyDown("s") || Greenfoot.isKeyDown("down")) {
            double playerCos = Math.cos(Math.toRadians(playerAngle)) * playerMoveSpeed * deltaTime;
            double playerSin = Math.sin(Math.toRadians(playerAngle)) * playerMoveSpeed * deltaTime;

            double newX = playerX - playerCos;
            double newY = playerY - playerSin;

            if (mapLayout[(int) Math.floor(newY)][(int) Math.floor(newX)] == 0) {
                playerX = newX;
                playerY = newY;
            }
        }
        if (Greenfoot.isKeyDown("a") || Greenfoot.isKeyDown("left")) {
            playerAngle -= (playerRotationSpeed * deltaTime);
        }
        if (Greenfoot.isKeyDown("d") || Greenfoot.isKeyDown("right")) {
            playerAngle += (playerRotationSpeed * deltaTime);
        }
        if (Greenfoot.isKeyDown("e")) {

        }
    }
}
