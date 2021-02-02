
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.abilities.effects.keyword.FatesealEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class SpinIntoMyth extends CardImpl {

    public SpinIntoMyth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{4}{U}");

        // Put target creature on top of its owner's library, then fateseal 2.
        this.getSpellAbility().addEffect(new PutOnLibraryTargetEffect(true));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        Effect effect = new FatesealEffect(2);
        effect.setText(", then fateseal 2. <i>(To fateseal 2, look at the top two cards of an opponent's library, then put any number of them on the bottom of that player's library and the rest on top in any order.)</i>");
        this.getSpellAbility().addEffect(effect);
    }

    private SpinIntoMyth(final SpinIntoMyth card) {
        super(card);
    }

    @Override
    public SpinIntoMyth copy() {
        return new SpinIntoMyth(this);
    }
}
