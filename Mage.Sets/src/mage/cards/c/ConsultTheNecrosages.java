
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

/**
 *
 * @author anonymous
 */
public final class ConsultTheNecrosages extends CardImpl {

    public ConsultTheNecrosages(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{U}{B}");


        // Choose one - Target player draws two cards; or target player discards two cards.
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new DrawCardTargetEffect(2));
        Mode mode = new Mode(new DiscardTargetEffect(2));
        mode.addTarget(new TargetPlayer());
        this.getSpellAbility().addMode(mode);
    }

    private ConsultTheNecrosages(final ConsultTheNecrosages card) {
        super(card);
    }

    @Override
    public ConsultTheNecrosages copy() {
        return new ConsultTheNecrosages(this);
    }
}
