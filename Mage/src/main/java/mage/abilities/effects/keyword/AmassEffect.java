package mage.abilities.effects.keyword;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.ZombieArmyToken;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public class AmassEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterControlledPermanent("Army you control");

    static {
        filter.add(new SubtypePredicate(SubType.ARMY));
    }

    private final DynamicValue amassNumber;
    private UUID amassedCreatureId = null;

    public AmassEffect(int amassNumber) {
        this(new StaticValue(amassNumber));
        staticText = "amass " + amassNumber + ". <i>(Put " + CardUtil.numberToText(amassNumber)
                + " +1/+1 counter" + (amassNumber > 1 ? "s " : " ")
                + "on an Army you control. If you don’t control one, "
                + "create a 0/0 black Zombie Army creature token first.)</i>";
    }

    public AmassEffect(DynamicValue amassNumber) {
        super(Outcome.BoostCreature);
        this.amassNumber = amassNumber;
        staticText = "amass X, where X is the number of " + amassNumber.getMessage() + ". <i>(Put X +1/+1 counters"
                + "on an Army you control. If you don’t control one, "
                + "create a 0/0 black Zombie Army creature token first.)</i>";
    }

    private AmassEffect(final AmassEffect effect) {
        super(effect);
        this.amassNumber = effect.amassNumber;
    }

    @Override
    public AmassEffect copy() {
        return new AmassEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int xValue = amassNumber.calculate(game, source, this);
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        if (!game.getBattlefield().contains(filter, source.getControllerId(), 1, game)) {
            new CreateTokenEffect(new ZombieArmyToken()).apply(game, source);
        }
        Target target = new TargetPermanent(filter);
        target.setNotTarget(true);
        if (!player.choose(outcome, target, source.getSourceId(), game)) {
            return false;
        }
        Permanent permanent = game.getPermanent(target.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        permanent.addCounters(CounterType.P1P1.createInstance(xValue), source, game);
        this.amassedCreatureId = permanent.getId();
        return true;
    }

    public UUID getAmassedCreatureId() {
        return amassedCreatureId;
    }
}
