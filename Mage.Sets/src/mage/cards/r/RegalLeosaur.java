package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.MutatesSourceTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.MutateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RegalLeosaur extends CardImpl {

    public RegalLeosaur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{W}");

        this.subtype.add(SubType.DINOSAUR);
        this.subtype.add(SubType.CAT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Mutate {1}{R/W}{R/W}
        this.addAbility(new MutateAbility(this, "{1}{R/W}{R/W}"));

        // Whenever this creature mutates, other creatures you control get +2/+1 until end of turn.
        this.addAbility(new MutatesSourceTriggeredAbility(
                new BoostControlledEffect(2, 1, Duration.EndOfTurn, true)
        ));
    }

    private RegalLeosaur(final RegalLeosaur card) {
        super(card);
    }

    @Override
    public RegalLeosaur copy() {
        return new RegalLeosaur(this);
    }
}
