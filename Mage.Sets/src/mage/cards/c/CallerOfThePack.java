
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.MyriadAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class CallerOfThePack extends CardImpl {

    public CallerOfThePack(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{G}{G}");
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(8);
        this.toughness = new MageInt(6);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Myriad (Whenever this creature attacks, for each opponent other than the defending player, create a token that's a copy of this creature tapped and attacking that player or a planeswalker they control. Exile those tokens at the end of combat.)
        this.addAbility(new MyriadAbility());

    }

    private CallerOfThePack(final CallerOfThePack card) {
        super(card);
    }

    @Override
    public CallerOfThePack copy() {
        return new CallerOfThePack(this);
    }
}
