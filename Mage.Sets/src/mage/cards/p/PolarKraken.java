
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author Plopman
 */
public final class PolarKraken extends CardImpl {

    public PolarKraken(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{8}{U}{U}{U}");
        this.subtype.add(SubType.KRAKEN);

        this.power = new MageInt(11);
        this.toughness = new MageInt(11);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // Polar Kraken enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
        // Cumulative upkeep-Sacrifice a land.
        this.addAbility(new CumulativeUpkeepAbility(new SacrificeTargetCost(new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_LAND_SHORT_TEXT))));
    }

    private PolarKraken(final PolarKraken card) {
        super(card);
    }

    @Override
    public PolarKraken copy() {
        return new PolarKraken(this);
    }
}
