package mage.abilities.common;

import mage.abilities.Ability;
import mage.abilities.costs.common.ExileSourceFromGraveCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.constants.AbilityWord;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.Counter;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.util.CardUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public class RenewAbility extends ActivateAsSorceryActivatedAbility {

    public RenewAbility(String manaString, Counter... counters) {
        super(Zone.GRAVEYARD, new RenewEffect(counters), new ManaCostsImpl<>(manaString));
        this.addCost(new ExileSourceFromGraveCost());
        this.addTarget(new TargetCreaturePermanent());
        this.setAbilityWord(AbilityWord.RENEW);
    }

    private RenewAbility(final RenewAbility ability) {
        super(ability);
    }

    @Override
    public RenewAbility copy() {
        return new RenewAbility(this);
    }
}

class RenewEffect extends OneShotEffect {

    private final List<Counter> counters = new ArrayList<>();

    RenewEffect(Counter... counters) {
        super(Outcome.Benefit);
        for (Counter counter : counters) {
            this.counters.add(counter);
        }
        staticText = makeText(this.counters);
    }

    private RenewEffect(final RenewEffect effect) {
        super(effect);
        for (Counter counter : effect.counters) {
            this.counters.add(counter.copy());
        }
    }

    @Override
    public RenewEffect copy() {
        return new RenewEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        for (Counter counter : counters) {
            permanent.addCounters(counter, source, game);
        }
        return true;
    }

    private static String makeText(List<Counter> counters) {
        return "put " + CardUtil.concatWithAnd(
                counters.stream().map(Counter::getDescription).collect(Collectors.toList())
        ) + " on target creature";
    }
}
