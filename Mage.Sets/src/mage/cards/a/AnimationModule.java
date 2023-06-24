
package mage.cards.a;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.ServoToken;
import mage.players.Player;
import mage.target.common.TargetPermanentOrPlayer;

/**
 *
 * @author emerald000
 */
public final class AnimationModule extends CardImpl {

    public AnimationModule(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        // Whenever one or more +1/+1 counters are put on a permanent you control, you may pay {1}. If you do, create a 1/1 colorless Servo artifact creature token.
        this.addAbility(new AnimationModuleTriggeredAbility());

        // {3}, {T}: Choose a counter on target permanent or player. Give that permanent or player another counter of that kind.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AnimationModuleEffect(), new GenericManaCost(3));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPermanentOrPlayer());
        this.addAbility(ability);
    }

    private AnimationModule(final AnimationModule card) {
        super(card);
    }

    @Override
    public AnimationModule copy() {
        return new AnimationModule(this);
    }
}

class AnimationModuleTriggeredAbility extends TriggeredAbilityImpl {

    AnimationModuleTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DoIfCostPaid(new CreateTokenEffect(new ServoToken()), new GenericManaCost(1)), false);
    }

    AnimationModuleTriggeredAbility(final AnimationModuleTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public AnimationModuleTriggeredAbility copy() {
        return new AnimationModuleTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COUNTERS_ADDED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getData().equals(CounterType.P1P1.getName())) {
            Permanent permanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
            if (permanent == null) {
                permanent = game.getPermanentEntering(event.getTargetId());
            }
            return permanent != null && permanent.isControlledBy(this.getControllerId());
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever one or more +1/+1 counters are put on a permanent you control, you may pay {1}. If you do, create a 1/1 colorless Servo artifact creature token.";
    }
}

class AnimationModuleEffect extends OneShotEffect {

    AnimationModuleEffect() {
        super(Outcome.Neutral);
        this.staticText = "Choose a counter on target permanent or player. Give that permanent or player another counter of that kind";
    }

    AnimationModuleEffect(final AnimationModuleEffect effect) {
        super(effect);
    }

    @Override
    public AnimationModuleEffect copy() {
        return new AnimationModuleEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent permanent = game.getPermanent(this.getTargetPointer().getFirst(game, source));
            if (permanent != null) {
                if (!permanent.getCounters(game).isEmpty()) {
                    if (permanent.getCounters(game).size() == 1) {
                        for (Counter counter : permanent.getCounters(game).values()) {
                            Counter newCounter = new Counter(counter.getName());
                            permanent.addCounters(newCounter, source.getControllerId(), source, game);
                        }
                    } else {
                        Choice choice = new ChoiceImpl(true);
                        Set<String> choices = new LinkedHashSet<>();
                        for (Counter counter : permanent.getCounters(game).values()) {
                            choices.add(counter.getName());
                        }
                        choice.setChoices(choices);
                        choice.setMessage("Choose a counter");
                        if (controller.choose(Outcome.Benefit, choice, game)) {
                            for (Counter counter : permanent.getCounters(game).values()) {
                                if (counter.getName().equals(choice.getChoice())) {
                                    Counter newCounter = new Counter(counter.getName());
                                    permanent.addCounters(newCounter, source.getControllerId(), source, game);
                                    break;
                                }
                            }
                        } else {
                            return false;
                        }
                    }
                }
            } else {
                Player player = game.getPlayer(this.getTargetPointer().getFirst(game, source));
                if (player != null) {
                    if (!player.getCounters().isEmpty()) {
                        if (player.getCounters().size() == 1) {
                            for (Counter counter : player.getCounters().values()) {
                                Counter newCounter = new Counter(counter.getName());
                                player.addCounters(newCounter, source.getControllerId(), source, game);
                            }
                        } else {
                            Choice choice = new ChoiceImpl(true);
                            Set<String> choices = new LinkedHashSet<>();
                            for (Counter counter : player.getCounters().values()) {
                                choices.add(counter.getName());
                            }
                            choice.setChoices(choices);
                            choice.setMessage("Choose a counter");
                            if (controller.choose(Outcome.Benefit, choice, game)) {
                                for (Counter counter : player.getCounters().values()) {
                                    if (counter.getName().equals(choice.getChoice())) {
                                        Counter newCounter = new Counter(counter.getName());
                                        player.addCounters(newCounter, source.getControllerId(), source, game);
                                        break;
                                    }
                                }
                            } else {
                                return false;
                            }
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}
