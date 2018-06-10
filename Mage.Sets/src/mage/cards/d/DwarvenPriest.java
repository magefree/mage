
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

/**
 *
 * @author TheElk801
 */
public final class DwarvenPriest extends CardImpl {

    public DwarvenPriest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // When Dwarven Priest enters the battlefield, you gain 1 life for each creature you control.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GainLifeEffect(
                new PermanentsOnBattlefieldCount(StaticFilters.FILTER_CONTROLLED_CREATURE)
        )));
    }

    public DwarvenPriest(final DwarvenPriest card) {
        super(card);
    }

    @Override
    public DwarvenPriest copy() {
        return new DwarvenPriest(this);
    }
}
