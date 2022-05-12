
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAttackingCreature;
import mage.target.common.TargetEnchantmentPermanent;

/**
 *
 * @author FenrisulfrX
 */
public final class TrevasCharm extends CardImpl {

    public TrevasCharm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{G}{W}{U}");

        // Choose one - Destroy target enchantment;
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetEnchantmentPermanent());

        // or exile target attacking creature;
        Mode mode = new Mode(new ExileTargetEffect());
        mode.addTarget(new TargetAttackingCreature());
        this.getSpellAbility().addMode(mode);

        // or draw a card, then discard a card.
        mode = new Mode(new DrawDiscardControllerEffect());
        this.getSpellAbility().addMode(mode);
    }

    private TrevasCharm(final TrevasCharm card) {
        super(card);
    }

    @Override
    public TrevasCharm copy() {
        return new TrevasCharm(this);
    }
}
