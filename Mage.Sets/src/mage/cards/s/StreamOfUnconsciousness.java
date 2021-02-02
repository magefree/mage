
package mage.cards.s;

import java.util.UUID;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class StreamOfUnconsciousness extends CardImpl {
    
    private static final FilterPermanent filter = new FilterPermanent("Wizard");

    static {
        filter.add(SubType.WIZARD.getPredicate());
    }

    public StreamOfUnconsciousness(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.TRIBAL,CardType.INSTANT},"{U}");
        this.subtype.add(SubType.WIZARD);

        // Target creature gets -4/-0 until end of turn. 
        this.getSpellAbility().addEffect(new BoostTargetEffect(-4, 0, Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        
        // If you control a Wizard, draw a card.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(new DrawCardSourceControllerEffect(1), 
                new PermanentsOnTheBattlefieldCondition(filter),"If you control a Wizard, draw a card"));
    }

    private StreamOfUnconsciousness(final StreamOfUnconsciousness card) {
        super(card);
    }

    @Override
    public StreamOfUnconsciousness copy() {
        return new StreamOfUnconsciousness(this);
    }
}
