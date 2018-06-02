
package mage.cards.z;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.abilities.keyword.LandwalkAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.mageobject.SupertypePredicate;

/**
 *
 * @author TheElk801
 */
public final class ZombieMusher extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent("snow land");

    static {
        filter.add(new SupertypePredicate(SuperType.SNOW));
    }

    public ZombieMusher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.addSuperType(SuperType.SNOW);
        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Snow landwalk
        this.addAbility(new LandwalkAbility(filter));

        // {snow}: Regenerate Zombie Musher.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect(), new ManaCostsImpl("{S}")));
    }

    public ZombieMusher(final ZombieMusher card) {
        super(card);
    }

    @Override
    public ZombieMusher copy() {
        return new ZombieMusher(this);
    }
}
