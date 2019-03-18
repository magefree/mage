
package mage.cards.w;

import java.util.UUID;
import mage.abilities.common.CantBeCounteredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.target.TargetPermanent;

/**
 *
 * @author Loki
 */
public final class WreakHavoc extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("artifact or land");

    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.ARTIFACT),
                new CardTypePredicate(CardType.LAND)));
    }

    public WreakHavoc(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{R}{G}");


        this.addAbility(new CantBeCounteredAbility());
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    public WreakHavoc(final WreakHavoc card) {
        super(card);
    }

    @Override
    public WreakHavoc copy() {
        return new WreakHavoc(this);
    }
}
