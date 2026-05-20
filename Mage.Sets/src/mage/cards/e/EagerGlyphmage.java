package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.Inkling11Token;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EagerGlyphmage extends CardImpl {

    public EagerGlyphmage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When this creature enters, create a 1/1 white and black Inkling creature token with flying.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new Inkling11Token())));
    }

    private EagerGlyphmage(final EagerGlyphmage card) {
        super(card);
    }

    @Override
    public EagerGlyphmage copy() {
        return new EagerGlyphmage(this);
    }
}
