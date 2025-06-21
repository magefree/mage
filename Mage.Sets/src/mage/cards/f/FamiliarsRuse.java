package mage.cards.f;

import mage.abilities.costs.common.ReturnToHandChosenControlledPermanentCost;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetSpell;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author Loki
 */
public final class FamiliarsRuse extends CardImpl {

    public FamiliarsRuse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}{U}");

        // As an additional cost to cast Familiar's Ruse, return a creature you control to its owner's hand.
        this.getSpellAbility().addCost(new ReturnToHandChosenControlledPermanentCost(new TargetControlledCreaturePermanent()));

        // Counter target spell.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    private FamiliarsRuse(final FamiliarsRuse card) {
        super(card);
    }

    @Override
    public FamiliarsRuse copy() {
        return new FamiliarsRuse(this);
    }
}
