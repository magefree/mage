
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ClashEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author Styxo
 */
public final class PullingTeeth extends CardImpl {

    public PullingTeeth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{B}");

        // Clash with an opponent. If you win, target player discards two cards. Otherwise that player discards a card.
        this.getSpellAbility().addEffect(new PullingTeethEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private PullingTeeth(final PullingTeeth card) {
        super(card);
    }

    @Override
    public PullingTeeth copy() {
        return new PullingTeeth(this);
    }
}

class PullingTeethEffect extends OneShotEffect {

    public PullingTeethEffect() {
        super(Outcome.Discard);
        this.staticText = "Clash with an opponent. If you win, target player discards two cards. Otherwise, that player discards a card";
    }

    public PullingTeethEffect(final PullingTeethEffect effect) {
        super(effect);
    }

    @Override
    public PullingTeethEffect copy() {
        return new PullingTeethEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int cardsToDiscard;
            if (new ClashEffect().apply(game, source)) {
                cardsToDiscard = 2;
            } else {
                cardsToDiscard = 1;
            }
            DiscardTargetEffect effect = new DiscardTargetEffect(cardsToDiscard);
            effect.apply(game, source);
            return true;
        }
        return false;
    }
}
