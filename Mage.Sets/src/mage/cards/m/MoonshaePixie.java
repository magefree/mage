package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.WatcherScope;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.target.TargetPermanent;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MoonshaePixie extends AdventureCard {

    private static final Hint hint = new ValueHint("Opponents dealt damage this turn", MoonshaePixieValue.instance);

    public MoonshaePixie(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.INSTANT}, "{3}{U}", "Pixie Dust", "{1}{U}");

        this.subtype.add(SubType.FAERIE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Moonshae Pixie enters the battlefield, draw cards equal to the number of opponents who were dealt combat damage this turn.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new DrawCardSourceControllerEffect(MoonshaePixieValue.instance)
                        .setText("draw cards equal to the number of opponents who were dealt combat damage this turn")
        ).addHint(hint), new MoonshaePixieWatcher());

        // Pixie Dust
        // Up to three target creatures gain flying until end of turn.
        this.getSpellCard().getSpellAbility().addEffect(new GainAbilityTargetEffect(FlyingAbility.getInstance()));
        this.getSpellCard().getSpellAbility().addTarget(new TargetPermanent(
                0, 3, StaticFilters.FILTER_PERMANENT_CREATURES
        ));
    }

    private MoonshaePixie(final MoonshaePixie card) {
        super(card);
    }

    @Override
    public MoonshaePixie copy() {
        return new MoonshaePixie(this);
    }
}

enum MoonshaePixieValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return MoonshaePixieWatcher.getDamaged(sourceAbility.getControllerId(), game);
    }

    @Override
    public MoonshaePixieValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "";
    }

    @Override
    public String toString() {
        return "";
    }
}

class MoonshaePixieWatcher extends Watcher {

    private final Set<UUID> players = new HashSet<>();

    MoonshaePixieWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DAMAGED_PLAYER
                && ((DamagedEvent) event).isCombatDamage()) {
            players.add(event.getTargetId());
        }
    }

    @Override
    public void reset() {
        players.clear();
        super.reset();
    }

    static int getDamaged(UUID playerId, Game game) {
        return game
                .getState()
                .getWatcher(MoonshaePixieWatcher.class)
                .players
                .stream()
                .filter(game.getOpponents(playerId)::contains)
                .mapToInt(x -> 1)
                .sum();
    }
}
