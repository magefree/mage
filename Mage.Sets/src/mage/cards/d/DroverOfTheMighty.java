
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostSourceWhileControlsEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;

/**
 *
 * @author TheElk801
 */
public final class DroverOfTheMighty extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Dinosaur");

    static {
        filter.add(SubType.DINOSAUR.getPredicate());
    }

    public DroverOfTheMighty(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Drover of the Mighty gets +2/+2 as long as you control a Dinosaur.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostSourceWhileControlsEffect(filter, 2, 2)));

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());
    }

    private DroverOfTheMighty(final DroverOfTheMighty card) {
        super(card);
    }

    @Override
    public DroverOfTheMighty copy() {
        return new DroverOfTheMighty(this);
    }
}
