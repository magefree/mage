package mage.cards.n;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.FullPartyCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.decorator.ConditionalRestrictionEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.hint.common.PartyCountHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.watchers.Watcher;

import java.util.*;

/**
 * @author TheElk801
 */
public final class NimbleTrapfinder extends CardImpl {

    public NimbleTrapfinder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Nimble Trapfinder can't be blocked if you had another Cleric, Rogue, Warrior, or Wizard enter the battlefield under your control this turn.
        this.addAbility(new SimpleStaticAbility(new ConditionalRestrictionEffect(
                new CantBeBlockedSourceEffect(), NimbleTrapfinderCondition.instance,
                "{this} can't be blocked if you had another Cleric, Rogue, Warrior, or Wizard " +
                        "enter the battlefield under your control this turn"
        )).addHint(NimbleTrapfinderCondition.getHint()), new NimbleTrapfinderWatcher());

        // At the beginning of combat on your turn, if you have a full party, creatures you control gain "Whenever this creature deals combat damage to a player, draw a card" until end of turn.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfCombatTriggeredAbility(
                        new GainAbilityAllEffect(new DealsCombatDamageToAPlayerTriggeredAbility(
                                new DrawCardSourceControllerEffect(1), false
                        ), Duration.EndOfTurn, StaticFilters.FILTER_CONTROLLED_CREATURES),
                        TargetController.YOU, false
                ), FullPartyCondition.instance, "At the beginning of combat on your turn, " +
                "if you have a full party, creatures you control gain " +
                "\"Whenever this creature deals combat damage to a player, draw a card\" until end of turn."
        ).addHint(PartyCountHint.instance));
    }

    private NimbleTrapfinder(final NimbleTrapfinder card) {
        super(card);
    }

    @Override
    public NimbleTrapfinder copy() {
        return new NimbleTrapfinder(this);
    }
}

enum NimbleTrapfinderCondition implements Condition {
    instance;

    private static final Hint hint = new ConditionHint(instance);

    static Hint getHint() {
        return hint;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        NimbleTrapfinderWatcher watcher = game.getState().getWatcher(NimbleTrapfinderWatcher.class);
        return watcher != null && watcher.checkPlayer(source.getSourceId(), game);
    }

    @Override
    public String toString() {
        return "You had another Cleric, Rogue, Warrior, or Wizard enter the battlefield under your control this turn";
    }
}

class NimbleTrapfinderWatcher extends Watcher {

    private static final FilterPermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(Predicates.or(
                SubType.CLERIC.getPredicate(),
                SubType.ROGUE.getPredicate(),
                SubType.WARRIOR.getPredicate(),
                SubType.WIZARD.getPredicate()
        ));
    }

    private final Map<UUID, Set<MageObjectReference>> playerMap = new HashMap<>();

    NimbleTrapfinderWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.ENTERS_THE_BATTLEFIELD) {
            return;
        }
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent == null || !filter.match(permanent, game)) {
            return;
        }
        playerMap
                .computeIfAbsent(event.getPlayerId(), u -> new HashSet<>())
                .add(new MageObjectReference(permanent, game));
    }

    @Override
    public void reset() {
        super.reset();
        playerMap.clear();
    }

    boolean checkPlayer(UUID sourceId, Game game) {
        Permanent permanent = game.getPermanent(sourceId);
        if (permanent == null) {
            return !playerMap.computeIfAbsent(game.getOwnerId(sourceId), u -> new HashSet<>()).isEmpty();
        }
        return playerMap
                .computeIfAbsent(permanent.getControllerId(), u -> new HashSet<>())
                .stream()
                .anyMatch(mor -> !mor.refersTo(permanent, game));
    }
}
