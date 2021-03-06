package mage.abilities.effects.keyword;

import mage.abilities.Ability;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.ClueArtifactToken;

/**
 *
 * @author LevelX2
 */
public class InvestigateEffect extends CreateTokenEffect {

    public InvestigateEffect() {
        super(new ClueArtifactToken());
        this.staticText = "investigate. <i>(Create a colorless Clue artifact token with \"{2}, Sacrifice this artifact: Draw a card.\")</i>";
    }

    public InvestigateEffect(final InvestigateEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (super.apply(game, source)) {
            game.fireEvent(GameEvent.getEvent(GameEvent.EventType.INVESTIGATED, source.getSourceId(), source, source.getControllerId()));
            return true;
        }
        return false;
    }

    @Override
    public InvestigateEffect copy() {
        return new InvestigateEffect(this);
    }
}
