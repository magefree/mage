
package mage.cards.s;

import java.util.UUID;
import mage.MageObjectReference;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.stack.StackObject;
import mage.target.TargetSpell;
import mage.watchers.common.CastSpellLastTurnWatcher;

/**
 *
 * @author noxx
 */
public final class SecondGuess extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("spell that's the second spell cast this turn");

    static {
        filter.add(new SecondSpellPredicate());
    }

    public SecondGuess(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}");


        // Counter target spell that's the second spell cast this turn.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell(filter));
    }

    private SecondGuess(final SecondGuess card) {
        super(card);
    }

    @Override
    public SecondGuess copy() {
        return new SecondGuess(this);
    }
}

class SecondSpellPredicate implements Predicate<StackObject> {

    @Override
    public boolean apply(StackObject input, Game game) {
        CastSpellLastTurnWatcher watcher = game.getState().getWatcher(CastSpellLastTurnWatcher.class);

        if (watcher != null && watcher.getSpellOrder(new MageObjectReference(input.getId(), game), game) == 2) {
            return true;
        }

        return false;
    }

    @Override
    public String toString() {
        return "SecondSpellThisTurn";
    }
}
