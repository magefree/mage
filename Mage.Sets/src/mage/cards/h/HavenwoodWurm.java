
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class HavenwoodWurm extends CardImpl {

    public HavenwoodWurm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{6}{G}");
        this.subtype.add(SubType.WURM);

        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        this.addAbility(FlashAbility.getInstance());
        this.addAbility(TrampleAbility.getInstance());
    }

    private HavenwoodWurm(final HavenwoodWurm card) {
        super(card);
    }

    @Override
    public HavenwoodWurm copy() {
        return new HavenwoodWurm(this);
    }
}
