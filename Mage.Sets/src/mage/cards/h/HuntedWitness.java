package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.SoldierLifelinkToken;

/**
 *
 * @author TheElk801
 */
public final class HuntedWitness extends CardImpl {

    public HuntedWitness(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");

        this.subtype.add(SubType.HUMAN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Hunted Witness dies, create a 1/1 white Soldier creature token with lifelink.
        this.addAbility(new DiesSourceTriggeredAbility(
                new CreateTokenEffect(new SoldierLifelinkToken())
        ));
    }

    private HuntedWitness(final HuntedWitness card) {
        super(card);
    }

    @Override
    public HuntedWitness copy() {
        return new HuntedWitness(this);
    }
}
