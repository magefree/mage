
package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.SetBasePowerSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CoilingWoodworm extends CardImpl {

    private static final DynamicValue count = new PermanentsOnBattlefieldCount(
            new FilterPermanent(SubType.FOREST, "Forests on the battlefield")
    );

    public CoilingWoodworm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.INSECT);
        this.subtype.add(SubType.WORM);
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // Coiling Woodworm's power is equal to the number of Forests on the battlefield.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerSourceEffect(count)));
    }

    private CoilingWoodworm(final CoilingWoodworm card) {
        super(card);
    }

    @Override
    public CoilingWoodworm copy() {
        return new CoilingWoodworm(this);
    }
}
