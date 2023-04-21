package mage.abilities.keyword;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;
import mage.watchers.Watcher;

import java.util.*;

/**
 * @author LevelX2
 * <p>
 * 702.74. Hideaway 702.74a Hideaway represents a static ability and a triggered
 * ability. "Hideaway" means "This permanent enters the battlefield tapped" and
 * "When this permanent enters the battlefield, look at the top four cards of
 * your library. Exile one of them face down and put the rest on the bottom of
 * your library in any order. The exiled card gains 'Any player who has
 * controlled the permanent that exiled this card may look at this card in the
 * exile zone.'"
 */
public class HideawayAbility extends EntersBattlefieldTriggeredAbility {

    private final int amount;

    public HideawayAbility(int amount) {
        super(new HideawayExileEffect(amount));
        this.amount = amount;
        this.addWatcher(new HideawayWatcher());
    }

    private HideawayAbility(final HideawayAbility ability) {
        super(ability);
        this.amount = ability.amount;
    }

    @Override
    public String getRule() {
        return "Hideaway " + this.amount + " <i>(When this permanent enters the battlefield, look at the top "
                + CardUtil.numberToText(this.amount) + " cards of your library, exile one face down, " +
                "then put the rest on the bottom of your library in a random order.)</i>";
    }

    @Override
    public HideawayAbility copy() {
        return new HideawayAbility(this);
    }
}

class HideawayExileEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("card to exile face down");
    private final int amount;

    HideawayExileEffect(int amount) {
        super(Outcome.Benefit);
        this.amount = amount;
    }

    private HideawayExileEffect(final HideawayExileEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public HideawayExileEffect copy() {
        return new HideawayExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Cards cards = new CardsImpl(controller.getLibrary().getTopCards(game, amount));
        if (cards.isEmpty()) {
            return true;
        }
        TargetCard target = new TargetCard(Zone.LIBRARY, filter);
        target.setNotTarget(true);
        controller.choose(Outcome.Detriment, cards, target, source, game);
        Card card = cards.get(target.getFirstTarget(), game);
        if (card != null) {
            controller.moveCardsToExile(
                    card, source, game, false,
                    CardUtil.getExileZoneId(game, source),
                    "Hideaway (" + CardUtil.getSourceName(game, source) + ')'
            );
            game.addEffect(new HideawayLookAtFaceDownCardEffect().setTargetPointer(new FixedTarget(card, game)), source);
            card.setFaceDown(true, game);
        }
        cards.retainZone(Zone.LIBRARY, game);
        controller.putCardsOnBottomOfLibrary(cards, game, source, false);
        return true;
    }
}

class HideawayLookAtFaceDownCardEffect extends AsThoughEffectImpl {

    HideawayLookAtFaceDownCardEffect() {
        super(AsThoughEffectType.LOOK_AT_FACE_DOWN, Duration.EndOfGame, Outcome.Benefit);
    }

    private HideawayLookAtFaceDownCardEffect(final HideawayLookAtFaceDownCardEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public HideawayLookAtFaceDownCardEffect copy() {
        return new HideawayLookAtFaceDownCardEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        return Objects.equals(objectId, getTargetPointer().getFirst(game, source))
                && HideawayWatcher.check(affectedControllerId, source, game);
    }
}

class HideawayWatcher extends Watcher {

    private final Map<MageObjectReference, Set<UUID>> morMap = new HashMap<>();

    HideawayWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        Permanent permanent;
        UUID playerId;
        switch (event.getType()) {
            case GAINED_CONTROL:
                permanent = game.getPermanent(event.getTargetId());
                playerId = event.getPlayerId();
                break;
            case ENTERS_THE_BATTLEFIELD:
                permanent = ((EntersTheBattlefieldEvent) event).getTarget();
                playerId = permanent.getControllerId();
                break;
            case BEGINNING_PHASE_PRE:
                if (game.getTurnNum() == 1) {
                    morMap.clear();
                }
            default:
                return;
        }
        morMap.computeIfAbsent(new MageObjectReference(permanent, game), x -> new HashSet<>()).add(playerId);
    }

    static boolean check(UUID playerId, Ability source, Game game) {
        return game
                .getState()
                .getWatcher(HideawayWatcher.class)
                .morMap
                .getOrDefault(new MageObjectReference(source), Collections.emptySet())
                .contains(playerId);
    }
}
