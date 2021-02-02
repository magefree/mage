
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.EldraziHorrorToken;

/**
 *
 * @author LevelX2
 */
public final class EnlightenedManiac extends CardImpl {

    public EnlightenedManiac(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}");
        this.subtype.add(SubType.HUMAN);
        this.power = new MageInt(0);
        this.toughness = new MageInt(2);

        // When Enlightened Maniac enters the battlefield, create a 3/2 colorless Eldrazi Horror creature token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new EldraziHorrorToken()), false));
    }

    private EnlightenedManiac(final EnlightenedManiac card) {
        super(card);
    }

    @Override
    public EnlightenedManiac copy() {
        return new EnlightenedManiac(this);
    }
}
