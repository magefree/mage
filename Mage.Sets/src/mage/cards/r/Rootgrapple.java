
package mage.cards.r;

import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 *
 * @author Wehk
 */
public final class Rootgrapple extends CardImpl {
    
    private static final FilterPermanent filterNoncreature = new FilterPermanent("noncreature permanent");
    private static final FilterPermanent filterTreefolk = new FilterPermanent("If you control a Treefolk,");

    static {
        filterNoncreature.add(Predicates.not(CardType.CREATURE.getPredicate()));
        filterTreefolk.add(SubType.TREEFOLK.getPredicate());
    }

    public Rootgrapple(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.TRIBAL,CardType.INSTANT},"{4}{G}");
        this.subtype.add(SubType.TREEFOLK);

        // Destroy target noncreature permanent.
        this.getSpellAbility().addTarget(new TargetPermanent(filterNoncreature));
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        
        // If you control a Treefolk, draw a card.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(new DrawCardSourceControllerEffect(1),
                new PermanentsOnTheBattlefieldCondition(filterTreefolk),
                "If you control a Treefolk, draw a card"));
    }

    private Rootgrapple(final Rootgrapple card) {
        super(card);
    }

    @Override
    public Rootgrapple copy() {
        return new Rootgrapple(this);
    }
}
