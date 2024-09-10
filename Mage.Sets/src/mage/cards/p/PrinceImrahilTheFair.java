package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.DrawNthCardTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.permanent.token.HumanSoldierToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PrinceImrahilTheFair extends CardImpl {

    public PrinceImrahilTheFair(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever you draw your second card each turn, create a 1/1 white Human Soldier creature token.
        this.addAbility(new DrawNthCardTriggeredAbility(
                new CreateTokenEffect(new HumanSoldierToken()), false, 2)
        );
    }

    private PrinceImrahilTheFair(final PrinceImrahilTheFair card) {
        super(card);
    }

    @Override
    public PrinceImrahilTheFair copy() {
        return new PrinceImrahilTheFair(this);
    }
}
