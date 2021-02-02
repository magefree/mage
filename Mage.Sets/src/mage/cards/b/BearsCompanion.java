
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.BearsCompanionBearToken;

/**
 *
 * @author LevelX2
 */
public final class BearsCompanion extends CardImpl {

    public BearsCompanion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{U}{R}");
        this.subtype.add(SubType.HUMAN, SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Bear's Companion enters the battlefield, create a 4/4 green Bear creature token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new BearsCompanionBearToken())));
    }

    private BearsCompanion(final BearsCompanion card) {
        super(card);
    }

    @Override
    public BearsCompanion copy() {
        return new BearsCompanion(this);
    }
}
