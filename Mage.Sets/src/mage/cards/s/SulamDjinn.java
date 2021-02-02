
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MostCommonColorCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.TrampleAbility;
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
public final class SulamDjinn extends CardImpl {

    public SulamDjinn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}");

        this.subtype.add(SubType.DJINN);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Sulam Djinn gets -2/-2 as long as green is the most common color among all permanents or is tied for most common.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(new BoostSourceEffect(-2, -2, Duration.WhileOnBattlefield),
                        new MostCommonColorCondition(new ObjectColor(ObjectColor.GREEN)),
                        "{this} gets -2/-2 as long as green is the most common color among all permanents or is tied for most common")));
    }

    private SulamDjinn(final SulamDjinn card) {
        super(card);
    }

    @Override
    public SulamDjinn copy() {
        return new SulamDjinn(this);
    }
}
