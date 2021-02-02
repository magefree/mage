
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DrawCardAllEffect;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author TheElk801
 */
public final class SoaringShowOff extends CardImpl {

    public SoaringShowOff(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Soaring Show-Off enters the battlefield, each player draws a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardAllEffect(1)));
    }

    private SoaringShowOff(final SoaringShowOff card) {
        super(card);
    }

    @Override
    public SoaringShowOff copy() {
        return new SoaringShowOff(this);
    }
}
