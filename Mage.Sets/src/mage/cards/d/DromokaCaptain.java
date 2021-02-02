
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.keyword.BolsterEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class DromokaCaptain extends CardImpl {

    public DromokaCaptain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());
        
        // Whenever Dromoka Captain attacks, bolster 1.
        this.addAbility(new AttacksTriggeredAbility(new BolsterEffect(1), false));
    }

    private DromokaCaptain(final DromokaCaptain card) {
        super(card);
    }

    @Override
    public DromokaCaptain copy() {
        return new DromokaCaptain(this);
    }
}
