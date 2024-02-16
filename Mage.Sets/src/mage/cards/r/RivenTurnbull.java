
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.mana.BlackManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

/**
 *
 * @author LoneFox
 */
public final class RivenTurnbull extends CardImpl {

    public RivenTurnbull(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{U}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(7);

        // {tap}: Add {B}.
        this.addAbility(new BlackManaAbility());
    }

    private RivenTurnbull(final RivenTurnbull card) {
        super(card);
    }

    @Override
    public RivenTurnbull copy() {
        return new RivenTurnbull(this);
    }
}
