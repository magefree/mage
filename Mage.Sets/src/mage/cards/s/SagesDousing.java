
package mage.cards.s;

import java.util.UUID;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.target.TargetSpell;

/**
 *
 * @author fireshoes
 */
public final class SagesDousing extends CardImpl {
    
    private static final FilterPermanent filter = new FilterPermanent("Wizard");

    static {
        filter.add(SubType.WIZARD.getPredicate());
    }

    public SagesDousing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.TRIBAL,CardType.INSTANT},"{2}{U}");
        this.subtype.add(SubType.WIZARD);

        // Counter target spell unless its controller pays {3}. 
        this.getSpellAbility().addTarget(new TargetSpell());
        this.getSpellAbility().addEffect(new CounterUnlessPaysEffect(new GenericManaCost(3)));
        
        // If you control a Wizard, draw a card.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(new DrawCardSourceControllerEffect(1), 
                new PermanentsOnTheBattlefieldCondition(filter),"If you control a Wizard, draw a card"));
    }

    private SagesDousing(final SagesDousing card) {
        super(card);
    }

    @Override
    public SagesDousing copy() {
        return new SagesDousing(this);
    }
}
