package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealtDamageToSourceTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.common.FilterAnyTarget;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetPermanentOrPlayer;

import java.util.UUID;

/**
 * @author muz
 */
public final class RedHulk extends CardImpl {

    public RedHulk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GAMMA);
        this.subtype.add(SubType.BERSERKER);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(6);
        this.toughness = new MageInt(7);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Enrage -- Whenever Red Hulk is dealt damage, put a +1/+1 counter on him. When you do, he deals damage equal to the number of +1/+1 counters on him to any other target.
        this.addAbility(new DealtDamageToSourceTriggeredAbility(new RedHulkEffect(), false, true));
    }

    private RedHulk(final RedHulk card) {
        super(card);
    }

    @Override
    public RedHulk copy() {
        return new RedHulk(this);
    }
}

class RedHulkEffect extends OneShotEffect {

    private static final FilterAnyTarget filter = new FilterAnyTarget("any other target");
    private static final CountersSourceCount xValue = new CountersSourceCount(CounterType.P1P1);

    static {
        filter.getPermanentFilter().add(AnotherPredicate.instance);
    }

    RedHulkEffect() {
        super(Outcome.Benefit);
        this.staticText = "put a +1/+1 counter on him. When you do, he deals damage equal to the number of +1/+1 counters on him to any other target";
    }

    private RedHulkEffect(final RedHulkEffect effect) {
        super(effect);
    }

    @Override
    public RedHulkEffect copy() {
        return new RedHulkEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent == null || !permanent.addCounters(CounterType.P1P1.createInstance(), source.getControllerId(), source, game)) {
            return false;
        }
        ReflexiveTriggeredAbility reflexive = new ReflexiveTriggeredAbility(
            new DamageTargetEffect(xValue).setText(
                "he deals damage equal to the number of +1/+1 counters on him to any other target"
            ), false
        );
        reflexive.addTarget(new TargetPermanentOrPlayer(filter));
        game.fireReflexiveTriggeredAbility(reflexive, source);
        return true;
    }
}
