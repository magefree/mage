package mage.cards.i;

import mage.abilities.effects.common.DamageAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IncandescentAria extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("nontoken creature");

    static {
        filter.add(TokenPredicate.FALSE);
    }

    public IncandescentAria(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{R}{G}{W}");

        // Incandescent Aria deals 3 damage to each nontoken creature.
        this.getSpellAbility().addEffect(new DamageAllEffect(3, filter));
    }

    private IncandescentAria(final IncandescentAria card) {
        super(card);
    }

    @Override
    public IncandescentAria copy() {
        return new IncandescentAria(this);
    }
}
