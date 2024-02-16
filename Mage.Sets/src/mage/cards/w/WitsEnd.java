package mage.cards.w;

import mage.abilities.effects.common.discard.DiscardHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author North
 */
public final class WitsEnd extends CardImpl {

    public WitsEnd(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{B}{B}");

        // Target player discards their hand.
        this.getSpellAbility().addEffect(new DiscardHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private WitsEnd(final WitsEnd card) {
        super(card);
    }

    @Override
    public WitsEnd copy() {
        return new WitsEnd(this);
    }
}
