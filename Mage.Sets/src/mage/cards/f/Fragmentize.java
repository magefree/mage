
package mage.cards.f;

import java.util.UUID;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.common.FilterArtifactOrEnchantmentPermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.TargetPermanent;

/**
 *
 * @author fireshoes
 */
public final class Fragmentize extends CardImpl {

    private static final FilterArtifactOrEnchantmentPermanent filter = new FilterArtifactOrEnchantmentPermanent("artifact or enchantment with mana value 4 or less");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 5));
    }

    public Fragmentize(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{W}");

        // Destroy target artifact or enchantment with converted mana cost 4 or less.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    private Fragmentize(final Fragmentize card) {
        super(card);
    }

    @Override
    public Fragmentize copy() {
        return new Fragmentize(this);
    }
}
