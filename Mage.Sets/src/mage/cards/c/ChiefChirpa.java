
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.BecomesMonstrousSourceTriggeredAbility;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.MonstrosityAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.permanent.token.EwokToken;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author Styxo
 */
public final class ChiefChirpa extends CardImpl {

    private static final FilterCreaturePermanent diedFilter = new FilterCreaturePermanent("a green creature you control");
    private static final FilterControlledCreaturePermanent ewokFilter = new FilterControlledCreaturePermanent("another target Ewok creature you control");

    static {
        diedFilter.add(new ColorPredicate(ObjectColor.GREEN));
        diedFilter.add(new ControllerPredicate(TargetController.YOU));

        ewokFilter.add(new SubtypePredicate(SubType.EWOK));
        ewokFilter.add(new AnotherPredicate());
    }

    public ChiefChirpa(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}{G}{W}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.EWOK);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {2}{R}{G}{W}: Monstrosity 2.
        this.addAbility(new MonstrosityAbility("{2}{R}{G}{W}", 2));

        // Whenever a green creature you control dies, you may put a +1/+1 counter on another target Ewok creature you control.
        Ability ability = new DiesCreatureTriggeredAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance()), true, diedFilter);
        ability.addTarget(new TargetControlledCreaturePermanent(ewokFilter));
        this.addAbility(ability);

        // When Chief Chirpa become monstrous, create three 1/1 green Ewok creature tokens.
        this.addAbility(new BecomesMonstrousSourceTriggeredAbility(new CreateTokenEffect(new EwokToken(), 3)));

    }

    public ChiefChirpa(final ChiefChirpa card) {
        super(card);
    }

    @Override
    public ChiefChirpa copy() {
        return new ChiefChirpa(this);
    }
}
