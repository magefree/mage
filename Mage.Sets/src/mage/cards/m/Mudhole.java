package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author cbt33
 */
public final class Mudhole extends CardImpl {

    public Mudhole(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");

        // Target player exiles all land cards from their graveyard.
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new MudholeEffect());
    }

    private Mudhole(final Mudhole card) {
        super(card);
    }

    @Override
    public Mudhole copy() {
        return new Mudhole(this);
    }
}

class MudholeEffect extends OneShotEffect {

    MudholeEffect() {
        super(Outcome.Exile);
        staticText = "Target player exiles all land cards from their graveyard";
    }

    private MudholeEffect(final MudholeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        if (player == null) {
            return false;
        }
        return player.moveCards(player.getGraveyard().getCards(
                StaticFilters.FILTER_CARD_LAND, game
        ), Zone.EXILED, source, game);
    }

    @Override
    public MudholeEffect copy() {
        return new MudholeEffect(this);
    }
}
