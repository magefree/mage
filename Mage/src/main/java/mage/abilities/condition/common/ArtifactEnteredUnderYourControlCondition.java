package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.watchers.common.ArtifactEnteredControllerWatcher;

/**
 * /!\ You need to add ArtifactEnteredControllerWatcher to any card using this condition
 *
 * @author Cguy7777
 */
public enum ArtifactEnteredUnderYourControlCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return ArtifactEnteredControllerWatcher.enteredArtifactForPlayer(source.getControllerId(), game);
    }

    @Override
    public String toString() {
        return "an artifact entered the battlefield under your control this turn";
    }
}
