package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EndOfCombatTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleEvasionAbility;
import mage.abilities.condition.common.AttackedOrBlockedThisCombatSourceCondition;
import mage.abilities.condition.common.IsStepCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.RemoveCounterSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.watchers.common.AttackedOrBlockedThisCombatWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ClockworkSteed extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("artifact creatures");

    static {
        filter.add(CardType.ARTIFACT.getPredicate());
    }

    public ClockworkSteed(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}");
        this.subtype.add(SubType.HORSE);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // Clockwork Steed enters the battlefield with four +1/+0 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.P1P0.createInstance(4)), "with four +1/+0 counters on it"));

        // Clockwork Steed can't be blocked by artifact creatures.
        this.addAbility(new SimpleEvasionAbility(new CantBeBlockedByCreaturesSourceEffect(filter, Duration.WhileOnBattlefield)));

        // At end of combat, if Clockwork Steed attacked or blocked this combat, remove a +1/+0 counter from it.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new EndOfCombatTriggeredAbility(
                        new RemoveCounterSourceEffect(CounterType.P1P0.createInstance()), false
                ), AttackedOrBlockedThisCombatSourceCondition.instance, "At end of combat, " +
                "if {this} attacked or blocked this combat, remove a +1/+0 counter from it."
        ), new AttackedOrBlockedThisCombatWatcher());

        // {X}, {tap}: Put up to X +1/+0 counters on Clockwork Steed. This ability can't cause the total number of +1/+0 counters on Clockwork Steed to be greater than four. Activate this ability only during your upkeep.
        Ability ability = new ConditionalActivatedAbility(
                Zone.BATTLEFIELD, new ClockworkSteedEffect(),
                new ManaCostsImpl<>("{X}"), new IsStepCondition(PhaseStep.UPKEEP)
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private ClockworkSteed(final ClockworkSteed card) {
        super(card);
    }

    @Override
    public ClockworkSteed copy() {
        return new ClockworkSteed(this);
    }
}

class ClockworkSteedEffect extends OneShotEffect {

    ClockworkSteedEffect() {
        super(Outcome.Benefit);
        staticText = "put up to X +1/+0 counters on {this}. This ability can't cause " +
                "the total number of +1/+0 counters on {this} to be greater than four";
    }

    private ClockworkSteedEffect(final ClockworkSteedEffect effect) {
        super(effect);
    }

    @Override
    public ClockworkSteedEffect copy() {
        return new ClockworkSteedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (player == null || permanent == null) {
            return false;
        }
        int maxCounters = Integer.min(
                4 - permanent.getCounters(game).getCount(CounterType.P1P0), source.getManaCostsToPay().getX()
        );
        if (maxCounters < 1) {
            return false;
        }
        int toAdd = player.getAmount(
                0, maxCounters, "Choose how many +1/+0 counters to put on " + permanent.getName(), game
        );
        return toAdd > 0 && permanent.addCounters(
                CounterType.P1P0.createInstance(toAdd), source.getControllerId(),
                source, game, null, true, 4
        );
    }
}
