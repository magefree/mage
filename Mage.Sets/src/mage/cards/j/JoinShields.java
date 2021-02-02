package mage.cards.j;

import java.util.UUID;
import mage.abilities.effects.common.UntapAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

/**
 *
 * @author TheElk801
 */
public final class JoinShields extends CardImpl {

    public JoinShields(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{G}{W}");

        // Untap all creatures you control. They gain hexproof and indestructible until end of turn.
        this.getSpellAbility().addEffect(new UntapAllEffect(
                StaticFilters.FILTER_CONTROLLED_CREATURES
        ));
        this.getSpellAbility().addEffect(new GainAbilityControlledEffect(
                HexproofAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_CONTROLLED_CREATURES
        ).setText("They gain hexproof"));
        this.getSpellAbility().addEffect(new GainAbilityControlledEffect(
                IndestructibleAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_CONTROLLED_CREATURES
        ).setText("and indestructible until end of turn"));
    }

    private JoinShields(final JoinShields card) {
        super(card);
    }

    @Override
    public JoinShields copy() {
        return new JoinShields(this);
    }
}
