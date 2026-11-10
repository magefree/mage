package mage.cards.e;

import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author muz
 */
public final class EmergencyRegeneration extends CardImpl {

    public EmergencyRegeneration(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Artifacts you control gain hexproof and indestructible until end of turn.
        this.getSpellAbility().addEffect(new GainAbilityControlledEffect(
            HexproofAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_ARTIFACTS
        ).setText("artifacts you control gain hexproof"));
        this.getSpellAbility().addEffect(new GainAbilityControlledEffect(
            IndestructibleAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_ARTIFACTS
        ).setText("and indestructible until end of turn"));
    }

    private EmergencyRegeneration(final EmergencyRegeneration card) {
        super(card);
    }

    @Override
    public EmergencyRegeneration copy() {
        return new EmergencyRegeneration(this);
    }
}
