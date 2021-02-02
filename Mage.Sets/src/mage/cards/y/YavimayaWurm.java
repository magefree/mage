

package mage.cards.y;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class YavimayaWurm extends CardImpl {

    public YavimayaWurm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}{G}");
        this.subtype.add(SubType.WURM);

        this.power = new MageInt(6);
        this.toughness = new MageInt(4);

        this.addAbility(TrampleAbility.getInstance());
    }

    private YavimayaWurm(final YavimayaWurm card) {
        super(card);
    }

    @Override
    public YavimayaWurm copy() {
        return new YavimayaWurm(this);
    }

}
