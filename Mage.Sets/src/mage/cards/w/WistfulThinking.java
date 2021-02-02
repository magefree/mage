
package mage.cards.w;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

/**
 *
 * @author daagar
 */
public final class WistfulThinking extends CardImpl {

    public WistfulThinking(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{U}");

        // Target player draws two cards, then discards four cards.
        this.getSpellAbility().addEffect(new DrawCardTargetEffect(2));
        Effect effect = new DiscardTargetEffect(4);
        effect.setText(", then discards four cards");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private WistfulThinking(final WistfulThinking card) {
        super(card);
    }

    @Override
    public WistfulThinking copy() {
        return new WistfulThinking(this);
    }
}
