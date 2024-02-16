package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MaddeningCacophony extends CardImpl {

    public MaddeningCacophony(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{U}");

        // Kicker {3}{U}
        this.addAbility(new KickerAbility("{3}{U}"));

        // Each opponent mills eight cards. If this spell was kicked, instead each opponent mills half their library, rounded up.
        this.getSpellAbility().addEffect(new MaddeningCacophonyEffect());
    }

    private MaddeningCacophony(final MaddeningCacophony card) {
        super(card);
    }

    @Override
    public MaddeningCacophony copy() {
        return new MaddeningCacophony(this);
    }
}

class MaddeningCacophonyEffect extends OneShotEffect {

    MaddeningCacophonyEffect() {
        super(Outcome.Benefit);
        staticText = "each opponent mills eight cards. If this spell was kicked, " +
                "instead each opponent mills half their library, rounded up";
    }

    private MaddeningCacophonyEffect(final MaddeningCacophonyEffect effect) {
        super(effect);
    }

    @Override
    public MaddeningCacophonyEffect copy() {
        return new MaddeningCacophonyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean kicked = KickedCondition.ONCE.apply(game, source);
        for (UUID playerId : game.getOpponents(source.getControllerId())) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            int toMill = 8;
            if (kicked) {
                toMill = Math.floorDiv(player.getLibrary().size(), 2) + player.getLibrary().size() % 2;
            }
            player.millCards(toMill, source, game);
        }
        return true;
    }
}
