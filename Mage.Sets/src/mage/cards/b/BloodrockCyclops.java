
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksEachCombatStaticAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class BloodrockCyclops extends CardImpl {

    public BloodrockCyclops(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.CYCLOPS);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        this.addAbility(new AttacksEachCombatStaticAbility());
    }

    private BloodrockCyclops(final BloodrockCyclops card) {
        super(card);
    }

    @Override
    public BloodrockCyclops copy() {
        return new BloodrockCyclops(this);
    }
}
