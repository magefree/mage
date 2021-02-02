

package mage.cards.b;

import java.util.UUID;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.CascadeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class BituminousBlast extends CardImpl {

    public BituminousBlast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{B}{R}");
        

        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new DamageTargetEffect(4));
        this.addAbility(new CascadeAbility());
    }

    private BituminousBlast(final BituminousBlast card) {
        super(card);
    }

    @Override
    public BituminousBlast copy() {
        return new BituminousBlast(this);
    }

}
