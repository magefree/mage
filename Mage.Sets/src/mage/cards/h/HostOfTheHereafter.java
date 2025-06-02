package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.EntersBattlefieldWithXCountersEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.MoveCountersFromSourceToTargetEffect;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.counters.Counters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.CounterAnyPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author Jmlundeen
 */
public final class HostOfTheHereafter extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("this creature or another creature you control");

    static {
        filter.add(CounterAnyPredicate.instance);
    }

    public HostOfTheHereafter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{G}");
        
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // This creature enters with two +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)),
                null, "This creature enters with two +1/+1 counters on it.", "")
        );

        // Whenever this creature or another creature you control dies, if it had counters on it, put its counters on up to one target creature you control.
        Ability ability = new DiesCreatureTriggeredAbility(new HostOfTheHereafterDiesEffect(), false, filter)
                .setTriggerPhrase("Whenever " + filter.getMessage() + " dies, if it had counters on it, ");
        ability.addTarget(new TargetControlledCreaturePermanent(0, 1));
        this.addAbility(ability);
    }

    private HostOfTheHereafter(final HostOfTheHereafter card) {
        super(card);
    }

    @Override
    public HostOfTheHereafter copy() {
        return new HostOfTheHereafter(this);
    }
}

class HostOfTheHereafterDiesEffect extends OneShotEffect {

    HostOfTheHereafterDiesEffect() {
        super(Outcome.Benefit);
        staticText = "put its counters on up to one target creature you control";
    }

    private HostOfTheHereafterDiesEffect(final HostOfTheHereafterDiesEffect effect) {
        super(effect);
    }

    @Override
    public HostOfTheHereafterDiesEffect copy() {
        return new HostOfTheHereafterDiesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        Permanent deadCreature = (Permanent) this.getValue("creatureDied");
        if (permanent == null || deadCreature == null) {
            return false;
        }
        Counters counters = deadCreature.getCounters(game);
        counters.values().stream()
                .filter(counter -> counter.getCount() > 0)
                .forEach(counter -> permanent.addCounters(counter, source, game));
        return true;
    }
}
