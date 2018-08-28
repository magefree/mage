
package mage.cards.f;

import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author L_J
 */
public final class FreeForAll extends CardImpl {

    public FreeForAll(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{U}");

        // When Free-for-All enters the battlefield, exile all creatures face down.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new FreeForAllExileAllEffect()));

        // At the beginning of each player's upkeep, that player chooses a card exiled with Free-for-All at random and puts it onto the battlefield.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new FreeForAllReturnFromExileEffect(), TargetController.ANY, false, true));        

        // When Free-for-All leaves the battlefield, put all cards exiled with it into their owners' graveyards.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new FreeForAllLeavesBattlefieldEffect(), false));
    }

    public FreeForAll(final FreeForAll card) {
        super(card);
    }

    @Override
    public FreeForAll copy() {
        return new FreeForAll(this);
    }
}

class FreeForAllExileAllEffect extends OneShotEffect {

    public FreeForAllExileAllEffect() {
        super(Outcome.Exile);
        staticText = "exile all creatures face down";
    }

    public FreeForAllExileAllEffect(final FreeForAllExileAllEffect effect) {
        super(effect);
    }

    @Override
    public FreeForAllExileAllEffect copy() {
        return new FreeForAllExileAllEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> permanents = game.getBattlefield().getActivePermanents(new FilterCreaturePermanent(), source.getControllerId(), source.getSourceId(), game);
        for (Permanent permanent : permanents) {
            Card card = game.getCard(permanent.getId());
            permanent.moveToExile(source.getSourceId(), "Free-for-All", source.getSourceId(), game);
            if (card != null) {
                card.setFaceDown(true, game);
            }
        }
        return true;
    }
}

class FreeForAllReturnFromExileEffect extends OneShotEffect {

    public FreeForAllReturnFromExileEffect() {
        super(Outcome.PutCardInPlay);
        staticText = "that player chooses a card exiled with Free-for-All at random and puts it onto the battlefield";
    }

    public FreeForAllReturnFromExileEffect(final FreeForAllReturnFromExileEffect effect) {
        super(effect);
    }

    @Override
    public FreeForAllReturnFromExileEffect copy() {
        return new FreeForAllReturnFromExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player != null) {
            ExileZone exZone = game.getExile().getExileZone(source.getSourceId());
            if (exZone != null) {
                Cards exiledCards = new CardsImpl(exZone.getCards(game));
                return player.moveCards(exiledCards.getRandom(game), Zone.BATTLEFIELD, source, game);
            }
            return true;
        }
        return false;
    }
}

class FreeForAllLeavesBattlefieldEffect extends OneShotEffect {

    public FreeForAllLeavesBattlefieldEffect() {
        super(Outcome.Detriment);
        this.staticText = "put all cards exiled with it into their owners' graveyards";
    }

    public FreeForAllLeavesBattlefieldEffect(final FreeForAllLeavesBattlefieldEffect effect) {
        super(effect);
    }

    @Override
    public FreeForAllLeavesBattlefieldEffect copy() {
        return new FreeForAllLeavesBattlefieldEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            ExileZone exZone = game.getExile().getExileZone(source.getSourceId());
            if (exZone != null) {
                return controller.moveCards(exZone.getCards(game), Zone.GRAVEYARD, source, game, false, false, true, null);
            }
            return true;
        }
        return false;
    }
}
