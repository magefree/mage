

package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksEachCombatStaticAbility;
import mage.abilities.keyword.BattleCryAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author ayratn
 */
public final class KuldothaRingleader extends CardImpl {

    public KuldothaRingleader (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}");
        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.BERSERKER);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        this.addAbility(new BattleCryAbility());
        this.addAbility(new AttacksEachCombatStaticAbility());
    }

    private KuldothaRingleader(final KuldothaRingleader card) {
        super(card);
    }

    @Override
    public KuldothaRingleader copy() {
        return new KuldothaRingleader(this);
    }

}
