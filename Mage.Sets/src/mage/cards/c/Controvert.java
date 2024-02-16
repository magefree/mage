
package mage.cards.c;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.keyword.RecoverAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetSpell;

/**
 *
 * @author fireshoes
 */
public final class Controvert extends CardImpl {

    public Controvert(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{U}{U}");

        // Counter target spell.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
        
        // Recover {2}{U}{U}
        this.addAbility(new RecoverAbility(new ManaCostsImpl<>("{2}{U}{U}"), this));
    }

    private Controvert(final Controvert card) {
        super(card);
    }

    @Override
    public Controvert copy() {
        return new Controvert(this);
    }
}
