package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IllusionOfChoice extends CardImpl {

    public IllusionOfChoice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}");

        // You choose how each player votes this turn.
        this.getSpellAbility().addEffect(new IllusionOfChoiceReplacementEffect());

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private IllusionOfChoice(final IllusionOfChoice card) {
        super(card);
    }

    @Override
    public IllusionOfChoice copy() {
        return new IllusionOfChoice(this);
    }
}

class IllusionOfChoiceReplacementEffect extends ContinuousRuleModifyingEffectImpl {

    IllusionOfChoiceReplacementEffect() {
        super(Duration.EndOfTurn, Outcome.Benefit);
        staticText = "you choose how each player votes this turn";
    }

    private IllusionOfChoiceReplacementEffect(final IllusionOfChoiceReplacementEffect effect) {
        super(effect);
    }

    @Override
    public IllusionOfChoiceReplacementEffect copy() {
        return new IllusionOfChoiceReplacementEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.VOTE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        event.setPlayerId(source.getControllerId());
        return false;
    }
}
