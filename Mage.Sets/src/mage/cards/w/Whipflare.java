
package mage.cards.w;

import java.util.UUID;
import mage.abilities.effects.common.DamageAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;

/**
 *
 * @author North
 */
public final class Whipflare extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nonartifact creature");

    static {
        filter.add(Predicates.not(CardType.ARTIFACT.getPredicate()));
    }

    public Whipflare(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{R}");


        // Whipflare deals 2 damage to each nonartifact creature.
        this.getSpellAbility().addEffect(new DamageAllEffect(2, filter));
    }

    private Whipflare(final Whipflare card) {
        super(card);
    }

    @Override
    public Whipflare copy() {
        return new Whipflare(this);
    }
}
