
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.HornetQueenInsectToken;

/**
 *
 * @author LevelX2
 */
public final class HornetQueen extends CardImpl {

    public HornetQueen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}{G}");
        this.subtype.add(SubType.INSECT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());
        // When Hornet Queen enters the battlefield, create four 1/1 green Insect creature tokens with flying and deathtouch.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new HornetQueenInsectToken(), 4), false));
    }

    public HornetQueen(final HornetQueen card) {
        super(card);
    }

    @Override
    public HornetQueen copy() {
        return new HornetQueen(this);
    }
}
