
package mage.cards.b;

import java.util.UUID;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.HorsemanshipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public final class BrokenDam extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(Predicates.not(new AbilityPredicate(HorsemanshipAbility.class)));
    }

   public BrokenDam(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{U}");

        // Tap one or two target creatures without horsemanship.
        this.getSpellAbility().addEffect(new TapTargetEffect("tap one or two target creatures without horsemanship"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(1, 2, filter, false));
    }

    private BrokenDam(final BrokenDam card) {
        super(card);
    }

    @Override
    public BrokenDam copy() {
        return new BrokenDam(this);
    }
}
