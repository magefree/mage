package mage.cards.e;

import mage.abilities.effects.common.discard.LookTargetHandChooseDiscardEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Extortion extends CardImpl {

    public Extortion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}{B}");

        // Look at target player's hand and choose up to two cards from it. That player discards those cards.
        this.getSpellAbility().addEffect(new LookTargetHandChooseDiscardEffect(true, 2));
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private Extortion(final Extortion card) {
        super(card);
    }

    @Override
    public Extortion copy() {
        return new Extortion(this);
    }
}
