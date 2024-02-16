
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.ControlsPermanentsControllerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.combat.CantAttackUnlessDefenderControllsPermanent;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Zone;
import mage.filter.common.FilterLandPermanent;

/**
 *
 * @author TheElk801
 */
public final class RonomSerpent extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent("a snow land");
    private static final FilterLandPermanent filter2 = new FilterLandPermanent("no snow lands");

    static {
        filter.add(SuperType.SNOW.getPredicate());
        filter2.add(SuperType.SNOW.getPredicate());
    }

    public RonomSerpent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}");

        this.supertype.add(SuperType.SNOW);
        this.subtype.add(SubType.SERPENT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        // Ronom Serpent can't attack unless defending player controls a snow land.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantAttackUnlessDefenderControllsPermanent(filter)));

        // When you control no snow lands, sacrifice Ronom Serpent.
        this.addAbility(new ControlsPermanentsControllerTriggeredAbility(
                filter2, ComparisonType.EQUAL_TO, 0, new SacrificeSourceEffect()
        ));
    }

    private RonomSerpent(final RonomSerpent card) {
        super(card);
    }

    @Override
    public RonomSerpent copy() {
        return new RonomSerpent(this);
    }
}
