package mage.cards.a;

import java.util.*;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.constants.*;
import mage.abilities.keyword.IslandwalkAbility;
import mage.abilities.keyword.CrewAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.watchers.Watcher;

/**
 *
 * @author Grath
 */
public final class Adrestia extends CardImpl {

    private static final Condition condition = AdrestiaCondition.instance;

    public Adrestia(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Islandwalk
        this.addAbility(new IslandwalkAbility());

        // Whenever Adrestia attacks, if an Assassin crewed it this turn, draw a card. Adrestia becomes an Assassin in addition to its other types until end of turn.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new AttacksTriggeredAbility(new DrawCardSourceControllerEffect(1), false),
                condition, "Whenever Adrestia attacks, if an Assassin crewed it this turn, draw a card. Adrestia becomes an Assassin in addition to its other types until end of turn.");
        ability.addEffect(new AdrestiaEffect());
        ability.addHint(AdrestiaCondition.getHint());
        this.addAbility(ability, new AdrestiaWatcher());

        // Crew 1
        this.addAbility(new CrewAbility(1));

    }

    private Adrestia(final Adrestia card) {
        super(card);
    }

    @Override
    public Adrestia copy() {
        return new Adrestia(this);
    }
}

class AdrestiaEffect extends ContinuousEffectImpl {

    public AdrestiaEffect() {
        super(Duration.EndOfTurn, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Benefit);
    }

    protected AdrestiaEffect(final AdrestiaEffect effect) {
        super(effect);
    }

    @Override
    public AdrestiaEffect copy() {
        return new AdrestiaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent == null) {
            return false;
        }
        permanent.addSubType(game, SubType.ASSASSIN);
        return true;
    }
}

enum AdrestiaCondition implements Condition {
    instance;
    private static final Hint hint = new ConditionHint(instance, "an Assassin crewed it this turn");

    @Override
    public boolean apply(Game game, Ability source) {
        return AdrestiaWatcher.checkIfAssassinCrewed(source.getSourcePermanentOrLKI(game), game);
    }

    public static Hint getHint() {
        return hint;
    }
}

class AdrestiaWatcher extends Watcher {

    private final static FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.ASSASSIN, "an Assassin");
    private final Map<MageObjectReference, Boolean> crewMap = new HashMap<>();

    public AdrestiaWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.CREWED_VEHICLE) {
            MageObjectReference vehicle = new MageObjectReference(event.getSourceId(), game);
            Permanent crewer = game.getPermanentOrLKIBattlefield(event.getTargetId());
            if (crewer != null) {
                if (!crewMap.containsKey(vehicle)) {
                    crewMap.put(vehicle, filter.match(crewer, game));
                }
                else {
                    crewMap.put(vehicle, crewMap.get(vehicle) || filter.match(crewer, game));
                }
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        crewMap.clear();
    }

    public static boolean checkIfAssassinCrewed(Permanent vehicle, Game game) {
        return game
                .getState()
                .getWatcher(AdrestiaWatcher.class)
                .crewMap
                .getOrDefault(new MageObjectReference(vehicle, game), Boolean.FALSE);
    }
}
