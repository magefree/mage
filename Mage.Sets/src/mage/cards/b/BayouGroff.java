package mage.cards.b;

import mage.MageInt;
import mage.abilities.costs.OrCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BayouGroff extends CardImpl {

    public BayouGroff(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.PLANT);
        this.subtype.add(SubType.DOG);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // As an additional cost to cast this spell, sacrifice a creature or pay {3}.
        this.getSpellAbility().addCost(new OrCost(
                "sacrifice a creature or pay {3}", new SacrificeTargetCost(new TargetControlledPermanent(
                        StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT
                )), new GenericManaCost(3)
        ));
    }

    private BayouGroff(final BayouGroff card) {
        super(card);
    }

    @Override
    public BayouGroff copy() {
        return new BayouGroff(this);
    }
}
