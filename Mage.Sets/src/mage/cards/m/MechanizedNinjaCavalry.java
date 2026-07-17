package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.Robot11Token;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MechanizedNinjaCavalry extends CardImpl {

    public MechanizedNinjaCavalry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{R/W}");

        this.subtype.add(SubType.ROBOT);
        this.subtype.add(SubType.NINJA);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When this creature enters, create a 1/1 colorless Robot artifact creature token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new Robot11Token())));
    }

    private MechanizedNinjaCavalry(final MechanizedNinjaCavalry card) {
        super(card);
    }

    @Override
    public MechanizedNinjaCavalry copy() {
        return new MechanizedNinjaCavalry(this);
    }
}
