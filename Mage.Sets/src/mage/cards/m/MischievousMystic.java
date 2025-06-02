package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.DrawNthCardTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.FaerieToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MischievousMystic extends CardImpl {

    public MischievousMystic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you draw your second card each turn, create a 1/1 blue Faerie token with flying.
        this.addAbility(new DrawNthCardTriggeredAbility(new CreateTokenEffect(new FaerieToken())));
    }

    private MischievousMystic(final MischievousMystic card) {
        super(card);
    }

    @Override
    public MischievousMystic copy() {
        return new MischievousMystic(this);
    }
}
