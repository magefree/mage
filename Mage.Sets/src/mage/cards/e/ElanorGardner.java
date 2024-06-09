package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.FoodToken;
import mage.target.common.TargetCardInLibrary;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ElanorGardner extends CardImpl {

    public ElanorGardner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HALFLING);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // When Elanor Gardner enters the battlefield, create a Food token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new FoodToken())));

        // At the beginning of your end step, if you sacrificed a Food this turn, you may search your library for a basic land card, put that card onto the battlefield tapped, then shuffle.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new SearchLibraryPutInPlayEffect(
                        new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND), true, true
                ), TargetController.YOU, ElanorGardnerCondition.instance, true
        ).addHint(ElanorGardnerCondition.getHint()), new ElanorGardnerWatcher());
    }

    private ElanorGardner(final ElanorGardner card) {
        super(card);
    }

    @Override
    public ElanorGardner copy() {
        return new ElanorGardner(this);
    }
}

enum ElanorGardnerCondition implements Condition {
    instance;
    private static final Hint hint = new ConditionHint(instance, "You sacrificed a Food this turn");

    public static Hint getHint() {
        return hint;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return ElanorGardnerWatcher.checkPlayer(game, source);
    }

    @Override
    public String toString() {
        return "you sacrificed a Food this turn";
    }
}

class ElanorGardnerWatcher extends Watcher {

    private final Set<UUID> set = new HashSet<>();

    ElanorGardnerWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.SACRIFICED_PERMANENT) {
            return;
        }
        Permanent permanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
        if (permanent != null && permanent.hasSubtype(SubType.FOOD, game)) {
            set.add(event.getPlayerId());
        }
    }

    @Override
    public void reset() {
        super.reset();
        set.clear();
    }

    static boolean checkPlayer(Game game, Ability source) {
        return game
                .getState()
                .getWatcher(ElanorGardnerWatcher.class)
                .set
                .contains(source.getControllerId());
    }
}
