
package mage.cards.f;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TimingRule;
import mage.target.TargetSpell;

/**
 *
 * @author cbt33
 */
public final class FerventDenial extends CardImpl {

    public FerventDenial(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{U}{U}");


        // Counter target spell.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
        // Flashback {5}{U}{U}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl("{5}{U}{U}")));
    }

    private FerventDenial(final FerventDenial card) {
        super(card);
    }

    @Override
    public FerventDenial copy() {
        return new FerventDenial(this);
    }
}
