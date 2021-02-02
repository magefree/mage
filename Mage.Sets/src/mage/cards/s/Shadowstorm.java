
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.keyword.ShadowAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;

/**
 *
 * @author fireshoes
 */
public final class Shadowstorm extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with shadow");

    static {
        filter.add(new AbilityPredicate(ShadowAbility.class));
    }

    public Shadowstorm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{R}");

        // Shadowstorm deals 2 damage to each creature with shadow.
        this.getSpellAbility().addEffect(new DamageAllEffect(2, filter));
    }

    private Shadowstorm(final Shadowstorm card) {
        super(card);
    }

    @Override
    public Shadowstorm copy() {
        return new Shadowstorm(this);
    }
}
