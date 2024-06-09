package mage.cards.p;

import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PippinsBravery extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.FOOD, "Food");

    public PippinsBravery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{G}");

        // You may sacrifice a Food. If you do, target creature gets +4/+4 until end of turn. Otherwise, that creature gets +2/+2 until end of turn.
        this.getSpellAbility().addEffect(new DoIfCostPaid(
                new BoostTargetEffect(4, 4),
                new BoostTargetEffect(2, 2),
                new SacrificeTargetCost(filter)
        ));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private PippinsBravery(final PippinsBravery card) {
        super(card);
    }

    @Override
    public PippinsBravery copy() {
        return new PippinsBravery(this);
    }
}
