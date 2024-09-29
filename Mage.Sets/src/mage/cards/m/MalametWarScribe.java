package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MalametWarScribe extends CardImpl {

    public MalametWarScribe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");

        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // When Malamet War Scribe enters the battlefield, creatures you control get +2/+1 until end of turn.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new BoostControlledEffect(2, 1, Duration.EndOfTurn)
        ));
    }

    private MalametWarScribe(final MalametWarScribe card) {
        super(card);
    }

    @Override
    public MalametWarScribe copy() {
        return new MalametWarScribe(this);
    }
}
