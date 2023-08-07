package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.target.common.TargetCreatureOrPlaneswalker;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BarrinTolarianArchmage extends CardImpl {

    private static final FilterCreatureOrPlaneswalkerPermanent filter
            = new FilterCreatureOrPlaneswalkerPermanent("other target creature or planeswalker");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public BarrinTolarianArchmage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Barrin, Tolarian Archmage enters the battlefield, return up to one other target creature or planeswalker to its owner's hand.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnToHandTargetEffect());
        ability.addTarget(new TargetCreatureOrPlaneswalker(0, 1, filter, false));
        this.addAbility(ability);

        // At the beginning of your end step, if a permanent was put into your hand from the battlefield this turn, draw a card.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfEndStepTriggeredAbility(
                        new DrawCardSourceControllerEffect(1), TargetController.YOU, false
                ), BarrinTolarianArchmageCondition.instance, "At the beginning of your end step, " +
                "if a permanent was put into your hand from the battlefield this turn, draw a card."
        ), new BarrinTolarianArchmageWatcher());
    }

    private BarrinTolarianArchmage(final BarrinTolarianArchmage card) {
        super(card);
    }

    @Override
    public BarrinTolarianArchmage copy() {
        return new BarrinTolarianArchmage(this);
    }
}

enum BarrinTolarianArchmageCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        BarrinTolarianArchmageWatcher watcher = game.getState().getWatcher(BarrinTolarianArchmageWatcher.class);
        return watcher != null && watcher.checkPlayer(source.getControllerId());
    }
}

class BarrinTolarianArchmageWatcher extends Watcher {

    private final Set<UUID> playerSet = new HashSet<>();

    BarrinTolarianArchmageWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.ZONE_CHANGE) {
            return;
        }
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.getFromZone() == Zone.BATTLEFIELD
                && zEvent.getToZone() == Zone.HAND) {
            playerSet.add(zEvent.getTarget().getOwnerId());
        }
    }

    @Override
    public void reset() {
        playerSet.clear();
        super.reset();
    }

    boolean checkPlayer(UUID playerId) {
        return playerSet.contains(playerId);
    }
}
