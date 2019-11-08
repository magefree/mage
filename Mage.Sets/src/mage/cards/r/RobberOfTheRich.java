package mage.cards.r;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.AsThoughManaEffect;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.ManaPoolItem;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RobberOfTheRich extends CardImpl {

    public RobberOfTheRich(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARCHER);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever Robber of the Rich attacks, if defending player has more cards in hand than you, exile the top card of their library. During any turn you attacked with a Rogue, you may cast that card and you may spend mana as though it were mana of any color to cast that spell.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new AttacksTriggeredAbility(
                        new RobberOfTheRichEffect(), false, "", SetTargetPointer.PLAYER
                ), RobberOfTheRichCondition.instance, "Whenever {this} attacks, " +
                "if defending player has more cards in hand than you, exile the top card of their library. " +
                "During any turn you attacked with a Rogue, you may cast that card and " +
                "you may spend mana as though it were mana of any color to cast that spell."
        ), new RobberOfTheRichWatcher());
    }

    private RobberOfTheRich(final RobberOfTheRich card) {
        super(card);
    }

    @Override
    public RobberOfTheRich copy() {
        return new RobberOfTheRich(this);
    }
}

enum RobberOfTheRichCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player player = game.getPlayer(game.getCombat().getDefendingPlayerId(source.getSourceId(), game));
        return controller != null && player != null && controller.getHand().size() < player.getHand().size();
    }
}

class RobberOfTheRichEffect extends OneShotEffect {

    RobberOfTheRichEffect() {
        super(Outcome.PutCreatureInPlay);
    }

    private RobberOfTheRichEffect(final RobberOfTheRichEffect effect) {
        super(effect);
    }

    @Override
    public RobberOfTheRichEffect copy() {
        return new RobberOfTheRichEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player damagedPlayer = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (controller == null || damagedPlayer == null) {
            return false;
        }
        MageObject sourceObject = game.getObject(source.getSourceId());
        UUID exileId = CardUtil.getCardExileZoneId(game, source);
        Card card = damagedPlayer.getLibrary().getFromTop(game);
        if (card == null || sourceObject == null) {
            return true;
        }
        // move card to exile
        controller.moveCardToExileWithInfo(card, exileId, sourceObject.getIdName(), source.getSourceId(), game, Zone.LIBRARY, true);
        // Add effects only if the card has a spellAbility (e.g. not for lands).
        if (card.getSpellAbility() == null) {
            return true;
        }
        // allow to cast the card
        game.addEffect(new RobberOfTheRichCastFromExileEffect(card.getId(), exileId), source);
        // and you may spend mana as though it were mana of any color to cast it
        ContinuousEffect effect = new RobberOfTheRichSpendAnyManaEffect();
        effect.setTargetPointer(new FixedTarget(card.getId()));
        game.addEffect(effect, source);
        return true;
    }
}

class RobberOfTheRichCastFromExileEffect extends AsThoughEffectImpl {

    private UUID cardId;
    private UUID exileId;

    RobberOfTheRichCastFromExileEffect(UUID cardId, UUID exileId) {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.Custom, Outcome.Benefit);
        this.cardId = cardId;
        this.exileId = exileId;
    }

    private RobberOfTheRichCastFromExileEffect(final RobberOfTheRichCastFromExileEffect effect) {
        super(effect);
        this.cardId = effect.cardId;
        this.exileId = effect.exileId;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public RobberOfTheRichCastFromExileEffect copy() {
        return new RobberOfTheRichCastFromExileEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        RobberOfTheRichWatcher watcher = game.getState().getWatcher(RobberOfTheRichWatcher.class);
        if (watcher == null || !watcher.getAttackedWithRogue(source.getControllerId())) {
            return false;
        }
        if (!sourceId.equals(cardId) || !source.isControlledBy(affectedControllerId)) {
            return false;
        }
        ExileZone exileZone = game.getState().getExile().getExileZone(exileId);
        if (exileZone != null && exileZone.contains(cardId)) {
            return true;
        }
        discard();
        return false;
    }
}

class RobberOfTheRichSpendAnyManaEffect extends AsThoughEffectImpl implements AsThoughManaEffect {

    RobberOfTheRichSpendAnyManaEffect() {
        super(AsThoughEffectType.SPEND_OTHER_MANA, Duration.Custom, Outcome.Benefit);
    }

    private RobberOfTheRichSpendAnyManaEffect(final RobberOfTheRichSpendAnyManaEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public RobberOfTheRichSpendAnyManaEffect copy() {
        return new RobberOfTheRichSpendAnyManaEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        FixedTarget fixedTarget = ((FixedTarget) getTargetPointer());
        return source.isControlledBy(affectedControllerId)
                && Objects.equals(objectId, fixedTarget.getTarget())
                && fixedTarget.getZoneChangeCounter() + 1 == game.getState().getZoneChangeCounter(objectId)
                && game.getState().getZone(objectId) == Zone.STACK;
    }

    @Override
    public ManaType getAsThoughManaType(ManaType manaType, ManaPoolItem mana, UUID affectedControllerId, Ability source, Game game) {
        return mana.getFirstAvailable();
    }
}

class RobberOfTheRichWatcher extends Watcher {

    private Set<UUID> rogueAttackers = new HashSet();

    RobberOfTheRichWatcher() {
        super(WatcherScope.GAME);
    }

    private RobberOfTheRichWatcher(final RobberOfTheRichWatcher watcher) {
        super(watcher);
        this.rogueAttackers.addAll(watcher.rogueAttackers);
    }

    @Override
    public RobberOfTheRichWatcher copy() {
        return new RobberOfTheRichWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.ATTACKER_DECLARED) {
            return;
        }
        Permanent permanent = game.getPermanent(event.getSourceId());
        if (permanent == null || !permanent.hasSubtype(SubType.ROGUE, game)) {
            return;
        }
        rogueAttackers.add(event.getPlayerId());
    }

    @Override
    public void reset() {
        super.reset();
        rogueAttackers.clear();
    }

    boolean getAttackedWithRogue(UUID playerId) {
        return rogueAttackers.contains(playerId);
    }
}

