
package mage.cards.z;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MostCommonColorCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
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
public final class ZanamDjinn extends CardImpl {

    public ZanamDjinn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}");

        this.subtype.add(SubType.DJINN);
        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Zanam Djinn gets -2/-2 as long as blue is the most common color among all permanents or is tied for most common.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(new BoostSourceEffect(-2, -2, Duration.WhileOnBattlefield),
                        new MostCommonColorCondition(new ObjectColor(ObjectColor.BLUE)),
                        "{this} gets -2/-2 as long as blue is the most common color among all permanents or is tied for most common")));
    }

    private ZanamDjinn(final ZanamDjinn card) {
        super(card);
    }

    @Override
    public ZanamDjinn copy() {
        return new ZanamDjinn(this);
    }
}
