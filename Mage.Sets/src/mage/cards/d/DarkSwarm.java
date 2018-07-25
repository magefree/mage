package mage.cards.d;

import java.util.UUID;

import mage.abilities.effects.common.PreventAllDamageByAllPermanentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 *
 * @author NinthWorld
 */
public final class DarkSwarm extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("non-Zerg creatures");

    static {
        filter.add(Predicates.not(new SubtypePredicate(SubType.ZERG)));
    }

    public DarkSwarm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");
        

        // Prevent all combat damage that would be dealt by non-Zerg creatures this turn.
        this.getSpellAbility().addEffect(new PreventAllDamageByAllPermanentsEffect(filter, Duration.EndOfTurn, true));
    }

    public DarkSwarm(final DarkSwarm card) {
        super(card);
    }

    @Override
    public DarkSwarm copy() {
        return new DarkSwarm(this);
    }
}
