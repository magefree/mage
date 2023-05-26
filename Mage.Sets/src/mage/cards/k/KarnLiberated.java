package mage.cards.k;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetForSourceEffect;
import mage.cards.*;
import mage.constants.*;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.GameImpl;
import mage.game.command.Commander;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentImpl;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInHand;
import mage.util.CardUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author bunchOfDevs
 */
public final class KarnLiberated extends CardImpl {

    public KarnLiberated(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{7}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.KARN);
        this.setStartingLoyalty(6);

        // +4: Target player exiles a card from their hand.
        LoyaltyAbility ability1 = new LoyaltyAbility(new KarnPlayerExileEffect(), 4);
        ability1.addTarget(new TargetPlayer());
        this.addAbility(ability1);

        // -3: Exile target permanent.
        LoyaltyAbility ability2 = new LoyaltyAbility(new ExileTargetForSourceEffect(), -3);
        ability2.addTarget(new TargetPermanent());
        this.addAbility(ability2);

        // -14: Restart the game, leaving in exile all non-Aura permanent cards exiled with Karn Liberated. Then put those cards onto the battlefield under your control.
        this.addAbility(new LoyaltyAbility(new KarnLiberatedEffect(), -14));
    }

    private KarnLiberated(final KarnLiberated card) {
        super(card);
    }

    @Override
    public KarnLiberated copy() {
        return new KarnLiberated(this);
    }
}

class KarnLiberatedEffect extends OneShotEffect {

    private UUID exileId;

    public KarnLiberatedEffect() {
        super(Outcome.ExtraTurn);
        this.staticText = "Restart the game, leaving in exile all non-Aura permanent cards exiled with {this}. Then put those cards onto the battlefield under your control";
    }

    public KarnLiberatedEffect(final KarnLiberatedEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject sourceObject = source.getSourceObject(game);
        if (sourceObject == null) {
            return false;
        }
        List<Card> cards = new ArrayList<>();
        for (ExileZone zone : game.getExile().getExileZones()) {
            exileId = CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter());
            if (zone.getId().equals(exileId)) {
                for (Card card : zone.getCards(game)) {
                    if (!card.hasSubtype(SubType.AURA, game)
                            && card.isPermanent(game)) {
                        cards.add(card);
                    }
                }
            }
        }

        // dirty hack for game restart, can cause bugs due strange clear code (some data like ZCC keeping on new game)
        // see testCommanderRestoredToBattlefieldAfterKarnUltimate for more details

        game.getState().clearOnGameRestart();
        // default watchers init, TODO: remove all restart/init code to game
        ((GameImpl) game).initGameDefaultWatchers();

        for (Card card : game.getCards()) {
            game.getState().addCard(card);
        }
        for (Player player : game.getPlayers().values()) {
            if (player.canRespond()) { // only players alive are in the restarted game
                player.getGraveyard().clear();
                player.getHand().clear();
                player.getLibrary().clear();
                for (Card card : game.getCards()) {
                    if (card.isOwnedBy(player.getId()) && !card.isCopy() // no copies
                            && !player.getSideboard().contains(card.getId())
                            && !cards.contains(card)) { // not the exiled cards
                        if (game.getCommandersIds(player, CommanderCardType.ANY, false).contains(card.getId())) {
                            game.addCommander(new Commander(card)); // TODO: check restart and init
                            // no needs in initCommander call -- it's used on game startup (init)
                            game.setZone(card.getId(), Zone.COMMAND);
                        } else {
                            player.getLibrary().putOnTop(card, game);
                        }
                    }
                }
                ((GameImpl) game).initPlayerDefaultWatchers(player.getId());
                player.init(game);
            }
        }
        for (Card card : cards) {
            game.getState().setZone(card.getId(), Zone.EXILED);
            game.getExile().add(exileId, sourceObject.getIdName(), card);
        }
        game.addDelayedTriggeredAbility(new KarnLiberatedDelayedTriggeredAbility(exileId), source);
        game.setStartingPlayerId(source.getControllerId());
        game.start(null);
        return true;
    }

    @Override
    public KarnLiberatedEffect copy() {
        return new KarnLiberatedEffect(this);
    }

}

class KarnLiberatedDelayedTriggeredAbility extends DelayedTriggeredAbility {

    public KarnLiberatedDelayedTriggeredAbility(UUID exileId) {
        super(new KarnLiberatedDelayedEffect(exileId));
    }

    public KarnLiberatedDelayedTriggeredAbility(final KarnLiberatedDelayedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.BEGINNING_PHASE_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return true;
    }

    @Override
    public KarnLiberatedDelayedTriggeredAbility copy() {
        return new KarnLiberatedDelayedTriggeredAbility(this);
    }

}

class KarnLiberatedDelayedEffect extends OneShotEffect {

    private final UUID exileId;

    public KarnLiberatedDelayedEffect(UUID exileId) {
        super(Outcome.PlayForFree);
        this.exileId = exileId;
        this.staticText = "Put those cards onto the battlefield under your control";
    }

    public KarnLiberatedDelayedEffect(final KarnLiberatedDelayedEffect effect) {
        super(effect);
        this.exileId = effect.exileId;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        ExileZone exileZone = game.getExile().getExileZone(exileId);
        if (controller == null || exileZone == null) {
            return false;
        }

        // Creatures put onto the battlefield due to Karn's ability will have been under their controller's control continuously
        // since the beginning of the first turn. They can attack and their activated abilities with {T} in the cost can be activated.
        Cards cards = new CardsImpl(exileZone); // needed because putOntoTheBattlefield removes from exile
        if (cards.isEmpty()) {
            return false;
        }

        controller.moveCards(cards, Zone.BATTLEFIELD, source, game);
        for (Card card : cards.getCards(game)) {
            if (card != null) {
                Permanent permanent = game.getPermanent(card.getId());
                if (permanent != null) {
                    ((PermanentImpl) permanent).removeSummoningSickness();
                }
            }
        }

        return true;
    }

    @Override
    public KarnLiberatedDelayedEffect copy() {
        return new KarnLiberatedDelayedEffect(this);
    }

}

class KarnPlayerExileEffect extends OneShotEffect {

    public KarnPlayerExileEffect() {
        super(Outcome.Exile);
        staticText = "target player exiles a card from their hand.";
    }

    public KarnPlayerExileEffect(final KarnPlayerExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        MageObject sourceObject = source.getSourceObject(game);
        if (sourceObject == null) {
            return false;
        }
        if (player == null) {
            return false;
        }
        TargetCardInHand target = new TargetCardInHand();
        if (target.canChoose(player.getId(), source, game)
                && target.chooseTarget(Outcome.Exile, player.getId(), source, game)) {
            UUID exileId = CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter());
            return player.moveCardsToExile(new CardsImpl(target.getTargets()).getCards(game), source, game, true, exileId, sourceObject.getIdName());
        }
        return false;
    }

    @Override
    public KarnPlayerExileEffect copy() {
        return new KarnPlayerExileEffect(this);
    }

}
