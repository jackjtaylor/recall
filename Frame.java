import greenfoot.*;
import static java.lang.Math.*;
import java.awt.Robot;   // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Frame here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Frame extends Actor
{
    final int screenWidth = 1280;
    final int screenHeight = 720;
    GreenfootImage frame = new GreenfootImage(screenWidth, screenHeight);

    private int[][] mapLayout = {
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
        {1, 0, 0, 0, 1, 1, 0, 0, 0, 1},
        {1, 0, 1, 1, 1, 0, 0, 0, 0, 1},
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 0, 0, 0, 1, 1, 0, 0, 0, 1},
        {1, 0, 1, 1, 1, 0, 0, 2, 0, 1},
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    };
    
    
    private int[][] texture1 = {
        {1,1,1,1,1,1,1,1},
        {0,0,0,1,0,0,0,1},
        {1,1,1,1,1,1,1,1},
        {0,1,0,0,0,1,0,0},
        {1,1,1,1,1,1,1,1},
        {0,0,0,1,0,0,0,1},
        {1,1,1,1,1,1,1,1},
        {0,1,0,0,0,1,0,0}
    };
    
    private int[][] texture2 = {
        {2,2,2,2,2,2,2,2},
        {2,2,2,2,2,2,2,2},
        {2,2,2,2,2,2,2,2},
        {2,2,2,2,2,2,2,2},
        {2,2,2,2,2,2,2,2},
        {2,2,2,2,2,2,2,2},
        {2,2,2,2,2,2,2,2},
        {2,2,2,2,2,2,2,2}
    };
    
    private int[][] texture3 = {
        {3,3,3,1,1,3,3,3},
        {3,3,3,1,1,3,3,3},
        {3,3,3,1,1,3,3,3},
        {3,3,3,1,1,3,3,3},
        {3,3,3,1,1,3,3,3},
        {3,3,3,3,3,3,3,3},
        {3,3,3,1,1,3,3,3},
        {3,3,3,1,1,3,3,3}
    };
    
    private int[][] colors = {
        {255, 241, 232},
        {178, 198, 199},
        {255, 0, 50},
        {0, 0, 0}
    };
    
    double playerX = 1;
    double playerY = 1;
    double playerAngle = 80;
    double playerFOV = 60;
    double playerRotationSpeed = 0.25;
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
    
    public Frame()
    {
        setImage(frame);
    }
    
    public void act()
    {
        currentDeltaTime();
        movement();
        frame.clear();
        raycast();
        Greenfoot.delay(1);
        resetDeltaTime();
    }
    
    public void raycast()
    {
        double rayAngle = playerAngle - ((playerFOV * resolutionMultiplier) / 2);
        for (int ray = 0; ray < screenWidth * resolutionMultiplier; ray++)
        {
            double rayX = playerX;
            double rayY = playerY;
            double rayCos = Math.cos(Math.toRadians(rayAngle)) / rayPrecision;
            double raySin = Math.sin(Math.toRadians(rayAngle)) / rayPrecision;
            
            double distance = 0;
            double height = 0;
            
            /*
            if (MyWorld.getActors(Enemy) != null)
            {
                for (int enemy = 0; enemy < MyWorld.getActors(Enemy); enemy++)
                {
                    
                }
            }
            */
            
            while (true)
            {
                rayX += rayCos;
                rayY += raySin;
                distance += 1;
                if (mapLayout[(int)rayY][(int)rayX] != 0)
                {
                    distance = distance * Math.cos(Math.toRadians(rayAngle - playerAngle));
                    height = (250/distance * wallHeightMultiplier);
                    double texturePositionX = Math.floor((8 * (rayX + rayY)) % 8);
                    
                    double yIncrementer = (height * 2) / 8;
                    double y = (screenHeight / 2) - height;
                    
                    
                    

                    
                    for (int i = 0; i < 8; i++) 
                    {
                        //System.out.println((int)(colors[texture1[i][(int)texturePositionX]][0] * (255 - distance)));
                        //Color texColor = new Color (
                        //Math.min(255, (int)(colors[texture1[i][(int)texturePositionX]][0] * (255 - distance))), 
                        //Math.min(255, (int)(colors[texture1[i][(int)texturePositionX]][1] * (255 - distance))), 
                        //Math.min(255, (int)(colors[texture1[i][(int)texturePositionX]][2] * (255 - distance))));
                    
                        Color texColor;
                        
                        if (mapLayout[(int)rayY][(int)rayX] == 2) 
                        {
                            texColor = new Color (
                            (int)Math.min(colors[texture2[i][(int)texturePositionX]][0] / Math.max(Math.pow(distance * 0.02, 2), 0.01), colors[texture2[i][(int)texturePositionX]][0]),
                            (int)Math.min(colors[texture2[i][(int)texturePositionX]][1] / Math.max(Math.pow(distance * 0.02, 2), 0.01), colors[texture2[i][(int)texturePositionX]][1]),
                            (int)Math.min(colors[texture2[i][(int)texturePositionX]][2] / Math.max(Math.pow(distance * 0.02, 2), 0.01), colors[texture2[i][(int)texturePositionX]][2])
                            );
                        }
                        else 
                        {
                            texColor = new Color (
                            (int)Math.min(colors[texture1[i][(int)texturePositionX]][0] / Math.max(Math.pow(distance * 0.02, 2), 0.01), colors[texture1[i][(int)texturePositionX]][0]),
                            (int)Math.min(colors[texture1[i][(int)texturePositionX]][1] / Math.max(Math.pow(distance * 0.02, 2), 0.01), colors[texture1[i][(int)texturePositionX]][1]),
                            (int)Math.min(colors[texture1[i][(int)texturePositionX]][2] / Math.max(Math.pow(distance * 0.02, 2), 0.01), colors[texture1[i][(int)texturePositionX]][2])
                            );
                        }
                        
                        
                        frame.setColor(texColor);
                        
                        frame.drawLine(ray, (int)y, ray, (int)y + (int)(Math.round(yIncrementer)));
                        y += yIncrementer;
                    }
                    
                    
                    
                    
                    break;
                }
            }
            
            double spriteHX = getWorld().getObjects(Enemy.class).get(0).enemyX - playerX;
            double spriteHY = getWorld().getObjects(Enemy.class).get(0).enemyY - playerY;
            
            double spriteAngle = Math.atan2(-spriteHY, spriteHX) * (180 / PI);
            
            double spriteH = (int)Math.round(Math.sqrt( Math.pow (spriteHX, 2)) + (Math.pow(spriteHY, 2)));
            
            if (spriteAngle < 0)
            {
                spriteAngle += 360;
            }
            else if (spriteAngle > 360)
            {
                spriteAngle -= 360;
            }
            
            double spriteXFactor = playerAngle + (playerFOV / 2) - spriteAngle;
            
            boolean isPlayerLookingAtQuadrant4 = 270 <= playerAngle && playerAngle <= 360;
            boolean isPlayerLookingAtQuadrant1 = 0 <= playerAngle && playerAngle <= 90;
            
            boolean isSpriteInQuadrant4 = 270 <= spriteAngle && spriteAngle <= 360;
            boolean isSpriteInQuadrant1 = 0 <= spriteAngle && spriteAngle <= 90;
            if (isPlayerLookingAtQuadrant4 && isSpriteInQuadrant1)
            {
                spriteXFactor += 360;
            }
            else if (isPlayerLookingAtQuadrant1 && isSpriteInQuadrant4)
            {
                spriteXFactor -= 360;
            }
            
            double spriteScreenX = spriteXFactor * (screenWidth / playerFOV);
            double spriteScreenY = screenHeight/2;
            
            int spriteHeight = (int)(100 / spriteH);
            int spriteWidth = spriteHeight;
            
            int spriteDrawXStart = (int)Math.round(spriteScreenX - (spriteWidth / 2));
            int spriteDrawXEnd = (int)Math.round(spriteScreenX + (spriteWidth / 2));
            
            frame.setColor(Color.RED);
            for (int col = spriteDrawXStart; col < spriteDrawXEnd; col++)
            {
                frame.drawLine(col, (screenHeight / 2) - spriteHeight, col, (screenHeight / 2) + spriteHeight);
            }
            // frame.drawLine( (int) (ray / resolutionMultiplier), (screenHeight / 2) + height, (int) (ray / resolutionMultiplier), (screenHeight / 2) - height);
            
            rayAngle += rayIncrement;
        }
        
    }
    
    public void currentDeltaTime()
    {
        currentTime = System.nanoTime();
        deltaTime = (int) ((currentTime - lastTime) / 1000000);
    }
    
    public void resetDeltaTime()
    {
        lastTime = currentTime;
    }
    
    public void movement()
    {
        if (Greenfoot.isKeyDown("w") || Greenfoot.isKeyDown("up"))
        {
            double playerCos = Math.cos(Math.toRadians(playerAngle)) * playerMoveSpeed * deltaTime;
            double playerSin = Math.sin(Math.toRadians(playerAngle)) * playerMoveSpeed * deltaTime;
            
            double newX = playerX + playerCos;
            double newY = playerY + playerSin;
            
            if (mapLayout[(int)Math.floor(newY)][(int)Math.floor(newX)] == 0)
            {
                playerX = newX;
                playerY = newY;
            }
        }
        if (Greenfoot.isKeyDown("s") || Greenfoot.isKeyDown("down"))
        {
            double playerCos = Math.cos(Math.toRadians(playerAngle)) * playerMoveSpeed * deltaTime;
            double playerSin = Math.sin(Math.toRadians(playerAngle)) * playerMoveSpeed * deltaTime;
            
            double newX = playerX - playerCos;
            double newY = playerY - playerSin;
            
            if (mapLayout[(int)Math.floor(newY)][(int)Math.floor(newX)] == 0)
            {
                playerX = newX;
                playerY = newY;
            }
        }
        if (Greenfoot.isKeyDown("a") || Greenfoot.isKeyDown("left"))
        {
            playerAngle -= (playerRotationSpeed * deltaTime);
        }
        if (Greenfoot.isKeyDown("d") || Greenfoot.isKeyDown("right"))
        {
            playerAngle += (playerRotationSpeed * deltaTime);
        }
        if (Greenfoot.isKeyDown("e"))
        {
            
        }
    }
}
