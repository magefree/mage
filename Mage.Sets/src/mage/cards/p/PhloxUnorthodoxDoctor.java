package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.CounterAnyPredicate;

import java.util.UUID;

/**
 * @author muz
 */
public final class PhloxUnorthodoxDoctor extends CardImpl {

    private static final FilterControlledCreaturePermanent filter =
        new FilterControlledCreaturePermanent("a creature you control with a counter on it");

    static {
        filter.add(CounterAnyPredicate.instance);
    }

    public PhloxUnorthodoxDoctor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DENOBULAN);
        this.subtype.add(SubType.DOCTOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever a creature you control with a counter on it deals combat damage to a player, draw a card.
        this.addAbility(new DealsDamageToAPlayerAllTriggeredAbility(
            new DrawCardSourceControllerEffect(1), filter, false, SetTargetPointer.NONE, true
        ));
    }

    private PhloxUnorthodoxDoctor(final PhloxUnorthodoxDoctor card) {
        super(card);
    }

    @Override
    public PhloxUnorthodoxDoctor copy() {
        return new PhloxUnorthodoxDoctor(this);
    }
}
