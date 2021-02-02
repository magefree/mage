
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterControlledLandPermanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author fireshoes
 */
public final class TrenchingSteed extends CardImpl {

    public TrenchingSteed(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add(SubType.HORSE);
        this.subtype.add(SubType.REBEL);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Sacrifice a land: Trenching Steed gets +0/+3 until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                Zone.BATTLEFIELD, 
                new BoostSourceEffect(0, 3, Duration.EndOfTurn), 
                new SacrificeTargetCost(new TargetControlledPermanent(new FilterControlledLandPermanent("land")))));
    }

    private TrenchingSteed(final TrenchingSteed card) {
        super(card);
    }

    @Override
    public TrenchingSteed copy() {
        return new TrenchingSteed(this);
    }
}
