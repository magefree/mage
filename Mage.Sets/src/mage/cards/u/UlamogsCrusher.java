

package mage.cards.u;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksEachCombatStaticAbility;
import mage.abilities.keyword.AnnihilatorAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class UlamogsCrusher extends CardImpl {

    public UlamogsCrusher (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{8}");
        this.subtype.add(SubType.ELDRAZI);
        this.power = new MageInt(8);
        this.toughness = new MageInt(8);
        this.addAbility(new AnnihilatorAbility(2));
        this.addAbility(new AttacksEachCombatStaticAbility());
    }

    private UlamogsCrusher(final UlamogsCrusher card) {
        super(card);
    }

    @Override
    public UlamogsCrusher copy() {
        return new UlamogsCrusher(this);
    }

}
