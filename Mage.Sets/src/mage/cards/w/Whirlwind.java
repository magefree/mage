
package mage.cards.w;

import java.util.UUID;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;

/**
 *
 * @author LevelX2
 */
public final class Whirlwind extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures with flying");

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }

    public Whirlwind(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{G}{G}");


        // Destroy all creatures with flying.
        this.getSpellAbility().addEffect(new DestroyAllEffect(filter));
    }

    private Whirlwind(final Whirlwind card) {
        super(card);
    }

    @Override
    public Whirlwind copy() {
        return new Whirlwind(this);
    }
}
