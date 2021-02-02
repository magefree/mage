
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.DelveAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class HootingMandrills extends CardImpl {

    public HootingMandrills(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{G}");
        this.subtype.add(SubType.APE);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Delve
        this.addAbility(new DelveAbility());
        // Trample
        this.addAbility(TrampleAbility.getInstance());
    }

    private HootingMandrills(final HootingMandrills card) {
        super(card);
    }

    @Override
    public HootingMandrills copy() {
        return new HootingMandrills(this);
    }
}
