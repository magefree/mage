package mage.cards.s;

import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class Scapegoat extends CardImpl {

    public Scapegoat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        // As an additional cost to cast Scapegoat, sacrifice a creature.
        this.getSpellAbility().addCost(new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_CREATURE));

        // Return any number of target creatures you control to their owner's hand.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect().setText("Return any number of target creatures you control to their owner's hand"));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent(0, Integer.MAX_VALUE));
    }

    private Scapegoat(final Scapegoat card) {
        super(card);
    }

    @Override
    public Scapegoat copy() {
        return new Scapegoat(this);
    }
}
