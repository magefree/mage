
package mage.cards.h;

import java.util.UUID;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

/**
 *
 * @author North
 */
public final class HorrifyingRevelation extends CardImpl {

    public HorrifyingRevelation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{B}");


        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new DiscardTargetEffect(1));
        this.getSpellAbility().addEffect(new MillCardsTargetEffect(1));
    }

    private HorrifyingRevelation(final HorrifyingRevelation card) {
        super(card);
    }

    @Override
    public HorrifyingRevelation copy() {
        return new HorrifyingRevelation(this);
    }
}
