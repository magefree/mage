
package mage.cards.m;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

/**
 *
 * @author fireshoes
 */
public final class MakeAStand extends CardImpl {

    public MakeAStand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{W}");

        // Creatures you control get +1/+0 and gain indestructible until end of turn.
        Effect effect1 = new BoostControlledEffect(1, 0, Duration.EndOfTurn);
        effect1.setText("Creatures you control get +1/+0");
        this.getSpellAbility().addEffect(effect1);
        Effect effect2 = new GainAbilityControlledEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURES);
        effect2.setText("and gain indestructible until end of turn");
        this.getSpellAbility().addEffect(effect2);
    }

    private MakeAStand(final MakeAStand card) {
        super(card);
    }

    @Override
    public MakeAStand copy() {
        return new MakeAStand(this);
    }
}
