
package mage.cards.u;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.AngelToken;

/**
 *
 * @author LevelX2
 */
public final class UrbisProtector extends CardImpl {

    public UrbisProtector(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{W}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Urbis Protector enters the battlefield, create a 4/4 white Angel creature token with flying.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new AngelToken())));
    }

    private UrbisProtector(final UrbisProtector card) {
        super(card);
    }

    @Override
    public UrbisProtector copy() {
        return new UrbisProtector(this);
    }
}
