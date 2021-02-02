

package mage.cards.r;

import java.util.UUID;
import mage.abilities.effects.common.CopyTargetSpellEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.target.TargetSpell;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class Reverberate extends CardImpl {

    private static final FilterSpell filter = new FilterSpell();

    static {
        filter.add(Predicates.or(
                CardType.INSTANT.getPredicate(),
                CardType.SORCERY.getPredicate()));
    }

    public Reverberate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{R}{R}");

        
        // Copy target instant or sorcery spell. You may choose new targets for the copy.
        this.getSpellAbility().addTarget(new TargetSpell(filter));
        this.getSpellAbility().addEffect(new CopyTargetSpellEffect());
    }

    private Reverberate(final Reverberate card) {
        super(card);
    }

    @Override
    public Reverberate copy() {
        return new Reverberate(this);
    }
}
