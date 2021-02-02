

package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksEachCombatStaticAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author Loki
 */
public final class CrazedGoblin extends CardImpl {

    public CrazedGoblin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.addAbility(new AttacksEachCombatStaticAbility());
    }

    private CrazedGoblin(final CrazedGoblin card) {
        super(card);
    }

    @Override
    public CrazedGoblin copy() {
        return new CrazedGoblin(this);
    }

}
