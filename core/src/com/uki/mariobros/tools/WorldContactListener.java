package com.uki.mariobros.tools;

import com.badlogic.gdx.physics.box2d.*;
import com.uki.mariobros.sprites.Enemy;
import com.uki.mariobros.sprites.InteractiveTileObject;

import static com.uki.mariobros.MarioBros.ENEMY_HEAD_BIT;
import static com.uki.mariobros.MarioBros.MARIO_BIT;

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
                    ((Enemy) fixtureA.getUserData()).onHeadHit();
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
