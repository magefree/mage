
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.BushidoAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

/**
 * @author Loki
 */
public final class KondaLordOfEiganjo extends CardImpl {

    public KondaLordOfEiganjo(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{W}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SAMURAI);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        this.addAbility(VigilanceAbility.getInstance());
        this.addAbility(IndestructibleAbility.getInstance());

        this.addAbility(new BushidoAbility(5));
    }

    private KondaLordOfEiganjo(final KondaLordOfEiganjo card) {
        super(card);
    }

    @Override
    public KondaLordOfEiganjo copy() {
        return new KondaLordOfEiganjo(this);
    }

}
