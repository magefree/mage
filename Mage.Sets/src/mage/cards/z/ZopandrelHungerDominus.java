package mage.cards.z;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledPermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 *
 * @author weirddan455
 */
public final class ZopandrelHungerDominus extends CardImpl {

    private static final FilterControlledCreaturePermanent filter
            = new FilterControlledCreaturePermanent("other creatures");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public ZopandrelHungerDominus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(6);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // At the beginning of each combat, double the power and toughness of each creature you control until end of turn.
        this.addAbility(new BeginningOfCombatTriggeredAbility(new ZopandrelHungerDominusEffect(), TargetController.ANY, false));

        // {G/P}{G/P}, Sacrifice two other creatures: Put an indestructible counter on Zopandrel, Hunger Dominus.
        Ability ability = new SimpleActivatedAbility(new AddCountersSourceEffect(CounterType.INDESTRUCTIBLE.createInstance()), new ManaCostsImpl<>("{G/P}{G/P}"));
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(2, filter)));
        this.addAbility(ability);
    }

    private ZopandrelHungerDominus(final ZopandrelHungerDominus card) {
        super(card);
    }

    @Override
    public ZopandrelHungerDominus copy() {
        return new ZopandrelHungerDominus(this);
    }
}

class ZopandrelHungerDominusEffect extends OneShotEffect {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent();

    public ZopandrelHungerDominusEffect() {
        super(Outcome.BoostCreature);
        staticText = "double the power and toughness of each creature you control until end of turn";
    }

    private ZopandrelHungerDominusEffect(final ZopandrelHungerDominusEffect effect) {
        super(effect);
    }

    @Override
    public ZopandrelHungerDominusEffect copy() {
        return new ZopandrelHungerDominusEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(filter, source.getControllerId(), game)) {
            ContinuousEffect effect = new BoostTargetEffect(permanent.getPower().getValue(), permanent.getToughness().getValue());
            effect.setTargetPointer(new FixedTarget(permanent, game));
            game.addEffect(effect, source);
        }
        return true;
    }
}
