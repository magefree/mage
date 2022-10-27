package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CompletedDungeonCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.TakeTheInitiativeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;
import mage.watchers.common.CompletedDungeonWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RavenloftAdventurer extends CardImpl {

    public RavenloftAdventurer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.subtype.add(SubType.ASSASSIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // When Ravenloft Adventurer enters the battlefield, you take the initiative.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new TakeTheInitiativeEffect()));

        // If a creature an opponent controls would die, instead exile it and put a hit counter on it.
        this.addAbility(new SimpleStaticAbility(new RavenloftAdventurerReplacementEffect()));

        // Whenever Ravenloft Adventurer attacks, if you've completed a dungeon, defending player loses 1 life for each card they own in exile with a hit counter on it.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new AttacksTriggeredAbility(
                        new RavenloftAdventurerLifeEffect(), false, "", SetTargetPointer.PLAYER
                ), CompletedDungeonCondition.instance, "Whenever {this} attacks, if you've completed a dungeon, " +
                "defending player loses 1 life for each card they own in exile with a hit counter on it."
        ).addHint(CompletedDungeonCondition.getHint()), new CompletedDungeonWatcher());
    }

    private RavenloftAdventurer(final RavenloftAdventurer card) {
        super(card);
    }

    @Override
    public RavenloftAdventurer copy() {
        return new RavenloftAdventurer(this);
    }
}

class RavenloftAdventurerReplacementEffect extends ReplacementEffectImpl {

    RavenloftAdventurerReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Exile);
        staticText = "if a creature an opponent controls would die, instead exile it and put a hit counter on it";
    }

    private RavenloftAdventurerReplacementEffect(final RavenloftAdventurerReplacementEffect effect) {
        super(effect);
    }

    @Override
    public RavenloftAdventurerReplacementEffect copy() {
        return new RavenloftAdventurerReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent permanent = ((ZoneChangeEvent) event).getTarget();
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null || permanent == null
                || !controller.hasOpponent(permanent.getControllerId(), game)) {
            return false;
        }

        return CardUtil.moveCardWithCounter(game, source, controller, permanent, Zone.EXILED, CounterType.HIT.createInstance());
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        ZoneChangeEvent zce = (ZoneChangeEvent) event;
        return zce.isDiesEvent() && zce.getTarget().isCreature(game);
    }
}

class RavenloftAdventurerLifeEffect extends OneShotEffect {

    RavenloftAdventurerLifeEffect() {
        super(Outcome.Benefit);
    }

    private RavenloftAdventurerLifeEffect(final RavenloftAdventurerLifeEffect effect) {
        super(effect);
    }

    @Override
    public RavenloftAdventurerLifeEffect copy() {
        return new RavenloftAdventurerLifeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (player == null) {
            return false;
        }
        int count = game
                .getExile()
                .getAllCards(game, player.getId())
                .stream()
                .filter(card -> card.getCounters(game).containsKey(CounterType.HIT))
                .mapToInt(x -> 1)
                .sum();
        return count > 0 && player.loseLife(count, game, source, false) > 0;
    }
}
