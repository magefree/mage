package mage.cards.t;

import java.util.Map;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.dynamicvalue.common.SignInversionDynamicValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.Counter;
import mage.counters.Counters;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetOpponentsCreaturePermanent;

/**
 *
 * @author weirddan455
 */
public final class ThornmantleStriker extends CardImpl {

    private static final PermanentsOnBattlefieldCount elfCount
            = new PermanentsOnBattlefieldCount(new FilterControlledPermanent(SubType.ELF));
    private static final SignInversionDynamicValue negativeElfCount
            = new SignInversionDynamicValue(elfCount);

    public ThornmantleStriker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // When Thornmantle Striker enters the battlefield, choose one —
        // • Remove X counters from target permanent, where X is the number of Elves you control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ThornmantleStrikerEffect(elfCount));
        ability.addTarget(new TargetPermanent());

        // • Target creature an opponent controls gets -X/-X until end of turn, where X is the number of Elves you control.
        Mode mode = new Mode(new BoostTargetEffect(negativeElfCount, negativeElfCount, Duration.EndOfTurn, true
        ).setText("Target creature an opponent controls gets -X/-X until end of turn, where X is the number of Elves you control"));
        mode.addTarget(new TargetOpponentsCreaturePermanent());

        ability.addMode(mode);
        this.addAbility(ability);
    }

    private ThornmantleStriker(final ThornmantleStriker card) {
        super(card);
    }

    @Override
    public ThornmantleStriker copy() {
        return new ThornmantleStriker(this);
    }
}

class ThornmantleStrikerEffect extends OneShotEffect {

    private final DynamicValue xValue;

    ThornmantleStrikerEffect(DynamicValue xValue) {
        super(Outcome.AIDontUseIt);
        staticText = "Remove X counters from target permanent, where X is the number of Elves you control";
        this.xValue = xValue;
    }

    private ThornmantleStrikerEffect(final ThornmantleStrikerEffect effect) {
        super(effect);
        this.xValue = effect.xValue.copy();
    }

    @Override
    public ThornmantleStrikerEffect copy() {
        return new ThornmantleStrikerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (controller == null || permanent == null) {
            return false;
        }
        int elves = xValue.calculate(game, source, this);
        if (elves < 1) {
            return false;
        }
        // Make copy of counters to avoid concurrent modification exception
        Counters counters = permanent.getCounters(game).copy();
        int totalCounters = 0;
        for (Counter counter : counters.values()) {
            totalCounters += counter.getCount();
        }
        if (totalCounters == 0) {
            return false;
        }
        if (totalCounters <= elves) {
            for (Map.Entry<String, Counter> entry : counters.entrySet()) {
                permanent.removeCounters(entry.getKey(), entry.getValue().getCount(), source, game);
            }
            return true;
        }
        if (counters.size() == 1) {
            String counterName = counters.keySet().iterator().next();
            permanent.removeCounters(counterName, elves, source, game);
            return true;
        }
        int remainingCounters = totalCounters;
        int countersLeftToRemove = elves;
        for (Map.Entry<String, Counter> entry : counters.entrySet()) {
            String counterName = entry.getKey();
            int numCounters = entry.getValue().getCount();
            remainingCounters -= numCounters;
            int min = Math.max(0, countersLeftToRemove - remainingCounters);
            int max = Math.min(countersLeftToRemove, numCounters);
            int toRemove = controller.getAmount(min, max, counterName + " counters to remove", game);
            // Sanity check in case of GUI bugs/disconnects
            toRemove = Math.max(toRemove, min);
            toRemove = Math.min(toRemove, max);
            permanent.removeCounters(counterName, toRemove, source, game);
            countersLeftToRemove -= toRemove;
            if (countersLeftToRemove <= 0) {
                break;
            }
        }
        return true;
    }
}
