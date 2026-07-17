package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.CrewAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.RedMutantToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TurtleBlimp extends CardImpl {

    public TurtleBlimp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When this Vehicle enters, create a 2/2 red Mutant creature token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new RedMutantToken())));

        // Crew 2
        this.addAbility(new CrewAbility(2));
    }

    private TurtleBlimp(final TurtleBlimp card) {
        super(card);
    }

    @Override
    public TurtleBlimp copy() {
        return new TurtleBlimp(this);
    }
}
