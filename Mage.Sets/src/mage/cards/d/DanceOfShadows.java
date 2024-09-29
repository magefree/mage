

package mage.cards.d;

import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FearAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 *
 * @author Loki
 */
public final class DanceOfShadows extends CardImpl {

    public DanceOfShadows (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{B}{B}");
        this.subtype.add(SubType.ARCANE);

        
        // Creatures you control get +1/+0 and gain fear until end of turn. (They can't be blocked except by artifact creatures and/or black creatures.)
        Effect effect = new BoostControlledEffect(1, 0, Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURES);
        effect.setText("Creatures you control get +1/+0");
        this.getSpellAbility().addEffect(effect);
        effect = new GainAbilityControlledEffect(FearAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURES);
        effect.setText("and gain fear until end of turn");
        this.getSpellAbility().addEffect(effect);
    }

    private DanceOfShadows(final DanceOfShadows card) {
        super(card);
    }

    @Override
    public DanceOfShadows copy() {
        return new DanceOfShadows(this);
    }

}
