
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HorsemanshipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

/**
 *
 * @author LevelX2
 */
public final class SunQuanLordOfWu extends CardImpl {

    public SunQuanLordOfWu(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Creatures you control have horsemanship.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityControlledEffect(HorsemanshipAbility.getInstance(),
                Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_CREATURES)));
    }

    private SunQuanLordOfWu(final SunQuanLordOfWu card) {
        super(card);
    }

    @Override
    public SunQuanLordOfWu copy() {
        return new SunQuanLordOfWu(this);
    }
}
