
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author nantuko
 */
public final class DiregrafGhoul extends CardImpl {

    public DiregrafGhoul(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}");
        this.subtype.add(SubType.ZOMBIE);

        this.color.setBlack(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Diregraf Ghoul enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
    }

    private DiregrafGhoul(final DiregrafGhoul card) {
        super(card);
    }

    @Override
    public DiregrafGhoul copy() {
        return new DiregrafGhoul(this);
    }
}
