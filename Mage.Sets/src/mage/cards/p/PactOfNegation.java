
package mage.cards.p;

import java.util.UUID;
import mage.abilities.common.delayed.PactDelayedTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetSpell;

/**
 *
 * @author Plopman
 */
public final class PactOfNegation extends CardImpl {

    public PactOfNegation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{0}");

        this.color.setBlue(true);
        
        // Counter target spell.
        this.getSpellAbility().addTarget(new TargetSpell());
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        // At the beginning of your next upkeep, pay {3}{U}{U}. If you don't, you lose the game.
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(new PactDelayedTriggeredAbility(new ManaCostsImpl<>("{3}{U}{U}")), false));
    }

    private PactOfNegation(final PactOfNegation card) {
        super(card);
    }

    @Override
    public PactOfNegation copy() {
        return new PactOfNegation(this);
    }
}
