package mage.cards.n;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityControllerEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPlayer;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.other.PlayerIdPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @author Grath
 */
public final class NobleHeritage extends CardImpl {

    public NobleHeritage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.BACKGROUND);

        // Commander creatures you own have “When this creature enters the battlefield and at the beginning of your upkeep, each player may put two +1/+1 counters on a creature they control. For each opponent who does, you gain protection from that player until your next turn.” (You can’t be targeted, dealt damage, or enchanted by anything controlled by that player.)
        this.addAbility(new SimpleStaticAbility(new GainAbilityAllEffect(new NobleHeritageTriggeredAbility(), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_CREATURES_OWNED_COMMANDER
        )));
    }

    private NobleHeritage(final NobleHeritage card) {
        super(card);
    }

    @Override
    public NobleHeritage copy() {
        return new NobleHeritage(this);
    }
}

class NobleHeritageTriggeredAbility extends TriggeredAbilityImpl {

    public NobleHeritageTriggeredAbility() {
        super(Zone.BATTLEFIELD, null, false);
    }

    public NobleHeritageTriggeredAbility(final NobleHeritageTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public NobleHeritageTriggeredAbility copy() {
        return new NobleHeritageTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD || event.getType() == GameEvent.EventType.UPKEEP_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        this.getEffects().clear();
        boolean result;
        if (event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD) {
            result = event.getTargetId().equals(this.getSourceId());
        } else {
            result = event.getPlayerId().equals(this.getControllerId());
        }
        if (result) {
            Permanent enchantment = game.getPermanentOrLKIBattlefield(getSourceId());
            if (enchantment != null) {
                Effect effect = new NobleHeritageEffect();
                this.getEffects().add(effect);
            } else {
                result = false;
            }
        }
        return result;
    }

    @Override
    public String getRule() {
        return "When this creature enters the battlefield and at the beginning of your upkeep, each player may put two +1/+1 counters on a creature they control. For each opponent who does, you gain protection from that player until your next turn.";
    }
}

class NobleHeritageEffect extends OneShotEffect {

    public NobleHeritageEffect() {
        super(Outcome.Benefit);
        this.staticText = "each player may put two +1/+1 counters on a creature they control. "
                + "If a player does, creatures that player controls can't attack you or planeswalkers you control until your next turn";
    }

    public NobleHeritageEffect(final NobleHeritageEffect effect) {
        super(effect);
    }

    @Override
    public NobleHeritageEffect copy() {
        return new NobleHeritageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            List<UUID> players = new ArrayList<>();
            List<UUID> creatures = new ArrayList<>();
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    if (player.chooseUse(outcome, "Put two +1/+1 counters on a creature you control?", source, game)) {
                        Target target = new TargetControlledCreaturePermanent(new FilterControlledCreaturePermanent("a creature you control (to add two +1/+1 counters on it)"));
                        if (player.choose(outcome, target, source, game)) {
                            creatures.add(target.getFirstTarget());
                            players.add(player.getId());
                        }

                    }
                }
            }
            for (UUID creatureId : creatures) {
                Permanent creature = game.getPermanent(creatureId);
                if (creature != null) {
                    creature.addCounters(CounterType.P1P1.createInstance(2), creature.getControllerId(), source, game);
                }
            }
            for (UUID playerId : players) {
                if (!Objects.equals(playerId, source.getControllerId())) {
                    FilterPlayer filter = new FilterPlayer();
                    filter.add(new PlayerIdPredicate(playerId));
                    game.addEffect(new GainAbilityControllerEffect(
                            new ProtectionAbility(filter), Duration.UntilYourNextTurn
                    ), source);
                }
            }
            return true;
        }
        return false;
    }
}
