
package mage.cards.v;

import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class ValorInAkros extends CardImpl {

    public ValorInAkros(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}");

        // Whenever a creature enters the battlefield under your control, creatures you control get +1/+1 until end of turn.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                Zone.BATTLEFIELD,
                new BoostControlledEffect(1, 1, Duration.EndOfTurn),
                StaticFilters.FILTER_PERMANENT_A_CREATURE,
                false)
        );
    }

    private ValorInAkros(final ValorInAkros card) {
        super(card);
    }

    @Override
    public ValorInAkros copy() {
        return new ValorInAkros(this);
    }
}
