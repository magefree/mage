/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.util.functions;

import mage.MageObject;
import mage.abilities.Ability;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public class AddSubtypeApplier extends ApplyToPermanent {

    private final SubType subtype;

    public AddSubtypeApplier(SubType subtype) {
        this.subtype = subtype;
    }

    @Override
    public boolean apply(Game game, Permanent blueprint, Ability source, UUID copyToObjectId) {
        if (!blueprint.hasSubtype(subtype, game)) {
            blueprint.addSubType(game, subtype);
        }
        return true;
    }

    @Override
    public boolean apply(Game game, MageObject blueprint, Ability source, UUID copyToObjectId) {
        if (!blueprint.hasSubtype(subtype, game)) {
            blueprint.addSubType(game, subtype);
        }
        return true;
    }

}
