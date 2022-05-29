package com.uki.mariobros.tools;

import com.badlogic.gdx.physics.box2d.*;
import com.uki.mariobros.sprites.Enemy;
import com.uki.mariobros.sprites.InteractiveTileObject;

import static com.uki.mariobros.MarioBros.*;

public class WorldContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {

        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        int cDef = fixtureA.getFilterData().categoryBits |  fixtureB.getFilterData().categoryBits;

        //rewrite for mario head for cdef
        if (fixtureA.getUserData() == "head" || fixtureB.getUserData() == "head") {
            Fixture head = fixtureA.getUserData() == "head" ? fixtureA : fixtureB;
            Fixture object = head == fixtureA ? fixtureB : fixtureA;

            if(object.getUserData() instanceof InteractiveTileObject){
                ((InteractiveTileObject) object.getUserData()).onHeadHit();
            }
        }

        switch (cDef){
            case ENEMY_HEAD_BIT | MARIO_BIT:
                if(fixtureA.getFilterData().categoryBits == ENEMY_HEAD_BIT)
                    ((Enemy) fixtureA.getUserData()).onHeadHit();
                else if(fixtureB.getFilterData().categoryBits == ENEMY_HEAD_BIT)
                    ((Enemy) fixtureB.getUserData()).onHeadHit();
                break;
            case ENEMY_BIT | OBJECT_BIT:
                if(fixtureA.getFilterData().categoryBits == ENEMY_BIT)
                    ((Enemy) fixtureA.getUserData()).reverseVelocity(true,false);
                else if(fixtureB.getFilterData().categoryBits == ENEMY_BIT)
                    ((Enemy) fixtureB.getUserData()).reverseVelocity(true,false);
                break;
            case MARIO_BIT | ENEMY_BIT:
                System.out.println("Mario died");
                break;
            case ENEMY_BIT:
                ((Enemy) fixtureA.getUserData()).reverseVelocity(true,false);
                ((Enemy) fixtureB.getUserData()).reverseVelocity(true,false);
                break;

        }

    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
