package mage.cards.c;

import java.util.UUID;

import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.target.TargetPermanent;

/**
 *
 * @author NinthWorld
 */
public final class Contaminate extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("artifact or enchantment");

    static {
        filter.add(new CardTypePredicate(CardType.ARTIFACT));
        filter.add(new CardTypePredicate(CardType.ENCHANTMENT));
    }

    public Contaminate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}");
        

        // Destroy target artifact or enchantment. Draw a card.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1));
    }

    public Contaminate(final Contaminate card) {
        super(card);
    }

    @Override
    public Contaminate copy() {
        return new Contaminate(this);
    }
}
