package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author L_J
 */
public final class FreeForAll extends CardImpl {

    public FreeForAll(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{U}");

        // When Free-for-All enters the battlefield, exile all creatures face down.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new FreeForAllExileAllEffect()));

        // At the beginning of each player's upkeep, that player chooses a card exiled with Free-for-All at random and puts it onto the battlefield.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new FreeForAllReturnFromExileEffect(), TargetController.ANY, false));

        // When Free-for-All leaves the battlefield, put all cards exiled with it into their owners' graveyards.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new FreeForAllLeavesBattlefieldEffect(), false));
    }

    private FreeForAll(final FreeForAll card) {
        super(card);
    }

    @Override
    public FreeForAll copy() {
        return new FreeForAll(this);
    }
}

class FreeForAllExileAllEffect extends OneShotEffect {

    FreeForAllExileAllEffect() {
        super(Outcome.Exile);
        staticText = "exile all creatures face down";
    }

    private FreeForAllExileAllEffect(final FreeForAllExileAllEffect effect) {
        super(effect);
    }

    @Override
    public FreeForAllExileAllEffect copy() {
        return new FreeForAllExileAllEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_PERMANENT_CREATURE, source.getControllerId(), source.getSourceId(), game
        ).stream().forEach(cards::add);
        player.moveCardsToExile(cards.getCards(game), source, game, false, CardUtil.getExileZoneId(game, source), CardUtil.getSourceName(game, source));
        cards.getCards(game)
                .stream()
                .filter(card -> game.getState().getZone(card.getId()) == Zone.EXILED)
                .forEach(card -> card.setFaceDown(true, game));
        return true;
    }
}

class FreeForAllReturnFromExileEffect extends OneShotEffect {

    FreeForAllReturnFromExileEffect() {
        super(Outcome.PutCardInPlay);
        staticText = "that player chooses a card exiled with {this} at random and puts it onto the battlefield";
    }

    private FreeForAllReturnFromExileEffect(final FreeForAllReturnFromExileEffect effect) {
        super(effect);
    }

    @Override
    public FreeForAllReturnFromExileEffect copy() {
        return new FreeForAllReturnFromExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(game.getActivePlayerId());
        ExileZone exZone = game.getExile().getExileZone(CardUtil.getExileZoneId(game, source));
        if (player == null || exZone == null || exZone.isEmpty()) {
            return false;
        }
        Cards exiledCards = new CardsImpl(exZone.getCards(game));
        return player.moveCards(exiledCards.getRandom(game), Zone.BATTLEFIELD, source, game);
    }
}

class FreeForAllLeavesBattlefieldEffect extends OneShotEffect {

    FreeForAllLeavesBattlefieldEffect() {
        super(Outcome.Detriment);
        this.staticText = "put all cards exiled with it into their owners' graveyards";
    }

    private FreeForAllLeavesBattlefieldEffect(final FreeForAllLeavesBattlefieldEffect effect) {
        super(effect);
    }

    @Override
    public FreeForAllLeavesBattlefieldEffect copy() {
        return new FreeForAllLeavesBattlefieldEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        ExileZone exZone = game.getExile().getExileZone(CardUtil.getExileZoneId(game, source));
        return controller != null
                && exZone != null
                && controller.moveCards(exZone.getCards(game), Zone.GRAVEYARD, source, game);
    }
}
