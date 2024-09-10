package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BecomesTappedSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.SoldierLifelinkToken;

/**
 *
 * @author TheElk801
 */
public final class EmmaraSoulOfTheAccord extends CardImpl {

    public EmmaraSoulOfTheAccord(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Emmara, Soul of the Accord becomes tapped, create a 1/1 white Soldier creature token with lifelink.
        this.addAbility(new BecomesTappedSourceTriggeredAbility(
                new CreateTokenEffect(new SoldierLifelinkToken())
        ));
    }

    private EmmaraSoulOfTheAccord(final EmmaraSoulOfTheAccord card) {
        super(card);
    }

    @Override
    public EmmaraSoulOfTheAccord copy() {
        return new EmmaraSoulOfTheAccord(this);
    }
}
