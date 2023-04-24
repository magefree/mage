
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.continuous.LoseAbilitySourceEffect;
import mage.abilities.keyword.DefenderAbility;
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
public final class TorpidMoloch extends CardImpl {

    public TorpidMoloch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}");
        this.subtype.add(SubType.LIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Defender
        this.addAbility(DefenderAbility.getInstance());
        
        // Sacrifice three lands: Torpid Moloch loses defender until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                Zone.BATTLEFIELD, 
                new LoseAbilitySourceEffect(DefenderAbility.getInstance(), Duration.EndOfTurn), 
                new SacrificeTargetCost(new TargetControlledPermanent(3, 3, new FilterControlledLandPermanent("lands"), true))));
    }

    private TorpidMoloch(final TorpidMoloch card) {
        super(card);
    }

    @Override
    public TorpidMoloch copy() {
        return new TorpidMoloch(this);
    }
}
