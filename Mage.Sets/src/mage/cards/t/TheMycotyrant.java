package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.DescendedThisTurnCount;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.permanent.token.FungusCantBlockToken;
import mage.watchers.common.DescendedWatcher;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class TheMycotyrant extends CardImpl {

    private static final FilterControlledCreaturePermanent filterPower =
            new FilterControlledCreaturePermanent("creatures you control that are Fungi and/or Saprolings");

    static {
        filterPower.add(Predicates.or(
                SubType.FUNGUS.getPredicate(),
                SubType.SAPROLING.getPredicate()
        ));
    }

    private static final DynamicValue xValuePower = new PermanentsOnBattlefieldCount(filterPower);
    private static final Hint hintPower = new ValueHint("Fungi/Saproling", xValuePower);

    public TheMycotyrant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELDER);
        this.subtype.add(SubType.FUNGUS);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // The Mycotyrant's power and toughness are each equal to the number of creatures you control that are Fungi and/or Saprolings.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SetBasePowerToughnessSourceEffect(xValuePower)
        ).addHint(hintPower));

        // At the beginning of your end step, create X 1/1 black Fungus creature tokens with "This creature can't block," where X is the number of times you descended this turn.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new CreateTokenEffect(new FungusCantBlockToken(), DescendedThisTurnCount.instance),
                TargetController.YOU, false
        ).addHint(DescendedThisTurnCount.getHint()), new DescendedWatcher());
    }

    private TheMycotyrant(final TheMycotyrant card) {
        super(card);
    }

    @Override
    public TheMycotyrant copy() {
        return new TheMycotyrant(this);
    }
}
