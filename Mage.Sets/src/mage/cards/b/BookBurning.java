package mage.cards.b;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author tomd1990
 */
public final class BookBurning extends CardImpl {

    public BookBurning(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}");

        // Any player may have Book Burning deal 6 damage to them. If no one does, target player puts the top six cards of their library into their graveyard.
        this.getSpellAbility().addEffect(new BookBurningMillEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private BookBurning(final BookBurning card) {
        super(card);
    }

    @Override
    public BookBurning copy() {
        return new BookBurning(this);
    }
}

class BookBurningMillEffect extends OneShotEffect {

    public BookBurningMillEffect() {
        super(Outcome.Detriment);
        staticText = "Any player may have {this} deal 6 damage to them. If no one does, target player mills six cards";
    }

    private BookBurningMillEffect(final BookBurningMillEffect effect) {
        super(effect);
    }

    @Override
    public BookBurningMillEffect copy() {
        return new BookBurningMillEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject sourceObject = source.getSourceObject(game);
        if (sourceObject != null) {
            boolean millCards = true;
            for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null && player.chooseUse(Outcome.Detriment, "Have " + sourceObject.getLogName() + " deal 6 damage to you?", source, game)) {
                    millCards = false;
                    player.damage(6, source.getSourceId(), source, game);
                    game.informPlayers(player.getLogName() + " has " + sourceObject.getLogName() + " deal 6 damage to them");
                }
            }
            if (millCards) {
                Player targetPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
                if (targetPlayer != null) {
                    targetPlayer.moveCards(targetPlayer.getLibrary().getTopCards(game, 6), Zone.GRAVEYARD, source, game);
                }
            }
            return true;
        }
        return false;
    }
}
