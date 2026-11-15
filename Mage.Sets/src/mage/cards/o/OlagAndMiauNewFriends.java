package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.Ability;
import mage.target.TargetPermanent;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class OlagAndMiauNewFriends extends CardImpl {

    private static final FilterControlledPermanent anotherCat = new FilterControlledPermanent(SubType.CAT, "another Cat you control");
    private static final FilterControlledPermanent anotherZombie = new FilterControlledPermanent(SubType.ZOMBIE, "another Zombie you control");
    private static final FilterControlledPermanent targetCat = new FilterControlledPermanent(SubType.CAT);
    private static final FilterControlledPermanent targetZombie = new FilterControlledPermanent(SubType.ZOMBIE);
    private static final FilterControlledPermanent tappedCatsOrZombies = new FilterControlledPermanent("tapped Cats and/or Zombies you control");

    static {
        anotherCat.add(AnotherPredicate.instance);
        anotherZombie.add(AnotherPredicate.instance);
        tappedCatsOrZombies.add(Predicates.or(
            SubType.CAT.getPredicate(),
            SubType.ZOMBIE.getPredicate()
        ));
        tappedCatsOrZombies.add(TappedPredicate.TAPPED);
    }

    private static final DynamicValue tappedCatsOrZombiesCount = new PermanentsOnBattlefieldCount(tappedCatsOrZombies);
    private static final Hint hint = new ValueHint("Tapped Cats and/or Zombies you control", tappedCatsOrZombiesCount);

    public OlagAndMiauNewFriends(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{B}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.CAT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever another Zombie you control enters, put a +1/+1 counter on target Cat you control.
        Ability ability = new EntersBattlefieldAllTriggeredAbility(
            new AddCountersTargetEffect(CounterType.P1P1.createInstance()), anotherZombie
        );
        ability.addTarget(new TargetPermanent(targetCat));
        this.addAbility(ability);

        // Whenever another Cat you control enters, put a +1/+1 counter on target Zombie you control.
        Ability ability2 = new EntersBattlefieldAllTriggeredAbility(
            new AddCountersTargetEffect(CounterType.P1P1.createInstance()), anotherCat
        );
        ability2.addTarget(new TargetPermanent(targetZombie));
        this.addAbility(ability2);

        // At the beginning of your end step, each opponent loses life equal to the number of tapped Cats and/or Zombies you control.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
            new LoseLifeOpponentsEffect(tappedCatsOrZombiesCount)
                .setText("each opponent loses life equal to the number of tapped Cats and/or Zombies you control")
        ).addHint(hint));
    }

    private OlagAndMiauNewFriends(final OlagAndMiauNewFriends card) {
        super(card);
    }

    @Override
    public OlagAndMiauNewFriends copy() {
        return new OlagAndMiauNewFriends(this);
    }
}
