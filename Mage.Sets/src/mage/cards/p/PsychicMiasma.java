
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class PsychicMiasma extends CardImpl {

    public PsychicMiasma(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{B}");

        // Target player discards a card. If a land card is discarded this way, return Psychic Miasma to its owner's hand.
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new PsychicMiasmaEffect());
    }

    private PsychicMiasma(final PsychicMiasma card) {
        super(card);
    }

    @Override
    public PsychicMiasma copy() {
        return new PsychicMiasma(this);
    }

}

class PsychicMiasmaEffect extends OneShotEffect {

    PsychicMiasmaEffect() {
        super(Outcome.Discard);
        staticText = "Target player discards a card. If a land card is discarded this way, return {this} to its owner's hand";
    }

    private PsychicMiasmaEffect(final PsychicMiasmaEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player != null) {
            Card discardedCard = player.discardOne(false, false, source, game);
            if (discardedCard != null && discardedCard.isLand(game)) {
                Card spellCard = game.getStack().getSpell(source.getSourceId()).getCard();
                if (spellCard != null) {
                    player.moveCards(spellCard, Zone.HAND, source, game);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public PsychicMiasmaEffect copy() {
        return new PsychicMiasmaEffect(this);
    }
}
