package mage.cards.z;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author ciaccona007
 */
public final class ZimoneParadoxSculptor extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent("up to two artifacts and/or creatures you control");

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate()
        ));
    }

    public ZimoneParadoxSculptor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // At the beginning of combat on your turn, put a +1/+1 counter on each of up to two target creatures you control.
        Ability triggeredAbility = new BeginningOfCombatTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance())
        );
        triggeredAbility.addTarget(new TargetControlledCreaturePermanent(0, 2));
        this.addAbility(triggeredAbility);

        // {G}{U}, {T}: Double the number of each kind of counter on up to two target creatures and/or artifacts you control.
        Ability ability = new SimpleActivatedAbility(new ZimoneParadoxSculptorEffect(), new ManaCostsImpl<>("{G}{U}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPermanent(0, 2, filter));
        this.addAbility(ability);
    }

    private ZimoneParadoxSculptor(final ZimoneParadoxSculptor card) {
        super(card);
    }

    @Override
    public ZimoneParadoxSculptor copy() {
        return new ZimoneParadoxSculptor(this);
    }
}

class ZimoneParadoxSculptorEffect extends OneShotEffect {

    ZimoneParadoxSculptorEffect() {
        super(Outcome.Benefit);
        this.staticText = "double the number of each kind of counter "
                + "on up to two target creatures and/or artifacts you control";
    }

    private ZimoneParadoxSculptorEffect(final ZimoneParadoxSculptorEffect effect) {
        super(effect);
    }

    @Override
    public ZimoneParadoxSculptorEffect copy() {
        return new ZimoneParadoxSculptorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID targetId : getTargetPointer().getTargets(game, source)) {
            Permanent permanent = game.getPermanent(targetId);
            if (permanent == null) {
                continue;
            }
            for (Counter counter : permanent.getCounters(game).values()) {
                Counter newCounter = new Counter(counter.getName(), counter.getCount());
                permanent.addCounters(newCounter, source.getControllerId(), source, game);
            }
        }
        return true;
    }
}
