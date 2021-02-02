
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.BloodthirstAbility;
import mage.abilities.keyword.IslandwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class LurkingCrocodile extends CardImpl {

    public LurkingCrocodile(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.CROCODILE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(new BloodthirstAbility(1));
        this.addAbility(new IslandwalkAbility());
    }

    private LurkingCrocodile(final LurkingCrocodile card) {
        super(card);
    }

    @Override
    public LurkingCrocodile copy() {
        return new LurkingCrocodile(this);
    }
}
