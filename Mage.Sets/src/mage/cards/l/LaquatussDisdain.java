
package mage.cards.l;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.target.TargetSpell;

/**
 *
 * @author TheElk801
 */
public final class LaquatussDisdain extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("spell cast from a graveyard");

    static {
        filter.add(new LaquatussDisdainPredicate());
    }

    public LaquatussDisdain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Counter target spell cast from a graveyard.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell(filter));

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private LaquatussDisdain(final LaquatussDisdain card) {
        super(card);
    }

    @Override
    public LaquatussDisdain copy() {
        return new LaquatussDisdain(this);
    }
}

class LaquatussDisdainPredicate implements Predicate<MageObject> {

    public LaquatussDisdainPredicate() {
    }

    @Override
    public boolean apply(MageObject input, Game game) {
        if (input instanceof Spell) {
            if (((Spell) input).getFromZone() == Zone.GRAVEYARD) {
                return true;
            }
        }
        return false;
    }
}
