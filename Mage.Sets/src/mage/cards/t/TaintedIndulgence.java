package mage.cards.t;

import mage.abilities.condition.Condition;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.DifferentManaValuesInGraveCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.abilities.hint.common.DifferentManaValuesInGraveHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TaintedIndulgence extends CardImpl {

    private static final Condition condition = new InvertCondition(DifferentManaValuesInGraveCondition.FIVE);

    public TaintedIndulgence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}{R}");

        // Draw two cards. Then discard a card unless there are five or more mana values among cards in your graveyard.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DiscardControllerEffect(1), condition, "Then discard a card " +
                "unless there are five or more mana values among cards in your graveyard"
        ));
        this.getSpellAbility().addHint(DifferentManaValuesInGraveHint.instance);
    }

    private TaintedIndulgence(final TaintedIndulgence card) {
        super(card);
    }

    @Override
    public TaintedIndulgence copy() {
        return new TaintedIndulgence(this);
    }
}
