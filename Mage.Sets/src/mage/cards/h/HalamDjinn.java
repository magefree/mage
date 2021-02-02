
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MostCommonColorCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author TheElk801
 */
public final class HalamDjinn extends CardImpl {

    public HalamDjinn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{R}");

        this.subtype.add(SubType.DJINN);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Halam Djinn gets -2/-2 as long as red is the most common color among all permanents or is tied for most common.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(new BoostSourceEffect(-2, -2, Duration.WhileOnBattlefield),
                        new MostCommonColorCondition(new ObjectColor(ObjectColor.RED)),
                        "{this} gets -2/-2 as long as red is the most common color among all permanents or is tied for most common")));
    }

    private HalamDjinn(final HalamDjinn card) {
        super(card);
    }

    @Override
    public HalamDjinn copy() {
        return new HalamDjinn(this);
    }
}
