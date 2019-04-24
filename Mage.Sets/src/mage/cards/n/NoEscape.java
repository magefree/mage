package mage.cards.n;

import mage.abilities.effects.common.CounterTargetWithReplacementEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NoEscape extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("creature or planeswalker spell");

    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.CREATURE),
                new CardTypePredicate(CardType.PLANESWALKER)
        ));
    }

    public NoEscape(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}");

        // Counter target creature or planeswalker spell. If that spell is countered this way, exile it instead of putting it into its owner's graveyard.
        this.getSpellAbility().addEffect(new CounterTargetWithReplacementEffect(Zone.EXILED));
        this.getSpellAbility().addTarget(new TargetSpell(filter));

        // Scry 1.
        this.getSpellAbility().addEffect(new ScryEffect(1));
    }

    private NoEscape(final NoEscape card) {
        super(card);
    }

    @Override
    public NoEscape copy() {
        return new NoEscape(this);
    }
}
