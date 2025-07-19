package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.DealtDamageAnyTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SonicTheHedgehog extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("creature you control with flash or haste");

    static {
        filter.add(Predicates.or(
                new AbilityPredicate(FlashAbility.class),
                new AbilityPredicate(HasteAbility.class)
        ));
    }

    public SonicTheHedgehog(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HEDGEHOG);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Gotta Go Fast -- Whenever Sonic the Hedgehog attacks, put a +1/+1 counter on each creature you control with flash or haste.
        this.addAbility(new AttacksTriggeredAbility(
                new AddCountersAllEffect(CounterType.P1P1.createInstance(), filter)
        ).withFlavorWord("Gotta Go Fast"));

        // Whenever a creature you control with flash or haste is dealt damage, create a tapped Treasure token.
        this.addAbility(new DealtDamageAnyTriggeredAbility(
                new CreateTokenEffect(new TreasureToken(), 1, true),
                filter, SetTargetPointer.NONE, false
        ));
    }

    private SonicTheHedgehog(final SonicTheHedgehog card) {
        super(card);
    }

    @Override
    public SonicTheHedgehog copy() {
        return new SonicTheHedgehog(this);
    }
}
