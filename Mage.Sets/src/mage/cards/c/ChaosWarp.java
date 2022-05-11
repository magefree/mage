
package mage.cards.c;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterPermanentCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

/**
 *
 * @author Mitchel Stein
 *
 */
public final class ChaosWarp extends CardImpl {

    public ChaosWarp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{R}");

        // The owner of target permanent shuffles it into their library,
        this.getSpellAbility().addEffect(new ChaosWarpShuffleIntoLibraryEffect());
        this.getSpellAbility().addTarget(new TargetPermanent());
        //then reveals the top card of their library.
        //If it's a permanent card, they put it onto the battlefield.
        this.getSpellAbility().addEffect(new ChaosWarpRevealEffect());

    }

    private ChaosWarp(final ChaosWarp card) {
        super(card);
    }

    @Override
    public ChaosWarp copy() {
        return new ChaosWarp(this);
    }
}

class ChaosWarpShuffleIntoLibraryEffect extends OneShotEffect {

    public ChaosWarpShuffleIntoLibraryEffect() {
        super(Outcome.Detriment);
        this.staticText = "The owner of target permanent shuffles it into their library";
    }

    public ChaosWarpShuffleIntoLibraryEffect(final ChaosWarpShuffleIntoLibraryEffect effect) {
        super(effect);
    }

    @Override
    public ChaosWarpShuffleIntoLibraryEffect copy() {
        return new ChaosWarpShuffleIntoLibraryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(targetPointer.getFirst(game, source));
        if (permanent != null) {
            Player owner = game.getPlayer(permanent.getOwnerId());
            if (owner != null) {
                owner.moveCardToLibraryWithInfo(permanent, source, game, Zone.BATTLEFIELD, true, true);
                owner.shuffleLibrary(source, game);
                return true;
            }
        }
        return false;
    }
}

class ChaosWarpRevealEffect extends OneShotEffect {

    public ChaosWarpRevealEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = ", then reveals the top card of their library. If it's a permanent card, they put it onto the battlefield";
    }

    public ChaosWarpRevealEffect(final ChaosWarpRevealEffect effect) {
        super(effect);
    }

    @Override
    public ChaosWarpRevealEffect copy() {
        return new ChaosWarpRevealEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = (Permanent) game.getLastKnownInformation(targetPointer.getFirst(game, source), Zone.BATTLEFIELD);
        if (permanent == null) {
            return false;
        }
        Player owner = game.getPlayer(permanent.getOwnerId());
        MageObject sourceObject = game.getObject(source);
        if (owner == null || sourceObject == null) {
            return false;
        }

        if (owner.getLibrary().hasCards()) {
            Card card = owner.getLibrary().getFromTop(game);
            if (card != null) {
                Cards cards = new CardsImpl(card);
                owner.revealCards(sourceObject.getIdName(), cards, game);
                if (new FilterPermanentCard().match(card, game)) {
                    owner.moveCards(card, Zone.BATTLEFIELD, source, game);
                }
            }
        }
        return true;
    }
}
