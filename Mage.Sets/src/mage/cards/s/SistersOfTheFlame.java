
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox
 */
public final class SistersOfTheFlame extends CardImpl {

    public SistersOfTheFlame(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {tap}: Add {R}.
        this.addAbility(new RedManaAbility());
    }

    private SistersOfTheFlame(final SistersOfTheFlame card) {
        super(card);
    }

    @Override
    public SistersOfTheFlame copy() {
        return new SistersOfTheFlame(this);
    }
}
