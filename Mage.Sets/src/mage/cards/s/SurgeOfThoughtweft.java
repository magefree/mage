
package mage.cards.s;

import java.util.UUID;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;

/**
 *
 * @author fireshoes
 */
public final class SurgeOfThoughtweft extends CardImpl {
    
    private static final FilterPermanent filter = new FilterPermanent("Kithkin");

    static {
        filter.add(SubType.KITHKIN.getPredicate());
    }

    public SurgeOfThoughtweft(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.TRIBAL,CardType.INSTANT},"{1}{W}");
        this.subtype.add(SubType.KITHKIN);

        // Creatures you control get +1/+1 until end of turn.
        this.getSpellAbility().addEffect(new BoostControlledEffect(1, 1, Duration.EndOfTurn));
        
        // If you control a Kithkin, draw a card.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(new DrawCardSourceControllerEffect(1), 
                new PermanentsOnTheBattlefieldCondition(filter),"If you control a Kithkin, draw a card"));
    }

    private SurgeOfThoughtweft(final SurgeOfThoughtweft card) {
        super(card);
    }

    @Override
    public SurgeOfThoughtweft copy() {
        return new SurgeOfThoughtweft(this);
    }
}
