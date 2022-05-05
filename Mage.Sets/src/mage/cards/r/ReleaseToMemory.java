package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.SpiritToken;
import mage.players.Player;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ReleaseToMemory extends CardImpl {

    public ReleaseToMemory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{W}");

        // Exile target opponent's graveyard. For each creature card exiled this way, create a 1/1 colorless Spirit creature token.
        this.getSpellAbility().addEffect(new ReleaseToMemoryEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private ReleaseToMemory(final ReleaseToMemory card) {
        super(card);
    }

    @Override
    public ReleaseToMemory copy() {
        return new ReleaseToMemory(this);
    }
}

class ReleaseToMemoryEffect extends OneShotEffect {

    ReleaseToMemoryEffect() {
        super(Outcome.Benefit);
        staticText = "exile target opponent's graveyard. For each creature " +
                "card exiled this way, create a 1/1 colorless Spirit creature token";
    }

    private ReleaseToMemoryEffect(final ReleaseToMemoryEffect effect) {
        super(effect);
    }

    @Override
    public ReleaseToMemoryEffect copy() {
        return new ReleaseToMemoryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        if (player == null) {
            return false;
        }
        int creatures = player.getGraveyard().count(StaticFilters.FILTER_CARD_CREATURE, game);
        player.moveCards(player.getGraveyard(), Zone.EXILED, source, game);
        if (creatures > 0) {
            new SpiritToken().putOntoBattlefield(creatures, game, source);
        }
        return true;
    }
}
