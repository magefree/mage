package mage.cards.v;

import mage.MageInt;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ViashinoSandsprinter extends CardImpl {

    public ViashinoSandsprinter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{R}");

        this.subtype.add(SubType.VIASHINO);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(1);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // At the beginning of the end step, return Viashino Sandsprinter to its owner's hand.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new ReturnToHandSourceEffect(true), TargetController.NEXT, false
        ));

        // Cycling {R}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{R}")));

    }

    private ViashinoSandsprinter(final ViashinoSandsprinter card) {
        super(card);
    }

    @Override
    public ViashinoSandsprinter copy() {
        return new ViashinoSandsprinter(this);
    }
}
