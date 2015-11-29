/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.util.functions;

import mage.MageObject;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public class AddSubtypeApplier extends ApplyToPermanent {

    private final String subtype;

    public AddSubtypeApplier(String subtype) {
        this.subtype = subtype;
    }

    @Override
    public Boolean apply(Game game, Permanent permanent) {
        if (!permanent.getSubtype().contains(subtype)) {
            permanent.getSubtype().add(subtype);
        }
        return true;
    }

    @Override
    public Boolean apply(Game game, MageObject mageObject) {
        if (!mageObject.getSubtype().contains(subtype)) {
            mageObject.getSubtype().add(subtype);
        }
        return true;
    }

}
