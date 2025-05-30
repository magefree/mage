package mage.cards.p;

import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PreposterousProportions extends CardImpl {

    public PreposterousProportions(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{G}{G}");

        // Creatures you control get +10/+10 and gain vigilance until end of turn.
        this.getSpellAbility().addEffect(new BoostControlledEffect(
                10, 10, Duration.EndOfTurn
        ).setText("creatures you control get +10/+10"));
        this.getSpellAbility().addEffect(new GainAbilityControlledEffect(
                VigilanceAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_PERMANENT_CREATURE
        ).setText("and gain vigilance until end of turn"));
    }

    private PreposterousProportions(final PreposterousProportions card) {
        super(card);
    }

    @Override
    public PreposterousProportions copy() {
        return new PreposterousProportions(this);
    }
}
