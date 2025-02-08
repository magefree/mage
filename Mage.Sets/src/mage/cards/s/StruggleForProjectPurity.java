package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.common.ModeChoiceSourceCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ChooseModeEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author JayDi85
 */
public final class StruggleForProjectPurity extends CardImpl {

    private static final String ruleTrigger1 = "&bull  Brotherhood &mdash; At the beginning of your upkeep, each opponent draws a card. You draw a card for each card drawn this way.";
    private static final String ruleTrigger2 = "&bull  Enclave &mdash; Whenever a player attacks you with one or more creatures, that player gets twice that many rad counters.";

    public StruggleForProjectPurity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{U}");

        // As Struggle for Project Purity enters, choose Brotherhood or Enclave.
        this.addAbility(new EntersBattlefieldAbility(new ChooseModeEffect("Brotherhood or Enclave?", "Brotherhood", "Enclave"), null,
                "As {this} enters, choose Brotherhood or Enclave.", ""));

        // * Brotherhood - At the beginning of your upkeep, each opponent draws a card. You draw a card for each card drawn this way.
        Ability ability = new ConditionalTriggeredAbility(
                new BeginningOfUpkeepTriggeredAbility(new StruggleForProjectDrawEffect()),
                new ModeChoiceSourceCondition("Brotherhood"),
                ruleTrigger1);
        this.addAbility(ability);

        // * Enclave - Whenever a player attacks you with one or more creatures, that player gets twice that many rad counters.
        ability = new ConditionalTriggeredAbility(
                new StruggleForProjectRadCountersTriggeredAbility(),
                new ModeChoiceSourceCondition("Enclave"),
                ruleTrigger2);
        this.addAbility(ability);
    }

    private StruggleForProjectPurity(final StruggleForProjectPurity card) {
        super(card);
    }

    @Override
    public StruggleForProjectPurity copy() {
        return new StruggleForProjectPurity(this);
    }
}

class StruggleForProjectDrawEffect extends OneShotEffect {

    StruggleForProjectDrawEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Each opponent draws a card. You draw a card for each card drawn this way.";
    }

    private StruggleForProjectDrawEffect(final StruggleForProjectDrawEffect effect) {
        super(effect);
    }

    @Override
    public StruggleForProjectDrawEffect copy() {
        return new StruggleForProjectDrawEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        int count = game
                .getOpponents(source.getControllerId(), true)
                .stream()
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .mapToInt(player -> player.drawCards(1, source, game))
                .sum();
        if (count > 0) {
            controller.drawCards(count, source, game);
            return true;
        }
        return false;
    }
}

class StruggleForProjectRadCountersTriggeredAbility extends TriggeredAbilityImpl {

    public StruggleForProjectRadCountersTriggeredAbility() {
        super(Zone.BATTLEFIELD, null);
        setTriggerPhrase("Whenever a player attacks you with one or more creatures, ");
    }

    private StruggleForProjectRadCountersTriggeredAbility(final StruggleForProjectRadCountersTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public StruggleForProjectRadCountersTriggeredAbility copy() {
        return new StruggleForProjectRadCountersTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Player attackingPlayer = game.getPlayer(event.getPlayerId());
        if (attackingPlayer == null) {
            return false;
        }

        Set<UUID> attackersOnYou = game.getCombat().getGroups().stream()
                .filter(g -> Objects.equals(g.getDefenderId(), getControllerId()))
                .flatMap(g -> g.getAttackers().stream())
                .collect(Collectors.toSet());
        if (attackersOnYou.isEmpty()) {
            return false;
        }

        this.getEffects().clear();
        FilterCreaturePermanent filter = new FilterCreaturePermanent();
        filter.add(new ControllerIdPredicate(event.getPlayerId()));
        Effect effect = new AddCountersTargetEffect(
                CounterType.RAD.createInstance(),
                StaticValue.get(attackersOnYou.size() * 2)
        );
        effect.setTargetPointer(new FixedTarget(attackingPlayer.getId()));
        this.getEffects().add(effect);
        return true;
    }
}
