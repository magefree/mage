package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.DrawNthCardTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SteelgazeGriffin extends CardImpl {

    public SteelgazeGriffin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");

        this.subtype.add(SubType.GRIFFIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When you draw your second card each turn, Steelgaze Griffin gets +2/+0 until end of turn.
        this.addAbility(new DrawNthCardTriggeredAbility(new BoostSourceEffect(2, 0, Duration.EndOfTurn), false, 2));
    }

    private SteelgazeGriffin(final SteelgazeGriffin card) {
        super(card);
    }

    @Override
    public SteelgazeGriffin copy() {
        return new SteelgazeGriffin(this);
    }
}
