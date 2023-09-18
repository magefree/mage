
package mage.cards.s;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.CardsInControllerHandCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.keyword.BolsterEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

/**
 *
 * @author fireshoes
 */
public final class SunbringersTouch extends CardImpl {

    public SunbringersTouch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{G}{G}");

        // Bolster X, where X is the number of cards in your hand. 
        this.getSpellAbility().addEffect(new BolsterEffect(CardsInControllerHandCount.instance).setText("Bolster X, where X is the number of cards in your hand."));
        
        // Each creature you control with a +1/+1 counter on it gains trample until end of turn.
        Effect effect = new GainAbilityControlledEffect(
                TrampleAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_EACH_CONTROLLED_CREATURE_P1P1);
        effect.setText("Each creature you control with a +1/+1 counter on it gains trample until end of turn. " +
                "<i>(To bolster X, choose a creature with the least toughness among creatures you control and put X +1/+1 counters on it.)</i>");
        this.getSpellAbility().addEffect(effect);
    }

    private SunbringersTouch(final SunbringersTouch card) {
        super(card);
    }

    @Override
    public SunbringersTouch copy() {
        return new SunbringersTouch(this);
    }
}
