
package mage.cards.c;

import java.util.UUID;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterControlledCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public final class CrossbowAmbush extends CardImpl {

    public CrossbowAmbush(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{G}");

        // Creatures you control gain reach until end of turn.
        this.getSpellAbility().addEffect(new GainAbilityControlledEffect(ReachAbility.getInstance(), Duration.EndOfTurn, new FilterControlledCreaturePermanent("Creatures")));
    }

    private CrossbowAmbush(final CrossbowAmbush card) {
        super(card);
    }

    @Override
    public CrossbowAmbush copy() {
        return new CrossbowAmbush(this);
    }
}
