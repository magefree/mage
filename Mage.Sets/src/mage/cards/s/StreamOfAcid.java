
package mage.cards.s;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.TargetPermanent;

/**
 *
 * @author fireshoes
 */
public final class StreamOfAcid extends CardImpl {
    
    static final FilterPermanent filter = new FilterPermanent("land or nonblack creature");

    static {
        filter.add(Predicates.or(
                CardType.LAND.getPredicate(),
                Predicates.and(
                    Predicates.not(new ColorPredicate(ObjectColor.BLACK)),
                    CardType.CREATURE.getPredicate())));
    }

    public StreamOfAcid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{B}{B}");

        // Destroy target land or nonblack creature.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    private StreamOfAcid(final StreamOfAcid card) {
        super(card);
    }

    @Override
    public StreamOfAcid copy() {
        return new StreamOfAcid(this);
    }
}
