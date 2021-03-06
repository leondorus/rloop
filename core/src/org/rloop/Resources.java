package org.rloop;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;

public class Resources {
    public Texture hiddenSpikes;
    public Texture spikes;
    public TextureRegion stoneFloor;
    public TextureRegion[][] walls;
    public Texture skeletonMage;
    public Texture goblin;
    public Texture allThings;
    public TextureRegion mainSword;
    public TextureRegion magickBook;
    public TextureRegion damagePotion;
    public TextureRegion speedPotion;
    public TextureRegion hpPotion;
    public TextureRegion tripleShot;
    public Texture portal;
    public Texture chest;
    public Texture prTexture ;
    public Texture boots;
    public Texture flamethrower;

    Resources() {
        hiddenSpikes = new Texture("HiddenSpikes.png");
        flamethrower = new Texture("flameTrap.png");
        spikes = new Texture("Spikes.png");
        Texture texture = new Texture("WallSet.png");
        walls = TextureRegion.split(texture,
                texture.getWidth() / 4,
                texture.getHeight() / 4);
        stoneFloor = walls[3][3];
        portal = new Texture("teleport.png");
        chest = new Texture("chest.png");
        skeletonMage = new Texture("player/skeletonMage.png");
        goblin = new Texture("player/goblin.png");
        allThings = new Texture("all-assets-preview.png");
        mainSword = new TextureRegion(allThings, 512, 64, 16, 16);
        magickBook = new TextureRegion(allThings, 673, 176, 16, 16);
        damagePotion = new TextureRegion(allThings, 31, 64, 16, 16);
        hpPotion = new TextureRegion(allThings, 271, 32, 16, 16);
        speedPotion = new TextureRegion(allThings, 175, 64, 16, 16);
        tripleShot = new TextureRegion(allThings, 528, 80, 16, 16);
        prTexture = new Texture("ProjectileTexture.png");
        boots = new Texture("chain_boots.png");
    }

    void dispose() {
        hiddenSpikes.dispose();
        spikes.dispose();
        allThings.dispose();
        prTexture.dispose();
    }
}
