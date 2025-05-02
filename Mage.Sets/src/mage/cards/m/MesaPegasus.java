

package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.BandingAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author L_J
 */
public final class MesaPegasus extends CardImpl {

    public MesaPegasus (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.PEGASUS);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Banding
        this.addAbility(BandingAbility.getInstance());
    }

    private MesaPegasus(final MesaPegasus card) {
        super(card);
    }

    @Override
    public MesaPegasus copy() {
        return new MesaPegasus(this);
    }

}
