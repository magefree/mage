
package mage.cards.e;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;
import mage.target.common.TargetEnchantmentPermanent;

/**
 *
 * @author North
 */
public final class EsperCharm extends CardImpl {

    public EsperCharm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{W}{U}{B}");


        // Choose one - Destroy target enchantment;
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetEnchantmentPermanent());
        // or draw two cards;
        Mode mode = new Mode(new DrawCardSourceControllerEffect(2));
        this.getSpellAbility().addMode(mode);
        // or target player discards two cards.
        mode = new Mode(new DiscardTargetEffect(2));
        mode.addTarget(new TargetPlayer());
        this.getSpellAbility().addMode(mode);
    }

    private EsperCharm(final EsperCharm card) {
        super(card);
    }

    @Override
    public EsperCharm copy() {
        return new EsperCharm(this);
    }
}
