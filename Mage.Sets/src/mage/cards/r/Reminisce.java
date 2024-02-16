package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author daagar
 */
public final class Reminisce extends CardImpl {

    public Reminisce(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}");

        // Target player shuffles their graveyard into their library.
        this.getSpellAbility().addEffect(new ReminisceEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private Reminisce(final Reminisce card) {
        super(card);
    }

    @Override
    public Reminisce copy() {
        return new Reminisce(this);
    }
}

class ReminisceEffect extends OneShotEffect {

    ReminisceEffect() {
        super(Outcome.Neutral);
        this.staticText = "Target player shuffles their graveyard into their library";
    }

    private ReminisceEffect(final ReminisceEffect effect) {
        super(effect);
    }

    @Override
    public ReminisceEffect copy() {
        return new ReminisceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (player != null) {
            player.shuffleCardsToLibrary(player.getGraveyard(), game, source);
            return true;
        }
        return false;
    }
}
