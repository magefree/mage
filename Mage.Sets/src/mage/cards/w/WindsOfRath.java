
package mage.cards.w;

import java.util.UUID;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.EnchantedPredicate;

/**
 *
 * @author LevelX2
 */
public final class WindsOfRath extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures that aren't enchanted");
    static {
        filter.add(Predicates.not(EnchantedPredicate.instance));
    }

    public WindsOfRath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{W}{W}");


        // Destroy all creatures that aren't enchanted. They can't be regenerated.
        this.getSpellAbility().addEffect(new DestroyAllEffect(filter, true));
    }

    private WindsOfRath(final WindsOfRath card) {
        super(card);
    }

    @Override
    public WindsOfRath copy() {
        return new WindsOfRath(this);
    }
}
