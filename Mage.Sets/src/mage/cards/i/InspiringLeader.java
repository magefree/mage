package mage.cards.i;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InspiringLeader extends CardImpl {

    public InspiringLeader(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.BACKGROUND);

        // Commander creatures you own have "Creature tokens you control get +2/+2."
        this.addAbility(new SimpleStaticAbility(new GainAbilityAllEffect(
                new SimpleStaticAbility(new BoostControlledEffect(
                        2, 2, Duration.WhileOnBattlefield,
                        StaticFilters.FILTER_CREATURE_TOKENS
                )), Duration.WhileOnBattlefield, StaticFilters.FILTER_CREATURES_OWNED_COMMANDER
        )));
    }

    private InspiringLeader(final InspiringLeader card) {
        super(card);
    }

    @Override
    public InspiringLeader copy() {
        return new InspiringLeader(this);
    }
}
