

package mage.cards.l;

import java.util.UUID;
import mage.abilities.common.CantBeCounteredSourceAbility;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetSpell;

/**
 *
 * @author Loki
 */
public final class LastWord extends CardImpl {

    public LastWord (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{U}{U}");

        this.addAbility(new CantBeCounteredSourceAbility());
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    private LastWord(final LastWord card) {
        super(card);
    }

    @Override
    public LastWord copy() {
        return new LastWord(this);
    }

}
