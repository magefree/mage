package mage.cards.r;

import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class RakshasasSecret extends CardImpl {

    public RakshasasSecret(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // Target opponent discards two cards. Put the top two cards of your library into your graveyard.
        this.getSpellAbility().addEffect(new DiscardTargetEffect(2));
        this.getSpellAbility().addTarget(new TargetOpponent());
        this.getSpellAbility().addEffect(new MillCardsControllerEffect(2).setText("Mill two cards."));
    }

    private RakshasasSecret(final RakshasasSecret card) {
        super(card);
    }

    @Override
    public RakshasasSecret copy() {
        return new RakshasasSecret(this);
    }
}
