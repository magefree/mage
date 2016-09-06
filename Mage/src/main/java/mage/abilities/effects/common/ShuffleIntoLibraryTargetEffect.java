/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author Styxo
 */
public class ShuffleIntoLibraryTargetEffect extends OneShotEffect {

    public ShuffleIntoLibraryTargetEffect() {
        super(Outcome.Detriment);
    }

    public ShuffleIntoLibraryTargetEffect(String effectText) {
        super(Outcome.Detriment);
        this.staticText = effectText;
    }

    public ShuffleIntoLibraryTargetEffect(final ShuffleIntoLibraryTargetEffect effect) {
        super(effect);
    }

    @Override
    public ShuffleIntoLibraryTargetEffect copy() {
        return new ShuffleIntoLibraryTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(targetPointer.getFirst(game, source));
        if (permanent != null) {
            if (permanent.moveToZone(Zone.LIBRARY, source.getSourceId(), game, true)) {
                game.getPlayer(permanent.getOwnerId()).shuffleLibrary(source, game);
                return true;
            }
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        } else {
            return "choose target " + mode.getTargets().get(0).getTargetName() + ". Its owner shuffles it into his or her library";
        }
    }
}
