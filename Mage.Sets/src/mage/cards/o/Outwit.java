
package mage.cards.o;

import mage.abilities.effects.common.CounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPlayer;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.TargetsPlayerPredicate;
import mage.target.TargetSpell;

import java.util.UUID;


/**
 * @author Susucr
 */
public final class Outwit extends CardImpl {

    private static FilterSpell filter = new FilterSpell("spell that targets a player");

    static {
        filter.add(new TargetsPlayerPredicate(new FilterPlayer()));
    }

    public Outwit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}");

        // Counter target spell that targets a player.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell(filter));
    }

    private Outwit(final Outwit card) {
        super(card);
    }

    @Override
    public Outwit copy() {
        return new Outwit(this);
    }
}
