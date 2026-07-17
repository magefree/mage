package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.WaterbendCost;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.effects.keyword.AirbendTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterSpellOrPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetSpellOrPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AangSwiftSavior extends TransformingDoubleFacedCard {

    private static final FilterSpellOrPermanent filter = new FilterSpellOrPermanent("other target creature or spell");
    private static final FilterPermanent laFilter = new FilterControlledCreaturePermanent("tapped creature you control");

    static {
    }
    static {
        filter.getPermanentFilter().add(CardType.CREATURE.getPredicate());
        filter.getPermanentFilter().add(AnotherPredicate.instance);
        laFilter.add(TappedPredicate.TAPPED);
    }

    public AangSwiftSavior(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.AVATAR, SubType.ALLY}, "{1}{W}{U}",
                "Aang and La, Ocean's Fury",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.AVATAR, SubType.SPIRIT, SubType.ALLY}, "");

        this.getLeftHalfCard().setPT(2, 3);
        this.getRightHalfCard().setPT(5, 5);

        // Flash
        this.getLeftHalfCard().addAbility(FlashAbility.getInstance());

        // Flying
        this.getLeftHalfCard().addAbility(FlyingAbility.getInstance());

        // When Aang enters, airbend up to one other target creature or spell.
        Ability ability = new EntersBattlefieldTriggeredAbility(new AirbendTargetEffect());
        ability.addTarget(new TargetSpellOrPermanent(0, 1, filter, false));
        this.getLeftHalfCard().addAbility(ability);

        // Waterbend {8}: Transform Aang.
        this.getLeftHalfCard().addAbility(new SimpleActivatedAbility(new TransformSourceEffect(), new WaterbendCost(8)));

        // Aang and La, Ocean's Fury

        // Reach
        this.getRightHalfCard().addAbility(ReachAbility.getInstance());

        // Trample
        this.getRightHalfCard().addAbility(TrampleAbility.getInstance());

        // Whenever Aang and La attack, put a +1/+1 counter on each tapped creature you control.
        this.getRightHalfCard().addAbility(new AttacksTriggeredAbility(new AddCountersAllEffect(CounterType.P1P1.createInstance(), laFilter)).setTriggerPhrase("Whenever {this} attack, "));
    }

    private AangSwiftSavior(final AangSwiftSavior card) {
        super(card);
    }

    @Override
    public AangSwiftSavior copy() {
        return new AangSwiftSavior(this);
    }
}
