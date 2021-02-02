
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.RepairAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.DroidToken;

/**
 *
 * @author Styxo
 */
public final class SecurityDroid extends CardImpl {

    public SecurityDroid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.DROID);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Security Droid enters the battlefield, create a 1/1 colorless Droid creature token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new DroidToken(), 1), false));
        
        // Repair 3
        this.addAbility(new RepairAbility(3));
    }

    private SecurityDroid(final SecurityDroid card) {
        super(card);
    }

    @Override
    public SecurityDroid copy() {
        return new SecurityDroid(this);
    }
}
