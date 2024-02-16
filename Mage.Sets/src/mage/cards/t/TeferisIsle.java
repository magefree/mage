
package mage.cards.t;

import java.util.UUID;
import mage.Mana;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.keyword.PhasingAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public final class TeferisIsle extends CardImpl {

    public TeferisIsle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");
        this.supertype.add(SuperType.LEGENDARY);

        // Phasing
        this.addAbility(PhasingAbility.getInstance());
        // Teferi's Isle enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
        // {tap}: Add {U}{U}.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, Mana.BlueMana(2), new TapSourceCost()));

    }

    private TeferisIsle(final TeferisIsle card) {
        super(card);
    }

    @Override
    public TeferisIsle copy() {
        return new TeferisIsle(this);
    }
}
