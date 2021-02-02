
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ShroudAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class SimicSkySwallower extends CardImpl {

    public SimicSkySwallower(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{G}{U}");
        this.subtype.add(SubType.LEVIATHAN);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(TrampleAbility.getInstance());
        this.addAbility(ShroudAbility.getInstance());
    }

    private SimicSkySwallower(final SimicSkySwallower card) {
        super(card);
    }

    @Override
    public SimicSkySwallower copy() {
        return new SimicSkySwallower(this);
    }
}
