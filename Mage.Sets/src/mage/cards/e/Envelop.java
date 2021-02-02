
package mage.cards.e;

import java.util.UUID;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterSpell;
import mage.target.TargetSpell;

/**
 *
 * @author LevelX2
 */
public final class Envelop extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("sorcery spell");

    static {
        filter.add(CardType.SORCERY.getPredicate());
    }

    public Envelop(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{U}");


        // Counter target sorcery spell.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell(filter));
    }

    private Envelop(final Envelop card) {
        super(card);
    }

    @Override
    public Envelop copy() {
        return new Envelop(this);
    }
}
