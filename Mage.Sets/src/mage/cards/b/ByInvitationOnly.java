package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ByInvitationOnly extends CardImpl {

    public ByInvitationOnly(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{W}{W}");

        // Choose a number between 0 and 13. Each player sacrifices that many creatures.
        this.getSpellAbility().addEffect(new ByInvitationOnlyEffect());
    }

    private ByInvitationOnly(final ByInvitationOnly card) {
        super(card);
    }

    @Override
    public ByInvitationOnly copy() {
        return new ByInvitationOnly(this);
    }
}

class ByInvitationOnlyEffect extends OneShotEffect {

    ByInvitationOnlyEffect() {
        super(Outcome.Benefit);
        staticText = "choose a number between 0 and 13. Each player sacrifices that many creatures";
    }

    private ByInvitationOnlyEffect(final ByInvitationOnlyEffect effect) {
        super(effect);
    }

    @Override
    public ByInvitationOnlyEffect copy() {
        return new ByInvitationOnlyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int number = player.getAmount(
                0, 13, "Choose a number between 0 and 13", game
        );
        return new SacrificeAllEffect(
                number, StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT
        ).apply(game, source);
    }
}
