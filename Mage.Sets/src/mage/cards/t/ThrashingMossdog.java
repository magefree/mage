

package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.ScavengeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */


public final class ThrashingMossdog extends CardImpl {

    public ThrashingMossdog (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.PLANT);
        this.subtype.add(SubType.DOG);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Reach
        this.addAbility(ReachAbility.getInstance());
        // Scavenge {4}{G}{G}
        this.addAbility(new ScavengeAbility(new ManaCostsImpl<>("{4}{G}{G}")));

    }

    public ThrashingMossdog (final ThrashingMossdog card) {
        super(card);
    }

    @Override
    public ThrashingMossdog copy() {
        return new ThrashingMossdog(this);
    }

}
