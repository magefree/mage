package mage.abilities.hint.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.ArtifactsYouControlCount;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.game.Game;

/**
 * @author JayDi85
 */
public enum ArtifactsYouControlHint implements Hint {

    instance;
    private static final Hint hint = new ValueHint("Artifacts you control", ArtifactsYouControlCount.instance);

    @Override
    public String getText(Game game, Ability ability) {
        return hint.getText(game, ability);
    }

    @Override
    public Hint copy() {
        return instance;
    }
}
