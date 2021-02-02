

package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class VedalkenOutlander extends CardImpl {

    public VedalkenOutlander(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{W}{U}");
        this.subtype.add(SubType.VEDALKEN);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(ProtectionAbility.from(ObjectColor.RED));
    }

    private VedalkenOutlander(final VedalkenOutlander card) {
        super(card);
    }

    @Override
    public VedalkenOutlander copy() {
        return new VedalkenOutlander(this);
    }
}
