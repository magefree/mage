
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.PreventDamageBySourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.SaprolingToken;
import mage.target.common.TargetNonBasicLandPermanent;

/**
 *
 * @author FenrisulfrX
 */
public final class RithsCharm extends CardImpl {

    public RithsCharm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{R}{G}{W}");

        // Choose one - Destroy target nonbasic land;
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetNonBasicLandPermanent());

        // or create three 1/1 green Saproling creature tokens;
        Mode mode = new Mode(new CreateTokenEffect(new SaprolingToken(), 3));
        this.getSpellAbility().addMode(mode);

        // or prevent all damage a source of your choice would deal this turn.
        mode = new Mode(new PreventDamageBySourceEffect());
        this.getSpellAbility().addMode(mode);
    }

    private RithsCharm(final RithsCharm card) {
        super(card);
    }

    @Override
    public RithsCharm copy() {
        return new RithsCharm(this);
    }
}
