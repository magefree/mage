
package mage.cards.s;

import mage.abilities.condition.common.ManaWasSpentCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.filter.StaticFilters;
import mage.game.permanent.token.SaprolingToken;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 *
 * @author Wehk
 */
public final class SeedSpark extends CardImpl {

    public SeedSpark(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{W}");

        // Destroy target artifact or enchantment. 
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT));
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        
        //If {G} was spent to cast Seed Spark, create two 1/1 green Saproling creature tokens.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new CreateTokenEffect(new SaprolingToken(), 2), 
                ManaWasSpentCondition.GREEN, "If {G} was spent to cast this spell, create two 1/1 green Saproling creature tokens"));
    }

    private SeedSpark(final SeedSpark card) {
        super(card);
    }

    @Override
    public SeedSpark copy() {
        return new SeedSpark(this);
    }
}
