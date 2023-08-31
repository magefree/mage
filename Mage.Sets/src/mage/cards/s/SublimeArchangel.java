
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.ExaltedAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.StaticFilters;

/**
 *
 * @author North
 */
public final class SublimeArchangel extends CardImpl {

    public SublimeArchangel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{W}");
        this.subtype.add(SubType.ANGEL);

        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Exalted
        this.addAbility(new ExaltedAbility());
        // Other creatures you control have exalted.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new GainAbilityControlledEffect(new ExaltedAbility(), Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_CREATURES, true)));
    }

    private SublimeArchangel(final SublimeArchangel card) {
        super(card);
    }

    @Override
    public SublimeArchangel copy() {
        return new SublimeArchangel(this);
    }
}
