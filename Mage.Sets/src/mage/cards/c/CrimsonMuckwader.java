
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceWhileControlsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;

/**
 *
 * @author North
 */
public final class CrimsonMuckwader extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Swamp");

    static {
        filter.add(SubType.SWAMP.getPredicate());
    }

    public CrimsonMuckwader(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.LIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Crimson Muckwader gets +1/+1 as long as you control a Swamp.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostSourceWhileControlsEffect(filter, 1, 1)));
        // {2}{B}: Regenerate Crimson Muckwader.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect(), new ManaCostsImpl<>("{2}{B}")));
    }

    private CrimsonMuckwader(final CrimsonMuckwader card) {
        super(card);
    }

    @Override
    public CrimsonMuckwader copy() {
        return new CrimsonMuckwader(this);
    }
}
