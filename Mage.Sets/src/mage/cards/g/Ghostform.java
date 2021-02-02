
package mage.cards.g;

import java.util.UUID;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author noxx
 */
public final class Ghostform extends CardImpl {

    public Ghostform(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{U}");


        // Up to two target creatures can't be blocked this turn..
        this.getSpellAbility().addEffect(new CantBeBlockedTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 2));
    }

    private Ghostform(final Ghostform card) {
        super(card);
    }

    @Override
    public Ghostform copy() {
        return new Ghostform(this);
    }
}
