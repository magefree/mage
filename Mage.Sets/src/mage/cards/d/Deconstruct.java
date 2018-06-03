
package mage.cards.d;

import java.util.UUID;
import mage.Mana;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.target.TargetPermanent;

/**
 *
 * @author Loki
 */
public final class Deconstruct extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("artifact");

    static {
        filter.add(new CardTypePredicate(CardType.ARTIFACT));
    }

    public Deconstruct(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{G}");

        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
        this.getSpellAbility().addEffect(new BasicManaEffect(Mana.GreenMana(3)));
    }

    public Deconstruct(final Deconstruct card) {
        super(card);
    }

    @Override
    public Deconstruct copy() {
        return new Deconstruct(this);
    }
}
