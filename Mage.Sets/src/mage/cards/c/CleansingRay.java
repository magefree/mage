
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetEnchantmentPermanent;

/**
 *
 * @author LevelX2
 */
public final class CleansingRay extends CardImpl {

    public CleansingRay(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{W}");

        // Choose one -
        // - Destroy target Vampire.
        getSpellAbility().addEffect(new DestroyTargetEffect());
        FilterPermanent filter = new FilterPermanent("Vampire");
        filter.add(new SubtypePredicate(SubType.VAMPIRE));
        getSpellAbility().addTarget(new TargetPermanent(filter));

        // - Destroy target enchantment.
        Mode mode = new Mode();
        mode.addEffect(new DestroyTargetEffect());
        mode.addTarget(new TargetEnchantmentPermanent());
        this.getSpellAbility().addMode(mode);

    }

    public CleansingRay(final CleansingRay card) {
        super(card);
    }

    @Override
    public CleansingRay copy() {
        return new CleansingRay(this);
    }
}
