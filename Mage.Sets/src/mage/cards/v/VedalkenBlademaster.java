
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.ProwessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class VedalkenBlademaster extends CardImpl {

    public VedalkenBlademaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.VEDALKEN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Prowess
        this.addAbility(new ProwessAbility());
    }

    private VedalkenBlademaster(final VedalkenBlademaster card) {
        super(card);
    }

    @Override
    public VedalkenBlademaster copy() {
        return new VedalkenBlademaster(this);
    }
}
