package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author fireshoes
 */

public final class DisplacementWave extends CardImpl {

    public DisplacementWave(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{U}{U}");

        // Return all nonland permanents with converted mana cost X or less to their owners' hands.
        this.getSpellAbility().addEffect(new DisplacementWaveEffect());
    }

    private DisplacementWave(final DisplacementWave card) {
        super(card);
    }

    @Override
    public DisplacementWave copy() {
        return new DisplacementWave(this);
    }
}

class DisplacementWaveEffect extends OneShotEffect {

    DisplacementWaveEffect() {
        super(Outcome.ReturnToHand);
        staticText = "Return all nonland permanents with mana value X or less to their owners' hands";
    }

    private DisplacementWaveEffect(final DisplacementWaveEffect effect) {
        super(effect);
    }

    @Override
    public DisplacementWaveEffect copy() {
        return new DisplacementWaveEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        for (Permanent permanent : game.getBattlefield().getActivePermanents(source.getControllerId(), game)) {
            if (!permanent.isLand(game) && permanent.getManaValue() <= source.getManaCostsToPay().getX()) {
                cards.add(permanent);
            }
        }
        return player.moveCards(cards, Zone.HAND, source, game);
    }
}
