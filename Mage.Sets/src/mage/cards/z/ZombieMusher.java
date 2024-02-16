package mage.cards.z;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.abilities.keyword.LandwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterControlledLandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ZombieMusher extends CardImpl {

    private static final FilterControlledLandPermanent filter = new FilterControlledLandPermanent("snow land");

    static {
        filter.add(SuperType.SNOW.getPredicate());
    }

    public ZombieMusher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.supertype.add(SuperType.SNOW);
        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Snow landwalk
        this.addAbility(new LandwalkAbility(filter));

        // {snow}: Regenerate Zombie Musher.
        this.addAbility(new SimpleActivatedAbility(new RegenerateSourceEffect(), new ManaCostsImpl<>("{S}")));
    }

    private ZombieMusher(final ZombieMusher card) {
        super(card);
    }

    @Override
    public ZombieMusher copy() {
        return new ZombieMusher(this);
    }
}
