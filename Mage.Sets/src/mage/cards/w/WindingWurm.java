

package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.EchoAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Backfir3
 */
public final class WindingWurm extends CardImpl {

    public WindingWurm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}");
        this.subtype.add(SubType.WURM);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

    this.addAbility(new EchoAbility("{4}{G}"));
    }

    private WindingWurm(final WindingWurm card) {
        super(card);
    }

    @Override
    public WindingWurm copy() {
        return new WindingWurm(this);
    }

}