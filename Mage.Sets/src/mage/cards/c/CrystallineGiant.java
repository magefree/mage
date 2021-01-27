package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.counters.Counters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.RandomUtil;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CrystallineGiant extends CardImpl {

    public CrystallineGiant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");

        this.subtype.add(SubType.GIANT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // At the beginning of combat on your turn, choose a kind of counter at random that Crystalline Giant doesn't have on it from among flying, first strike, deathtouch, hexproof, lifelink, menace, reach, trample, vigilance, or +1/+1. Put a counter of that kind on Crystalline Giant.
        this.addAbility(new BeginningOfCombatTriggeredAbility(
                new CrystallineGiantEffect(), TargetController.YOU, false
        ));
    }

    private CrystallineGiant(final CrystallineGiant card) {
        super(card);
    }

    @Override
    public CrystallineGiant copy() {
        return new CrystallineGiant(this);
    }
}

class CrystallineGiantEffect extends OneShotEffect {

    private static final EnumSet<CounterType> counterTypeSet = EnumSet.noneOf(CounterType.class);

    static {
        counterTypeSet.add(CounterType.FLYING);
        counterTypeSet.add(CounterType.FIRST_STRIKE);
        counterTypeSet.add(CounterType.DEATHTOUCH);
        counterTypeSet.add(CounterType.HEXPROOF);
        counterTypeSet.add(CounterType.LIFELINK);
        counterTypeSet.add(CounterType.MENACE);
        counterTypeSet.add(CounterType.REACH);
        counterTypeSet.add(CounterType.TRAMPLE);
        counterTypeSet.add(CounterType.VIGILANCE);
        counterTypeSet.add(CounterType.P1P1);
    }

    CrystallineGiantEffect() {
        super(Outcome.Benefit);
        staticText = "choose a kind of counter at random that {this} doesn't have on it from among " +
                "flying, first strike, deathtouch, hexproof, lifelink, menace, reach, trample, vigilance, and +1/+1. " +
                "Put a counter of that kind on {this}";
    }

    private CrystallineGiantEffect(final CrystallineGiantEffect effect) {
        super(effect);
    }

    @Override
    public CrystallineGiantEffect copy() {
        return new CrystallineGiantEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent == null) {
            return false;
        }
        Counters counters = permanent.getCounters(game);
        List<CounterType> counterTypes = new ArrayList();
        counterTypes.addAll(counterTypeSet);
        counterTypes.removeIf(counters::containsKey);
        if (counterTypes.isEmpty()) {
            return true;
        }
        return permanent.addCounters(counterTypes.get(
                RandomUtil.nextInt(counterTypes.size())
        ).createInstance(), source.getControllerId(), source, game);
    }
}