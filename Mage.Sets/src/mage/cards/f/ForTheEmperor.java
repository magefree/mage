package mage.cards.f;

import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.LifelinkAbility;
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
public final class ForTheEmperor extends CardImpl {

    public ForTheEmperor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{W}");

        // Creatures you control get +2/+2 and gain vigilance and lifelink until end of turn.
        this.getSpellAbility().addEffect(new BoostControlledEffect(
                2, 2, Duration.EndOfTurn
        ).setText("creatures you control get +2/+2"));
        this.getSpellAbility().addEffect(new GainAbilityControlledEffect(
                VigilanceAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_PERMANENT_CREATURE
        ).setText("and gain vigilance"));
        this.getSpellAbility().addEffect(new GainAbilityControlledEffect(
                LifelinkAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_PERMANENT_CREATURE
        ).setText("and lifelink until end of turn"));
    }

    private ForTheEmperor(final ForTheEmperor card) {
        super(card);
    }

    @Override
    public ForTheEmperor copy() {
        return new ForTheEmperor(this);
    }
}
