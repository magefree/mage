
package mage.cards.c;

import java.util.UUID;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ColorlessPredicate;
import mage.target.TargetSpell;

/**
 *
 * @author fireshoes
 */
public final class CeremoniousRejection extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("colorless spell");

    static{
        filter.add(new ColorlessPredicate());
    }

    public CeremoniousRejection(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{U}");

        // Counter target colorless spell.
        getSpellAbility().addEffect(new CounterTargetEffect());
        getSpellAbility().addTarget(new TargetSpell(filter));
    }

    public CeremoniousRejection(final CeremoniousRejection card) {
        super(card);
    }

    @Override
    public CeremoniousRejection copy() {
        return new CeremoniousRejection(this);
    }
}
