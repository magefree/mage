
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

/**
 *
 * @author Plopman
 */
public final class CosmicLarva extends CardImpl {

    public CosmicLarva(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}{R}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(7);
        this.toughness = new MageInt(6);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // At the beginning of your upkeep, sacrifice Cosmic Larva unless you sacrifice two lands.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new SacrificeSourceUnlessPaysEffect(
                new SacrificeTargetCost(2, StaticFilters.FILTER_LANDS))));
    }

    private CosmicLarva(final CosmicLarva card) {
        super(card);
    }

    @Override
    public CosmicLarva copy() {
        return new CosmicLarva(this);
    }
}
