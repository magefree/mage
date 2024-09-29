package mage.cards.t;

import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TakeOutTheTrash extends CardImpl {

    private static final Condition condition
            = new PermanentsOnTheBattlefieldCondition(new FilterControlledPermanent(SubType.RACCOON));
    private static final Hint hint = new ConditionHint(condition, "You control a Raccoon");

    public TakeOutTheTrash(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Take Out the Trash deals 3 damage to target creature or planeswalker. If you control a Raccoon, you may discard a card. If you do, draw a card.
        this.getSpellAbility().addEffect(new DamageTargetEffect(3));
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DoIfCostPaid(new DrawCardSourceControllerEffect(1), new DiscardCardCost()),
                condition, "if you control a Raccoon, you may discard a card. If you do, draw a card"
        ));
        this.getSpellAbility().addHint(hint);
    }

    private TakeOutTheTrash(final TakeOutTheTrash card) {
        super(card);
    }

    @Override
    public TakeOutTheTrash copy() {
        return new TakeOutTheTrash(this);
    }
}
