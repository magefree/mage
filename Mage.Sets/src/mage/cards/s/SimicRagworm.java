
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author LoneFox
 */
public final class SimicRagworm extends CardImpl {

    public SimicRagworm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.WORM);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {U}: Untap Simic Ragworm.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new UntapSourceEffect(), new ManaCostsImpl<>("{U}")));
    }

    private SimicRagworm(final SimicRagworm card) {
        super(card);
    }

    @Override
    public SimicRagworm copy() {
        return new SimicRagworm(this);
    }
}
