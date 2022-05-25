package org.rloop;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.rloop.Tiles.*;

import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.abs;

public class Level {
    protected World world;
    protected rloop game;

    protected int HALF_ROOM_HEIGHT = 15;
    protected int HALF_ROOM_WIDTH = 11;
    protected ArrayList<Tile> wallTiles;
    protected ArrayList<Tile> floorTiles;
    protected ArrayList<Spikes> spikesTiles;
    protected Viewport viewport;
    protected Vector2 pos;
    protected ArrayList<Rectangle> template;

    public static boolean PointIsInRectangle(float x, float y, Rectangle r){
        if(x >= r.x && y >= r.y && x <= r.x + r.width && y <= r.y + r.height){
            return true;
        }else{
            return false;
        }
    }
    public void GenerateTiles(){
        wallTiles  = new ArrayList<>();
        floorTiles = new ArrayList<>();
        spikesTiles = new ArrayList<>();
        for(Rectangle r1: template){
            for(int i=0;i<r1.width;i+=2){
                for(int j=0;j<r1.height;j+=2){
                    floorTiles.add(new Floor((int)r1.x + i, (int)r1.y+j, this));
                    if(new Random().nextInt(10) == 9){
                        if(new Random().nextBoolean())
                        spikesTiles.add(new Spikes((int)r1.x + i, (int)r1.y+j, this));
                        else spikesTiles.add(new HiddenSpikes((int)r1.x + i, (int)r1.y+j, this));
                    }
                }
            }
            boolean flag = false;
            for(Rectangle r2: template) {
                if (PointIsInRectangle(r1.x-1, r1.y - 1, r2)){
                    flag = true;
                }
            }
            if (!flag){
                wallTiles.add(new Wall((int)r1.x-2, (int)r1.y-2, this));
            }
            for(int i = 0; i <= r1.width; i += 2){
                for(int j = 0; j <= r1.height; j += 2){
                    float x = r1.x + i;
                    float y = r1.y + j;
                    flag = false;
                    for(Rectangle r2: template) {
                        if (PointIsInRectangle(x+1, r1.y - 1, r2)){
                            flag = true;
                        }
                    }
                    if (!flag){
                        wallTiles.add(new Wall((int)x, (int)r1.y-2, this));
                    }
                    flag = false;
                    for(Rectangle r2: template) {
                        if (PointIsInRectangle(x+1, r1.y + r1.height + 1, r2)){
                            flag = true;
                        }
                    }
                    if (!flag){
                        wallTiles.add(new Wall((int)x, (int) (r1.y + r1.height), this));
                    }
                    flag = false;
                    for(Rectangle r2: template) {
                        if (PointIsInRectangle(r1.x-1, y+1, r2)){
                            flag = true;
                        }
                    }
                    if (!flag){
                        wallTiles.add(new Wall((int)r1.x-2, (int)y, this));
                    }
                    flag = false;
                    for(Rectangle r2: template) {
                        if (PointIsInRectangle(r1.x + r1.width + 1, y+1, r2)){
                            flag = true;
                        }
                    }
                    if (!flag){
                        wallTiles.add(new Wall((int)(r1.x + r1.width), (int)y, this));
                    }
                }
            }
        }
    }

    public void render(){
        this.getCamera().update();
        this.viewport.apply();
        this.getGame().getBatch().setProjectionMatrix(this.getCamera().combined);
        this.getGame().getBatch().begin();
        for(Tile tile: wallTiles){
                tile.render();
        }
        for(Tile tile: floorTiles){
            tile.render();
        }
        for(Tile tile: spikesTiles){
            tile.render();
        }
        this.getGame().getBatch().end();
    }

    public Vector2 getPlayerPosition(){
        int k = new Random().nextInt(floorTiles.size());
        return new Vector2(floorTiles.get(k).getX()+1, floorTiles.get(k).getY()+1);
    }

    public Vector2 getPlayerCurrentPosition(){
        return new Vector2(game.mainScreen.player.x, game.mainScreen.player.y);
    }

    public Level(World world, rloop game, Viewport viewport, ArrayList<Rectangle> r){
        this.template = r;
        pos = new Vector2();
        this.viewport = viewport;
        this.game = game;
        this.world = world;
//        GenerateWalls();
//        GenerateDoors();
        GenerateTiles();
    }

    public Player getPlayer(){
        return game.mainScreen.player;
    }

    public Viewport getViewport() {
        return viewport;
    }

    public World getWorld(){
        return world;
    }
    public Camera getCamera(){
        return viewport.getCamera();
    }

    public rloop getGame(){
        return game;
    }

    public void render(float delta) {

    }
}