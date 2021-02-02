
package mage.cards.d;

import java.util.UUID;
import mage.abilities.effects.common.discard.DiscardEachPlayerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author LevelX2
 */
public final class DeliriumSkeins extends CardImpl {

    public DeliriumSkeins(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{B}");


        // Each player discards three cards.
        this.getSpellAbility().addEffect(new DiscardEachPlayerEffect(3, false));
    }

    private DeliriumSkeins(final DeliriumSkeins card) {
        super(card);
    }

    @Override
    public DeliriumSkeins copy() {
        return new DeliriumSkeins(this);
    }
}
