package mage.cards.a;

import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.EffectKeyValue;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AmazingAlliance extends CardImpl {

    private static final DynamicValue xValue = new EffectKeyValue(
            AttacksWithCreaturesTriggeredAbility.VALUEKEY_NUMBER_ATTACKERS, "that much"
    );

    public AmazingAlliance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}{W}");

        // Creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield)));

        // Whenever you attack with one or more legendary creatures, you gain that much life.
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(
                new GainLifeEffect(xValue), 1, StaticFilters.FILTER_CREATURES_LEGENDARY
        ));
    }

    private AmazingAlliance(final AmazingAlliance card) {
        super(card);
    }

    @Override
    public AmazingAlliance copy() {
        return new AmazingAlliance(this);
    }
}
