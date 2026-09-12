package mage.abilities.effects.keyword;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.JacePlaneswalkerToken;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.util.CardUtil;

import java.util.List;

/**
 * @author muz
 */

// Put X loyalty counters on a Jace token you control. If you don’t control one, first create a blue Jace planeswalker token with "[−1]: Surveil 1" and "[−3]: Draw a card."
public class EmpowerJaceEffect extends OneShotEffect {

    private static final FilterPermanent FILTER_JACE = new FilterControlledPermanent("a Jace token you control");

    static {
        FILTER_JACE.add(CardType.PLANESWALKER.getPredicate());
        FILTER_JACE.add(SubType.JACE.getPredicate());
        FILTER_JACE.add(TokenPredicate.TRUE);
        FILTER_JACE.setLockedFilter(true);
    }

    protected final DynamicValue empowerNumber;
    private final String empowerText;
    private final boolean dynamic;

    public EmpowerJaceEffect(int empowerNumber) {
        this(StaticValue.get(empowerNumber), Integer.toString(empowerNumber), false);
    }

    public EmpowerJaceEffect(DynamicValue empowerNumber) {
        this(empowerNumber, "X", true);
    }

    private EmpowerJaceEffect(DynamicValue empowerNumber, String empowerText, boolean dynamic) {
        super(Outcome.Benefit);
        this.empowerNumber = empowerNumber;
        this.empowerText = empowerText;
        this.dynamic = dynamic;
        this.setText();
    }

    protected EmpowerJaceEffect(final EmpowerJaceEffect effect) {
        super(effect);
        this.empowerNumber = effect.empowerNumber.copy();
        this.empowerText = effect.empowerText;
        this.dynamic = effect.dynamic;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        List<Permanent> jaceTokens = game.getBattlefield().getActivePermanents(FILTER_JACE, source.getControllerId(), source, game);
        if (jaceTokens.isEmpty()) {
            new JacePlaneswalkerToken().putOntoBattlefield(1, game, source, player.getId());
            jaceTokens = game.getBattlefield().getActivePermanents(FILTER_JACE, source.getControllerId(), source, game);
            if (jaceTokens.isEmpty()) {
                return false;
            }
        }

        Permanent chosenJace = null;
        if (jaceTokens.size() == 1) {
            chosenJace = jaceTokens.get(0);
        } else {
            TargetPermanent target = new TargetPermanent(FILTER_JACE);
            target.withNotTarget(true);
            if (player.choose(Outcome.Benefit, target, source, game)) {
                chosenJace = game.getPermanent(target.getFirstTarget());
            }
        }

        if (chosenJace == null) {
            return false;
        }

        int amount = empowerNumber.calculate(game, source, this);
        chosenJace.addCounters(CounterType.LOYALTY.createInstance(amount), source.getControllerId(), source, game);
        return true;
    }

    @Override
    public EmpowerJaceEffect copy() {
        return new EmpowerJaceEffect(this);
    }

    private void setText() {
        StringBuilder sb = new StringBuilder("empower Jace ").append(empowerText);
        if (dynamic) {
            sb.append(", where X is the number of ").append(empowerNumber.getMessage());
        }
        sb.append(". <i>(Put ");

        if (dynamic) {
            sb.append("that many loyalty counters");
        } else if (empowerText.equals("1")) {
            sb.append("a loyalty counter");
        } else {
            sb.append(CardUtil.numberToText(Integer.parseInt(empowerText))).append(" loyalty counters");
        }
        sb.append(" on a Jace token you control. If you don't control one, first create");
        sb.append(" a blue Jace planeswalker token with");
        sb.append(" \"-1: Surveil 1\" and \"-3: Draw a card.\".)</i>");

        staticText = sb.toString();
    }
}
