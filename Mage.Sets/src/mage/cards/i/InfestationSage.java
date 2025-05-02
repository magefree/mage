package mage.cards.i;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.InsectBlackGreenFlyingToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InfestationSage extends CardImpl {

    public InfestationSage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When this creature dies, create a 1/1 black and green Insect creature token with flying.
        this.addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(new InsectBlackGreenFlyingToken())));
    }

    private InfestationSage(final InfestationSage card) {
        super(card);
    }

    @Override
    public InfestationSage copy() {
        return new InfestationSage(this);
    }
}
