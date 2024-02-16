package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.SuspectSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.DetectiveToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PersonOfInterest extends CardImpl {

    public PersonOfInterest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Person of Interest enters the battlefield, suspect it. Create a 2/2 white and blue Detective creature token.
        Ability ability = new EntersBattlefieldTriggeredAbility(new SuspectSourceEffect());
        ability.addEffect(new CreateTokenEffect(new DetectiveToken()));
        this.addAbility(ability);
    }

    private PersonOfInterest(final PersonOfInterest card) {
        super(card);
    }

    @Override
    public PersonOfInterest copy() {
        return new PersonOfInterest(this);
    }
}
