package mage.abilities.effects.keyword;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.AddCardSubTypeTargetEffect;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.OrcArmyToken;
import mage.game.permanent.token.SliverArmyToken;
import mage.game.permanent.token.Token;
import mage.game.permanent.token.ZombieArmyToken;
import mage.game.permanent.token.custom.CreatureToken;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 * @author TheElk801
 */
public class AmassEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterControlledPermanent("Army you control");

    static {
        filter.add(SubType.ARMY.getPredicate());
    }

    private final DynamicValue amount;
    private final SubType subType;

    public AmassEffect(int amount, SubType subType) {
        this(StaticValue.get(amount), subType);
    }

    public AmassEffect(DynamicValue amount, SubType subType) {
        this(amount, subType, true);
    }

    public AmassEffect(DynamicValue amount, SubType subType, boolean withReminder) {
        super(Outcome.BoostCreature);
        this.amount = amount.copy();
        this.subType = subType;

        staticText = "amass " + subType + "s " + amount + ".";
        if (withReminder) {
            staticText += " <i>(Put " + CardUtil.numberToText(amount.toString(), "a")
                    + " +1/+1 counter" + (amount.toString().equals("1") ? " " : "s ") + "on an Army you control. It's also "
                    + subType.getIndefiniteArticle() + ' ' + subType + ". If you don't control an Army, "
                    + "create a 0/0 black " + subType + " Army creature token first.)</i>";
        }
    }

    private AmassEffect(final AmassEffect effect) {
        super(effect);
        this.amount = effect.amount.copy();
        this.subType = effect.subType;
    }

    @Override
    public AmassEffect copy() {
        return new AmassEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return doAmass(amount.calculate(game, source, this), subType, game, source) != null;
    }

    private static Token makeToken(SubType subType) {
        switch (subType) {
            case ORC:
                return new OrcArmyToken();
            case ZOMBIE:
                return new ZombieArmyToken();
            case SLIVER:
                return new SliverArmyToken();
            default:
                return new CreatureToken(
                        0, 0, "", subType, SubType.ARMY
                ).withColor("B");
        }
    }

    public static Permanent doAmass(int xValue, SubType subType, Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return null;
        }
        if (!game.getBattlefield().contains(filter, source, game, 1)) {
            makeToken(subType).putOntoBattlefield(1, game, source);
        }
        Target target = new TargetPermanent(filter);
        target.setNotTarget(true);
        player.choose(Outcome.BoostCreature, target, source, game);
        Permanent permanent = game.getPermanent(target.getFirstTarget());
        if (permanent == null) {
            return null;
        }
        if (!permanent.hasSubtype(subType, game)) {
            game.addEffect(new AddCardSubTypeTargetEffect(subType, Duration.Custom)
                    .setTargetPointer(new FixedTarget(permanent, game)), source);
        }
        if (xValue > 0) {
            permanent.addCounters(
                    CounterType.P1P1.createInstance(xValue),
                    source.getControllerId(), source, game
            );
        }
        return permanent;
    }
}
