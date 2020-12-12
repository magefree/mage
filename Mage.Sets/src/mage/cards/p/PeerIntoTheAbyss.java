package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PeerIntoTheAbyss extends CardImpl {

    public PeerIntoTheAbyss(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{B}{B}{B}");

        // Target player draws cards equal to half the number of cards in their library and loses half their life. Round up each time.
        this.getSpellAbility().addEffect(new PeerIntoTheAbyssEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private PeerIntoTheAbyss(final PeerIntoTheAbyss card) {
        super(card);
    }

    @Override
    public PeerIntoTheAbyss copy() {
        return new PeerIntoTheAbyss(this);
    }
}

class PeerIntoTheAbyssEffect extends OneShotEffect {

    PeerIntoTheAbyssEffect() {
        super(Outcome.Benefit);
        staticText = "Target player draws cards equal to half the number of cards in their library " +
                "and loses half their life. Round up each time.";
    }

    private PeerIntoTheAbyssEffect(final PeerIntoTheAbyssEffect effect) {
        super(effect);
    }

    @Override
    public PeerIntoTheAbyssEffect copy() {
        return new PeerIntoTheAbyssEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (targetPlayer == null) {
            return false;
        }
        targetPlayer.drawCards((int) Math.ceil(targetPlayer.getLibrary().size() / 2.0), source, game);
        targetPlayer.loseLife((int) Math.ceil(targetPlayer.getLife() / 2.0), game, source, false);
        return true;
    }
}