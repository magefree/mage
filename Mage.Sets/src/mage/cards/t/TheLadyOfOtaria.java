package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.LookLibraryControllerEffect;
import mage.abilities.effects.common.RevealLibraryPickControllerEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.target.common.TargetControlledPermanent;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheLadyOfOtaria extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledPermanent(SubType.DWARF, "untapped Dwarves you control");
    private static final FilterCard filter2 = new FilterCard("Dwarf cards");

    static {
        filter.add(TappedPredicate.UNTAPPED);
        filter2.add(SubType.DWARF.getPredicate());
    }

    public TheLadyOfOtaria(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.AVATAR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // You may tap three untapped Dwarves you control rather than pay this spell's mana cost.
        this.addAbility(new AlternativeCostSourceAbility(
                new TapTargetCost(new TargetControlledPermanent(3, filter))
        ));

        // At the beginning of each end step, if a land you controlled was put into your graveyard from the battlefield this turn, reveal the top four cards of your library. Put any number of Dwarf cards from among them into your hand and the rest on the bottom of your library in a random order.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new RevealLibraryPickControllerEffect(
                        4, Integer.MAX_VALUE, filter2,
                        LookLibraryControllerEffect.PutCards.HAND,
                        LookLibraryControllerEffect.PutCards.BOTTOM_RANDOM, false
                ), TargetController.ANY, TheLadyOfOtariaCondition.instance, false
        ).addHint(TheLadyOfOtariaCondition.getHint()), new TheLadyOfOtariaWatcher());
    }

    private TheLadyOfOtaria(final TheLadyOfOtaria card) {
        super(card);
    }

    @Override
    public TheLadyOfOtaria copy() {
        return new TheLadyOfOtaria(this);
    }
}

enum TheLadyOfOtariaCondition implements Condition {
    instance;
    private static final Hint hint = new ConditionHint(
            instance, "A land you controlled was put into your graveyard"
    );

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public String toString() {
        return "if a land you controlled was put into your graveyard from the battlefield this turn";
    }

    public static Hint getHint() {
        return hint;
    }
}

class TheLadyOfOtariaWatcher extends Watcher {

    private final Set<UUID> playerMap = new HashSet<>();

    TheLadyOfOtariaWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.ZONE_CHANGE) {
            return;
        }
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.isDiesEvent()
                && zEvent.getTarget() != null
                && zEvent.getTarget().isLand(game)
                && zEvent.getTarget().isOwnedBy(zEvent.getTarget().getControllerId())) {
            playerMap.add(zEvent.getTarget().getControllerId());
        }
    }

    @Override
    public void reset() {
        super.reset();
        playerMap.clear();
    }

    static boolean checkPlayer(Ability source, Game game) {
        return game
                .getState()
                .getWatcher(TheLadyOfOtariaWatcher.class)
                .playerMap
                .contains(source.getControllerId());
    }
}
