package mage.cards.p;

import java.util.UUID;

import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author muz
 */
public final class PlasmaCascade extends CardImpl {

    public PlasmaCascade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");

        // Plasma Cascade deals 3 damage to any target. You may discard a card. If you do, draw a card.
        this.getSpellAbility().addTarget(new TargetAnyTarget());
        this.getSpellAbility().addEffect(new DamageTargetEffect(3));
        this.getSpellAbility().addEffect(new DoIfCostPaid(
            new DrawCardSourceControllerEffect(1), new DiscardCardCost()
        ));
    }

    private PlasmaCascade(final PlasmaCascade card) {
        super(card);
    }

    @Override
    public PlasmaCascade copy() {
        return new PlasmaCascade(this);
    }
}
