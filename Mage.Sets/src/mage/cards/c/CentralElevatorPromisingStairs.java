package mage.cards.c;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.UnlockThisDoorTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.WinGameSourceControllerEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.abilities.hint.ValueHint;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardSetInfo;
import mage.cards.RoomCard;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

import java.util.*;
import java.util.stream.Collectors;

/**
 *
 * @author notgreat
 */
public final class CentralElevatorPromisingStairs extends RoomCard {
    public CentralElevatorPromisingStairs(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, "{3}{U}", "{2}{U}");

        // Central Elevator
        // When you unlock this door, search your library for a Room card that doesn't have the same name as a Room you control, reveal it, put it into your hand, then shuffle.
        this.getLeftHalfCard().addAbility(new UnlockThisDoorTriggeredAbility(
                new ElevatorSearchEffect(), false, true));

        // Promising Stairs
        // At the beginning of your upkeep, surveil 1. You win the game if there are eight or more different names among unlocked doors of Rooms you control.
        Ability right = new BeginningOfUpkeepTriggeredAbility(
                new SurveilEffect(1));
        right.addEffect(new ConditionalOneShotEffect(new WinGameSourceControllerEffect(), PromisingStairsCondition.instance));
        right.addHint(new ValueHint("Different names among unlocked doors of Rooms you control", UnlockedDoorNamesYouControlCount.instance));
        this.getRightHalfCard().addAbility(right);
    }

    private CentralElevatorPromisingStairs(final CentralElevatorPromisingStairs card) {
        super(card);
    }

    @Override
    public CentralElevatorPromisingStairs copy() {
        return new CentralElevatorPromisingStairs(this);
    }
}

class ElevatorSearchEffect extends OneShotEffect {
    private static final FilterPermanent filterRoomPermanent = new FilterControlledPermanent(SubType.ROOM);

    ElevatorSearchEffect() {
        super(Outcome.Benefit);
        staticText = "search your library for a Room card that doesn't have the same name as a Room you control, reveal it, put it into your hand, then shuffle.";
    }

    private ElevatorSearchEffect(final ElevatorSearchEffect effect) {
        super(effect);
    }

    @Override
    public ElevatorSearchEffect copy() {
        return new ElevatorSearchEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        List<Predicate<MageObject>> predicates = game.getBattlefield()
                .getActivePermanents(filterRoomPermanent, source.getControllerId(), source, game).stream()
                .map(Permanent::getName).filter(Objects::nonNull)
                .filter(s -> !s.isEmpty())
                .map(NamePredicate::new).map(Predicates::not)
                .collect(Collectors.toList());
        FilterCard filter = new FilterCard(SubType.ROOM, "a Room card that doesn't have the same name as a Room you control");
        filter.add(Predicates.and(predicates));
        return new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter), true).apply(game, source);
    }
}

enum PromisingStairsCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return UnlockedDoorNamesYouControlCount.instance.calculate(game, source, null) >= 8;
    }

    @Override
    public String toString() {
        return "there are eight or more different names among unlocked doors of Rooms you control";
    }
}

enum UnlockedDoorNamesYouControlCount implements DynamicValue {

    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Set<String> names = new HashSet<>();
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(sourceAbility.getControllerId())) {
            if (permanent.hasSubtype(SubType.ROOM, game)) {
                String name = permanent.getName();
                if (name == null || name.isEmpty()) {
                    continue;
                }
                if (name.contains(" // ")) {
                    String[] twoNames = name.split(" // ");
                    names.add(twoNames[0]);
                    names.add(twoNames[1]);
                } else {
                    names.add(name);
                }
            }
        }
        return names.size();
    }

    @Override
    public UnlockedDoorNamesYouControlCount copy() {
        return instance;
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "different names among unlocked doors of Rooms you control";
    }
}
