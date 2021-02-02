
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.BloodthirstAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class CarnageWurm extends CardImpl {

    public CarnageWurm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{6}{G}");
        this.subtype.add(SubType.WURM);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        this.addAbility(new BloodthirstAbility(3));
        this.addAbility(TrampleAbility.getInstance());
    }

    private CarnageWurm(final CarnageWurm card) {
        super(card);
    }

    @Override
    public CarnageWurm copy() {
        return new CarnageWurm(this);
    }
}
