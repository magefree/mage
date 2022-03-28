
package mage.cards.i;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.abilities.effects.common.discard.DiscardHandAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author emerald000
 */
public final class IllGottenGains extends CardImpl {

    public IllGottenGains(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{B}{B}");

        // Exile Ill-Gotten Gains.
        this.getSpellAbility().addEffect(new ExileSpellEffect());

        // Each player discards their hand,
        this.getSpellAbility().addEffect(new DiscardHandAllEffect());

        //then returns up to three cards from their graveyard to their hand.
        this.getSpellAbility().addEffect(new IllGottenGainsEffect());
    }

    private IllGottenGains(final IllGottenGains card) {
        super(card);
    }

    @Override
    public IllGottenGains copy() {
        return new IllGottenGains(this);
    }
}

class IllGottenGainsEffect extends OneShotEffect {

    IllGottenGainsEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = ", then returns up to three cards from their graveyard to their hand.";
    }

    IllGottenGainsEffect(final IllGottenGainsEffect effect) {
        super(effect);
    }

    @Override
    public IllGottenGainsEffect copy() {
        return new IllGottenGainsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    Target target = new TargetCardInYourGraveyard(0, 3, new FilterCard());
                    if (target.choose(Outcome.ReturnToHand, player.getId(), source.getSourceId(), source, game)) {
                        player.moveCards(new CardsImpl(target.getTargets()), Zone.HAND, source, game);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
