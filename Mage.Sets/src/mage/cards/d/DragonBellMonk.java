
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.ProwessAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class DragonBellMonk extends CardImpl {

    public DragonBellMonk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MONK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());
        // Prowess
        this.addAbility(new ProwessAbility());
    }

    private DragonBellMonk(final DragonBellMonk card) {
        super(card);
    }

    @Override
    public DragonBellMonk copy() {
        return new DragonBellMonk(this);
    }
}
