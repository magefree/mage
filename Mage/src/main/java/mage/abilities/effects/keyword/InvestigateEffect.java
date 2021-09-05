package mage.abilities.effects.keyword;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.ClueArtifactToken;
import mage.util.CardUtil;

/**
 * @author LevelX2
 */
public class InvestigateEffect extends OneShotEffect {

    private final int amount;

    public InvestigateEffect() {
        this(1);
    }

    public InvestigateEffect(int amount) {
        super(Outcome.Benefit);
        this.amount = amount;
    }

    public InvestigateEffect(final InvestigateEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        new ClueArtifactToken().putOntoBattlefield(amount, game, source, source.getControllerId());
        for (int i = 0; i < amount; i++) {
            game.fireEvent(GameEvent.getEvent(GameEvent.EventType.INVESTIGATED, source.getSourceId(), source, source.getControllerId()));
        }
        return true;
    }

    @Override
    public InvestigateEffect copy() {
        return new InvestigateEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        String message;
        switch (amount) {
            case 1:
                message = ". <i>(C";
                break;
            case 2:
                message = "twice. <i>(To investigate, c";
                break;
            default:
                message = CardUtil.numberToText(amount) + " times. <i>(To investigate, c";
        }
        return "investigate " + message + "reate a colorless Clue artifact token " +
                "with \"{2}, Sacrifice this artifact: Draw a card.\")</i>";
    }
}
