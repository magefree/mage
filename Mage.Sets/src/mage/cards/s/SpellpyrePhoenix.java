package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.WatcherScope;
import mage.filter.FilterCard;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetCardInYourGraveyard;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SpellpyrePhoenix extends CardImpl {

    private static final FilterCard filter
            = new FilterInstantOrSorceryCard("instant or sorcery card with a cycling ability from your graveyard");

    static {
        filter.add(new AbilityPredicate(CyclingAbility.class));
    }

    public SpellpyrePhoenix(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");

        this.subtype.add(SubType.PHOENIX);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Spellpyre Phoenix enters the battlefield, you may return target instant or sorcery card with a cycling ability from your graveyard to your hand.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new ReturnFromGraveyardToHandTargetEffect(), true
        );
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);

        // At the beginning of each end step, if you cycled two or more cards this turn, return Spellpyre Phoenix from your graveyard to your hand.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfEndStepTriggeredAbility(
                        new ReturnSourceFromGraveyardToHandEffect(),
                        TargetController.ANY, false
                ), SpellpyrePhoenixCondition.instance, "At the beginning of each end step, " +
                "if you cycled two or more cards this turn, return {this} from your graveyard to your hand."
        ), new SpellpyrePhoenixWatcher());
    }

    private SpellpyrePhoenix(final SpellpyrePhoenix card) {
        super(card);
    }

    @Override
    public SpellpyrePhoenix copy() {
        return new SpellpyrePhoenix(this);
    }
}

enum SpellpyrePhoenixCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        SpellpyrePhoenixWatcher watcher = game.getState().getWatcher(SpellpyrePhoenixWatcher.class);
        return watcher != null && watcher.checkCycleCount(source.getControllerId());
    }
}

class SpellpyrePhoenixWatcher extends Watcher {

    private final Map<UUID, Integer> cycleMap = new HashMap();

    SpellpyrePhoenixWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.CYCLED_CARD) {
            cycleMap.compute(event.getPlayerId(), (u, i) -> i == null ? 1 : i + 1);
        }
    }

    @Override
    public void reset() {
        super.reset();
        cycleMap.clear();
    }

    boolean checkCycleCount(UUID playerId) {
        return cycleMap.getOrDefault(playerId, 0) >= 2;
    }
}
