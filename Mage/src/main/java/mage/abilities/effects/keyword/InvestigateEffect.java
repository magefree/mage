package mage.abilities.effects.keyword;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.ClueArtifactToken;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author LevelX2
 */
public class InvestigateEffect extends OneShotEffect {

    private final DynamicValue amount;

    public InvestigateEffect() {
        this(1);
    }

    public InvestigateEffect(int amount) {
        this(StaticValue.get(amount));
    }

    public InvestigateEffect(DynamicValue amount) {
        super(Outcome.Benefit);
        this.amount = amount;
        this.staticText = makeText();
    }

    protected InvestigateEffect(final InvestigateEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int value = this.amount.calculate(game, source, this);
        if (value > 0) {
            doInvestigate(source.getControllerId(), value, game, source);
            return true;
        }
        return false;
    }

    public static void doInvestigate(UUID playerId, int value, Game game, Ability source) {
        new ClueArtifactToken().putOntoBattlefield(value, game, source, playerId);
        for (int i = 0; i < value; i++) {
            game.fireEvent(GameEvent.getEvent(
                    GameEvent.EventType.INVESTIGATED,
                    source.getSourceId(), source, playerId
            ));
        }
    }

    @Override
    public InvestigateEffect copy() {
        return new InvestigateEffect(this);
    }

    private String makeText() {
        String message;
        if (amount instanceof StaticValue) {
            int value = ((StaticValue) amount).getValue();
            switch (value) {
                case 1:
                    message = ". <i>(C";
                    break;
                case 2:
                    message = " twice. <i>(To investigate, c";
                    break;
                default:
                    message = ' ' + CardUtil.numberToText(value) + " times. <i>(To investigate, c";
            }
        } else {
            message = " X times, where X is the " + amount.getMessage() + ". <i>(To investigate, c";
        }
        return "investigate" + message + "reate a Clue token. " +
                "It's an artifact with \"{2}, Sacrifice this artifact: Draw a card.\")</i>";
    }
}
