package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.ConvokeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetNonlandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TemporalCleansing extends CardImpl {

    public TemporalCleansing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{U}");

        // Convoke
        this.addAbility(new ConvokeAbility());

        // The owner of target nonland permanent puts it into their library second from the top or on the bottom.
        this.getSpellAbility().addEffect(new TemporalCleansingEffect());
        this.getSpellAbility().addTarget(new TargetNonlandPermanent());
    }

    private TemporalCleansing(final TemporalCleansing card) {
        super(card);
    }

    @Override
    public TemporalCleansing copy() {
        return new TemporalCleansing(this);
    }
}

class TemporalCleansingEffect extends OneShotEffect {

    TemporalCleansingEffect() {
        super(Outcome.Benefit);
        staticText = "the owner of target nonland permanent puts it into their library second from the top or on the bottom";
    }

    private TemporalCleansingEffect(final TemporalCleansingEffect effect) {
        super(effect);
    }

    @Override
    public TemporalCleansingEffect copy() {
        return new TemporalCleansingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        Player player = game.getPlayer(permanent.getOwnerId());
        if (player == null) {
            return false;
        }
        if (player.chooseUse(
                outcome, "Put " + permanent.getIdName() + " second from the top or on the bottom?",
                null, "Second from top", "Bottom", source, game
        )) {
            return player.putCardOnTopXOfLibrary(permanent, game, source, 2, true);
        }
        return player.putCardsOnBottomOfLibrary(permanent, game, source, false);
    }
}
