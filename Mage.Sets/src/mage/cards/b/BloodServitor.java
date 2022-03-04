package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.BloodToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BloodServitor extends CardImpl {

    public BloodServitor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");

        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Blood Servitor enters the battlefield, create a Blood token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new BloodToken())));
    }

    private BloodServitor(final BloodServitor card) {
        super(card);
    }

    @Override
    public BloodServitor copy() {
        return new BloodServitor(this);
    }
}
