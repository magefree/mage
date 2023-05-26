
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.mana.BlueManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

/**
 *
 * @author LoneFox
 */
public final class PrincessLucrezia extends CardImpl {

    public PrincessLucrezia(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}{U}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // {tap}: Add {U}.
        this.addAbility(new BlueManaAbility());
    }

    private PrincessLucrezia(final PrincessLucrezia card) {
        super(card);
    }

    @Override
    public PrincessLucrezia copy() {
        return new PrincessLucrezia(this);
    }
}
