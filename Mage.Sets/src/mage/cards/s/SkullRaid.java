package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.ForetellAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SkullRaid extends CardImpl {

    public SkullRaid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}");

        // Target opponent discards two cards. If fewer than two cards were discarded this way, you draw cards equal to the difference.
        this.getSpellAbility().addEffect(new SkullRaidEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());

        // Foretell {1}{B}
        this.addAbility(new ForetellAbility(this, "{1}{B}"));
    }

    private SkullRaid(final SkullRaid card) {
        super(card);
    }

    @Override
    public SkullRaid copy() {
        return new SkullRaid(this);
    }
}

class SkullRaidEffect extends OneShotEffect {

    SkullRaidEffect() {
        super(Outcome.Benefit);
        staticText = "target opponent discards two cards. " +
                "If fewer than two cards were discarded this way, " +
                "you draw cards equal to the difference";
    }

    private SkullRaidEffect(final SkullRaidEffect effect) {
        super(effect);
    }

    @Override
    public SkullRaidEffect copy() {
        return new SkullRaidEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(source.getFirstTarget());
        if (opponent == null) {
            return false;
        }
        int discarded = opponent.discard(2, false, false, source, game).size();
        if (discarded >= 2) {
            return true;
        }
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            player.drawCards(2 - discarded, source, game);
        }
        return true;
    }
}
