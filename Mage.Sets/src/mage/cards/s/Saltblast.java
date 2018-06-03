
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
 * @author LevelX2
 */
public final class Saltblast extends CardImpl {

    static final protected FilterPermanent filter = new FilterPermanent("nonwhite permanent");
    static {
        filter.add(Predicates.not(new ColorPredicate(ObjectColor.WHITE)));
    }
    public Saltblast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{W}{W}");


        // Destroy target nonwhite permanent.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    public Saltblast(final Saltblast card) {
        super(card);
    }

    @Override
    public Saltblast copy() {
        return new Saltblast(this);
    }
}

