import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Enemy here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Enemy extends Actor
{
    public double enemyX = 3;
    public double enemyY = 3;
    
    /**
     * Act - do whatever the Enemy wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    
    public Enemy()
    {
        
    }
    
    public void act()
    {
        // Add your action code here.
    }
    
    public double getEnemyX(Enemy enemy)
    {
        return enemy.getX();
    }
    
    public double getEnemyY(Enemy enemy)
    {
        return enemy.getY();
    }
}
