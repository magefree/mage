package mage.cards.w;

import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WarleadersCall extends CardImpl {

    public WarleadersCall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}{W}");

        // Creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield)));

        // Whenever a creature enters the battlefield under your control, Warleader's Call deals 1 damage to each opponent.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                new DamagePlayersEffect(1, TargetController.OPPONENT),
                StaticFilters.FILTER_PERMANENT_A_CREATURE
        ));
    }

    private WarleadersCall(final WarleadersCall card) {
        super(card);
    }

    @Override
    public WarleadersCall copy() {
        return new WarleadersCall(this);
    }
}
