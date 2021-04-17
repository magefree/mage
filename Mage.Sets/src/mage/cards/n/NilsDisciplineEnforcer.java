package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.combat.CantAttackYouUnlessPayManaAllEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.TargetAdjuster;
import mage.target.targetpointer.EachTargetPointer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NilsDisciplineEnforcer extends CardImpl {

    public NilsDisciplineEnforcer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // At the beginning of your end step, for each player, put a +1/+1 counter on up to one target creature that player controls.
        Ability ability = new BeginningOfEndStepTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance())
                        .setTargetPointer(new EachTargetPointer())
                        .setText("for each player, put a +1/+1 counter on up to one target creature that player controls"),
                TargetController.YOU, false
        );
        ability.setTargetAdjuster(NilsDisciplineEnforcerAdjuster.instance);
        this.addAbility(ability);

        // Each creature with one or more counters on it can't attack you or planeswalkers you control unless its controller pays {X}, where X is the number of counters on that creature.
        this.addAbility(new SimpleStaticAbility(new NilsDisciplineEnforcerEffect()));
    }

    private NilsDisciplineEnforcer(final NilsDisciplineEnforcer card) {
        super(card);
    }

    @Override
    public NilsDisciplineEnforcer copy() {
        return new NilsDisciplineEnforcer(this);
    }
}

enum NilsDisciplineEnforcerAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        for (UUID playerId : game.getState().getPlayersInRange(ability.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            FilterPermanent filter = new FilterCreaturePermanent("creature controlled by " + player.getName());
            ability.addTarget(new TargetPermanent(0, 1, filter));
        }
    }
}

class NilsDisciplineEnforcerEffect extends CantAttackYouUnlessPayManaAllEffect {

    NilsDisciplineEnforcerEffect() {
        super(null, true);
        staticText = "Each creature with one or more counters on it can't attack you or planeswalkers you control " +
                "unless its controller pays {X}, where X is the number of counters on that creature.";
    }

    private NilsDisciplineEnforcerEffect(NilsDisciplineEnforcerEffect effect) {
        super(effect);
    }

    @Override
    public ManaCosts getManaCostToPay(GameEvent event, Ability source, Game game) {
        Permanent permanent = game.getPermanent(event.getSourceId());
        if (permanent == null) {
            return null;
        }
        int count = permanent
                .getCounters(game)
                .keySet()
                .stream()
                .mapToInt(permanent.getCounters(game)::getCount)
                .sum();
        if (count < 1) {
            return null;
        }
        return new ManaCostsImpl("{" + count + '}');
    }

    @Override
    public NilsDisciplineEnforcerEffect copy() {
        return new NilsDisciplineEnforcerEffect(this);
    }
}
