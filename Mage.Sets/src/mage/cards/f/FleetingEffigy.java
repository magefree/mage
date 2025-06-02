package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FleetingEffigy extends CardImpl {

    public FleetingEffigy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");

        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // At the beginning of your end step, return this creature to its owner's hand.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new ReturnToHandSourceEffect()));

        // {2}{R}: This creature gets +2/+0 until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                new BoostSourceEffect(2, 0, Duration.EndOfTurn), new ManaCostsImpl<>("{2}{R}")
        ));
    }

    private FleetingEffigy(final FleetingEffigy card) {
        super(card);
    }

    @Override
    public FleetingEffigy copy() {
        return new FleetingEffigy(this);
    }
}
