package mage.cards.c;

import mage.Mana;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.mana.AddManaToManaPoolTargetControllerEffect;
import mage.abilities.mana.DelayedTriggeredManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ManaEvent;
import mage.game.events.TappedForManaEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author L_J
 */
public final class ChaosMoon extends CardImpl {

    public ChaosMoon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}");

        // At the beginning of each upkeep, count the number of permanents. If the number is odd, until end of turn, red creatures get +1/+1 and whenever a player taps a Mountain for mana, that player adds {R} (in addition to the mana the land produces). If the number is even, until end of turn, red creatures get -1/-1 and if a player taps a Mountain for mana, that Mountain produces colorless mana instead of any other type.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new ChaosMoonEffect(), TargetController.ANY, false));
    }

    private ChaosMoon(final ChaosMoon card) {
        super(card);
    }

    @Override
    public ChaosMoon copy() {
        return new ChaosMoon(this);
    }
}

class ChaosMoonEffect extends OneShotEffect {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("red creatures");

    static {
        filter.add(new ColorPredicate(ObjectColor.RED));
    }

    ChaosMoonEffect() {
        super(Outcome.Neutral);
        this.staticText = "count the number of permanents. If the number is odd, " +
                "until end of turn, red creatures get +1/+1 and whenever a player taps a Mountain for mana, " +
                "that player adds {R} (in addition to the mana the land produces). If the number is even, " +
                "until end of turn, red creatures get -1/-1 and if a player taps a Mountain for mana, " +
                "that Mountain produces colorless mana instead of any other type";
    }

    private ChaosMoonEffect(final ChaosMoonEffect effect) {
        super(effect);
    }

    @Override
    public ChaosMoonEffect copy() {
        return new ChaosMoonEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int permanentsInPlay = game.getBattlefield().count(
                StaticFilters.FILTER_PERMANENT, source.getSourceId(), source.getControllerId(), game
        );
        // Odd
        if (permanentsInPlay % 2 == 1) {
            game.addEffect(new BoostAllEffect(1, 1, Duration.EndOfTurn, filter, false), source);
            new CreateDelayedTriggeredAbilityEffect(new ChaosMoonOddTriggeredAbility()).apply(game, source);
        } // Even
        else {
            game.addEffect(new BoostAllEffect(-1, -1, Duration.EndOfTurn, filter, false), source);
            game.addEffect(new ChaosMoonEvenReplacementEffect(), source);
        }
        return true;
    }
}

class ChaosMoonOddTriggeredAbility extends DelayedTriggeredManaAbility {

    ChaosMoonOddTriggeredAbility() {
        super(new AddManaToManaPoolTargetControllerEffect(new Mana(ColoredManaSymbol.R), "their"), Duration.EndOfTurn, false);
        this.usesStack = false;
    }

    private ChaosMoonOddTriggeredAbility(ChaosMoonOddTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TAPPED_FOR_MANA;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent land = ((TappedForManaEvent) event).getPermanent();
        if (land == null || !land.hasSubtype(SubType.MOUNTAIN, game)) {
            return false;
        }
        this.getEffects().setTargetPointer(new FixedTarget(land.getControllerId()));
        return true;
    }

    @Override
    public ChaosMoonOddTriggeredAbility copy() {
        return new ChaosMoonOddTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Until end of turn, whenever a player taps a Mountain for mana, that player adds {R}";
    }
}

class ChaosMoonEvenReplacementEffect extends ReplacementEffectImpl {

    ChaosMoonEvenReplacementEffect() {
        super(Duration.EndOfTurn, Outcome.Neutral);
        staticText = "Until end of turn, if a Mountain is tapped for mana, it produces colorless mana instead of any other type.";
    }

    private ChaosMoonEvenReplacementEffect(final ChaosMoonEvenReplacementEffect effect) {
        super(effect);
    }

    @Override
    public ChaosMoonEvenReplacementEffect copy() {
        return new ChaosMoonEvenReplacementEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        ManaEvent manaEvent = (ManaEvent) event;
        Mana mana = manaEvent.getMana();
        mana.setToMana(Mana.ColorlessMana(mana.count()));
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TAPPED_FOR_MANA;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent land = ((TappedForManaEvent) event).getPermanent();
        return land != null && land.hasSubtype(SubType.MOUNTAIN, game);
    }
}
