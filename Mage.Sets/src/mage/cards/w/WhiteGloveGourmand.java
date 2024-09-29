package mage.cards.w;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.FoodToken;
import mage.game.permanent.token.HumanSoldierToken;
import mage.watchers.Watcher;

import java.util.*;

/**
 * @author Susucr
 */
public final class WhiteGloveGourmand extends CardImpl {

    public WhiteGloveGourmand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When White Glove Gourmand enters the battlefield, create two 1/1 white Human Soldier creature tokens.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new HumanSoldierToken(), 2)));

        // At the beginning of your end step, if another Human died under your control this turn, create a Food token.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfEndStepTriggeredAbility(new CreateTokenEffect(new FoodToken()), TargetController.YOU, false),
                WhiteGloveGourmandCondition.instance,
                "At the beginning of your end step, if another Human died under your control this turn, create a Food token."
        ).addHint(WhiteGloveGourmandCondition.hint), new WhiteGloveGourmandWatcher());
    }

    private WhiteGloveGourmand(final WhiteGloveGourmand card) {
        super(card);
    }

    @Override
    public WhiteGloveGourmand copy() {
        return new WhiteGloveGourmand(this);
    }
}

enum WhiteGloveGourmandCondition implements Condition {
    instance;

    static final Hint hint = new ConditionHint(instance);

    @Override
    public boolean apply(Game game, Ability source) {
        MageObjectReference mor = new MageObjectReference(source.getSourceObject(game), game);
        return WhiteGloveGourmandWatcher.checkCondition(source.getControllerId(), mor, game);
    }

    @Override
    public String toString() {
        return "another Human died under your control this turn";
    }
}

class WhiteGloveGourmandWatcher extends Watcher {

    // player -> set of Human mor that dies under you control this turn.
    private final Map<UUID, Set<MageObjectReference>> diedThisTurn = new HashMap<>();

    WhiteGloveGourmandWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.ZONE_CHANGE) {
            return;
        }
        ZoneChangeEvent zce = (ZoneChangeEvent) event;
        if (!zce.isDiesEvent()) {
            return;
        }
        Permanent permanent = zce.getTarget();
        if (permanent != null && permanent.hasSubtype(SubType.HUMAN, game)) {
            diedThisTurn.computeIfAbsent(event.getPlayerId(), k -> new HashSet<>());
            diedThisTurn.get(event.getPlayerId()).add(new MageObjectReference(permanent, game));
        }
    }

    @Override
    public void reset() {
        super.reset();
        diedThisTurn.clear();
    }

    static boolean checkCondition(UUID playerId, MageObjectReference morSource, Game game) {
        WhiteGloveGourmandWatcher watcher = game.getState().getWatcher(WhiteGloveGourmandWatcher.class);
        return watcher != null && watcher
                .diedThisTurn
                .getOrDefault(playerId, Collections.emptySet())
                .stream()
                .anyMatch(mor -> !mor.equals(morSource));
    }
}