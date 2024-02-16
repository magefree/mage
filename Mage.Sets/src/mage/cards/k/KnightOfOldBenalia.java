package mage.cards.k;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KnightOfOldBenalia extends CardImpl {

    public KnightOfOldBenalia(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Suspend 5—{W}
        this.addAbility(new SuspendAbility(5, new ManaCostsImpl<>("{W}"), this));

        // When Knight of Old Benalia enters the battlefield, other creatures you control get +1/+1 until end of turn.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new BoostControlledEffect(1, 1, Duration.EndOfTurn, true)
        ));
    }

    private KnightOfOldBenalia(final KnightOfOldBenalia card) {
        super(card);
    }

    @Override
    public KnightOfOldBenalia copy() {
        return new KnightOfOldBenalia(this);
    }
}
