
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.SetPowerSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 *
 * @author TheElk801
 */
public final class CoilingWoodworm extends CardImpl {

    final static FilterPermanent filterLands = new FilterPermanent("Forests you control");

    static {
        filterLands.add(new SubtypePredicate(SubType.FOREST));
    }

    public CoilingWoodworm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.INSECT);
        this.subtype.add(SubType.WORM);
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // Coiling Woodworm's power is equal to the number of Forests on the battlefield.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetPowerSourceEffect(new PermanentsOnBattlefieldCount(filterLands), Duration.EndOfGame)));
    }

    public CoilingWoodworm(final CoilingWoodworm card) {
        super(card);
    }

    @Override
    public CoilingWoodworm copy() {
        return new CoilingWoodworm(this);
    }
}
