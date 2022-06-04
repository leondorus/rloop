package org.rloop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import org.rloop.Screens.GameScreen;
import org.rloop.Tiles.Wall;

//@author Marcin Kozik, Andrei Daletski

public class GameContactListener implements ContactListener {



    rloop game;
    public GameContactListener(rloop game){
        this.game = game;

    }
    @Override
    public void beginContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        //обработать хуйню
        if(fa == null || fb == null){
            return;
        }
        if(fa.getUserData() == null || fb.getUserData() == null){
            return;
        }

        //Урон монстрам от проджектайла
        if(     (fa.getUserData().getClass() == RangeWeaponProjectile.class && fb.getUserData().getClass() == ChasingMonster.class)
                || (fb.getUserData().getClass() == RangeWeaponProjectile.class && fa.getUserData().getClass() == ChasingMonster.class)) {
            RangeWeaponProjectile curProjectile;
            ChasingMonster curMonster;
            if (fb.getUserData().getClass() == RangeWeaponProjectile.class) {
                curProjectile = (RangeWeaponProjectile) fb.getUserData();
                curMonster = (ChasingMonster) fa.getUserData();
            } else {
                curProjectile = (RangeWeaponProjectile) fa.getUserData();
                curMonster = (ChasingMonster) fb.getUserData();
            }
            if (!curMonster.isImmune()) {
                curMonster.getHit(curProjectile.damageMonst);
                game.getOurMusic().monsterDmgSound.play(game.getOurMusic().getSoundVolume());
                curMonster.makeImmune();
                game.mainScreen.monstersDied.add(curProjectile);
            }
            else{
                game.mainScreen.monstersDied.add(curProjectile);
            }
        }
        else if((fa.getUserData().getClass() == RangeWeaponProjectile.class && fb.getUserData().getClass() == ShootingMonster.class) || (fb.getUserData().getClass() == RangeWeaponProjectile.class && fa.getUserData().getClass() == ShootingMonster.class)) {
            RangeWeaponProjectile curProjectile;
            ShootingMonster curMonster;
            if (fb.getUserData().getClass() == RangeWeaponProjectile.class) {
                curProjectile = (RangeWeaponProjectile) fb.getUserData();
                curMonster = (ShootingMonster) fa.getUserData();
            } else {
                curProjectile = (RangeWeaponProjectile) fa.getUserData();
                curMonster = (ShootingMonster) fb.getUserData();
            }
            if (!curMonster.isImmune()) {
                curMonster.getHit(curProjectile.damageMonst);
                game.getOurMusic().monsterDmgSound.play(game.getOurMusic().getSoundVolume());
                curMonster.makeImmune();
                game.mainScreen.monstersDied.add(curProjectile);
            }
            else{
                game.mainScreen.monstersDied.add(curProjectile);
            }
        }
        else if((fa.getUserData().getClass() == RangeWeaponProjectile.class && fb.getUserData().getClass() == Wall.class) || (fb.getUserData().getClass() == RangeWeaponProjectile.class && fa.getUserData().getClass() == Wall.class)) {
            RangeWeaponProjectile curProjectile;
            if (fb.getUserData().getClass() == RangeWeaponProjectile.class) {
                curProjectile = (RangeWeaponProjectile) fb.getUserData();
            } else {
                curProjectile = (RangeWeaponProjectile) fa.getUserData();
            }
            game.mainScreen.monstersDied.add(curProjectile);

        } else if (((fa.getUserData() instanceof ShootingMonsterProjectile && fb.getUserData() instanceof Wall)
                || (fb.getUserData() instanceof ShootingMonsterProjectile && fa.getUserData() instanceof Wall))) {
                Wall curWall;
                ShootingMonsterProjectile curMonster;
                if(fb.getUserData().getClass() == Wall.class) {
                    curWall = (Wall) fb.getUserData();
                    curMonster = (ShootingMonsterProjectile) fa.getUserData();
                }
                else{
                    curWall = (Wall) fa.getUserData();
                    curMonster = (ShootingMonsterProjectile) fb.getUserData();
                }

//                curMonster.getBody().getWorld().destroyBody(curMonster.getBody());
                game.mainScreen.monstersDied.add(curMonster);
        } else if ((fa.getUserData() instanceof Player && fb.getUserData() instanceof DamageMakerPlayer)
                || (fb.getUserData() instanceof Player && fa.getUserData() instanceof DamageMakerPlayer)) {
            Player player;
            DamageMakerPlayer damageMakerPlayer;
            if (fa.getUserData() instanceof Player) {
                player = (Player) fa.getUserData();
                damageMakerPlayer = (DamageMakerPlayer) fb.getUserData();
            } else {
                player = (Player) fb.getUserData();
                damageMakerPlayer = (DamageMakerPlayer) fa.getUserData();
            }
            player.addDamageMaker(damageMakerPlayer);
            if(damageMakerPlayer instanceof ShootingMonsterProjectile && ! (damageMakerPlayer instanceof RangeWeaponProjectile)){
                game.mainScreen.monstersDied.add((Monster) damageMakerPlayer);
            }
        } else if((fa.getUserData() instanceof MeleeWeaponProjectile && fb.getUserData() instanceof ShootingMonsterProjectile) || (fb.getUserData() instanceof MeleeWeaponProjectile && fa.getUserData() instanceof ShootingMonsterProjectile)) {
            MeleeWeaponProjectile curMelee;
            ShootingMonsterProjectile curRange;
            if (fa.getUserData() instanceof MeleeWeaponProjectile) {
                curMelee = (MeleeWeaponProjectile) fa.getUserData();
                curRange = (ShootingMonsterProjectile) fb.getUserData();
            } else {
                curMelee = (MeleeWeaponProjectile) fb.getUserData();
                curRange = (ShootingMonsterProjectile) fa.getUserData();
            }
            game.mainScreen.monstersDied.add((Monster) curRange);
        } else if((fa.getUserData() instanceof MeleeWeaponProjectile && fb.getUserData() instanceof ChasingMonster) || (fb.getUserData() instanceof MeleeWeaponProjectile && fa.getUserData() instanceof ChasingMonster)){
            MeleeWeaponProjectile curMelee;
            ChasingMonster curMonster;
            if (fa.getUserData() instanceof MeleeWeaponProjectile) {
                curMelee = (MeleeWeaponProjectile) fa.getUserData();
                curMonster = (ChasingMonster) fb.getUserData();
            } else {
                curMelee = (MeleeWeaponProjectile) fb.getUserData();
                curMonster = (ChasingMonster) fa.getUserData();
            }
            if (!curMonster.isImmune()) {
                curMonster.getHit(curMelee.myWeapon.weaponDamage);
                game.getOurMusic().monsterDmgSound.play(game.getOurMusic().getSoundVolume());
                curMonster.makeImmune();
            }
        } else if((fa.getUserData() instanceof MeleeWeaponProjectile && fb.getUserData() instanceof ShootingMonster) || (fb.getUserData() instanceof MeleeWeaponProjectile && fa.getUserData() instanceof ShootingMonster)){
            MeleeWeaponProjectile curMelee;
            ShootingMonster curMonster;
            if (fa.getUserData() instanceof MeleeWeaponProjectile) {
                curMelee = (MeleeWeaponProjectile) fa.getUserData();
                curMonster = (ShootingMonster) fb.getUserData();
            } else {
                curMelee = (MeleeWeaponProjectile) fb.getUserData();
                curMonster = (ShootingMonster) fa.getUserData();
            }
            if (!curMonster.isImmune()) {
                curMonster.getHit(curMelee.myWeapon.weaponDamage);
                game.getOurMusic().monsterDmgSound.play(game.getOurMusic().getSoundVolume());
                curMonster.makeImmune();
            }
        } else if((fa.getUserData() instanceof Portal && fb.getUserData() instanceof Player) || (fa.getUserData() instanceof Player && fb.getUserData() instanceof Portal)){
            if(fa.getUserData() instanceof Portal){
                game.setScreen(game.mainScreen = new GameScreen(game, ((Portal) fa.getUserData()).gameScreen.choosenWeapon));
            }else{
                game.setScreen(game.mainScreen = new GameScreen(game, ((Portal) fb.getUserData()).gameScreen.choosenWeapon));
            }
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        //обработать хуйню
        if(fa == null || fb == null){
            return;
        }
        if(fa.getUserData() == null || fb.getUserData() == null){
            return;
        }

        if (fa.getUserData() instanceof Player && fb.getUserData() instanceof DamageMakerPlayer || fb.getUserData() instanceof Player && fa.getUserData() instanceof DamageMakerPlayer) {
            Player player;
            DamageMakerPlayer damageMaker;
            if (fa.getUserData() instanceof Player) {
                player = (Player) fa.getUserData();
                damageMaker = (DamageMakerPlayer) fb.getUserData();
            } else {
                player = (Player) fb.getUserData();
                damageMaker = (DamageMakerPlayer) fa.getUserData();
            }
            player.removeDamageMaker(damageMaker);
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();
        if(fa == null || fb == null){
            return;
        }
        if(fa.getUserData() == null || fb.getUserData() == null){
            return;
        }
        if((fa.getUserData().getClass() == ShootingMonsterProjectile.class && fb.getUserData() instanceof Monster)
                || (fb.getUserData().getClass() == ShootingMonsterProjectile.class && fa.getUserData() instanceof  Monster)){
            contact.setEnabled(false);
        }
        else if ((fa.getUserData().getClass() == RangeWeaponProjectile.class && fb.getUserData().getClass() == Player.class) || (fb.getUserData().getClass() == RangeWeaponProjectile.class && fa.getUserData().getClass() == Player.class)){
            contact.setEnabled(false);
        }
        else if ((fa.getUserData().getClass() == RangeWeaponProjectile.class && fb.getUserData().getClass() == RangeWeaponProjectile.class)){
            contact.setEnabled(false);
        }
        else if ((fa.getUserData().getClass() == RangeWeaponProjectile.class && fb.getUserData().getClass() == ShootingMonsterProjectile.class) || (fb.getUserData().getClass() == RangeWeaponProjectile.class && fa.getUserData().getClass() == ShootingMonsterProjectile.class)){
            contact.setEnabled(false);
        }
        else if((fa.getUserData() instanceof Player && fb.getUserData() instanceof MeleeWeaponProjectile) || (fb.getUserData() instanceof Player && fa.getUserData() instanceof MeleeWeaponProjectile)){
            contact.setEnabled(false);
        }
        else if((fa.getUserData() instanceof MeleeWeaponProjectile && fb.getUserData() instanceof MeleeWeaponProjectile)){
            contact.setEnabled(false);
        }
        else if((fa.getUserData() instanceof  MeleeWeaponProjectile && fb.getUserData() instanceof Wall) || (fb.getUserData() instanceof  MeleeWeaponProjectile && fa.getUserData() instanceof Wall) ){
            contact.setEnabled(false);
        }

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}