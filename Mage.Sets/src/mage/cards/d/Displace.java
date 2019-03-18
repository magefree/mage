
package mage.cards.d;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ExileTargetForSourceEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderYourControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class Displace extends CardImpl {

    public Displace(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{U}");

        // Exile up to two target creatures you control, then return those cards to the battlefield under their owner's control.
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent(0, 2, new FilterControlledCreaturePermanent("creatures you control"), false));
        Effect effect = new ExileTargetForSourceEffect();
        effect.setText("Exile up to two target creatures you control");
        this.getSpellAbility().addEffect(effect);
        effect = new ReturnToBattlefieldUnderYourControlTargetEffect(true);
        effect.setText(", then return those cards to the battlefield under their owner's control");
        this.getSpellAbility().addEffect(effect);
    }

    public Displace(final Displace card) {
        super(card);
    }

    @Override
    public Displace copy() {
        return new Displace(this);
    }
}
