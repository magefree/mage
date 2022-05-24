
package mage.cards.l;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.CyclingAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 *
 * @author Plopman
 */
public final class LonelySandbar extends CardImpl {

    public LonelySandbar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // Lonely Sandbar enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
        // {tap}: Add {U}.
        this.addAbility(new BlueManaAbility());
        // Cycling {U}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{U}")));
    }

    private LonelySandbar(final LonelySandbar card) {
        super(card);
    }

    @Override
    public LonelySandbar copy() {
        return new LonelySandbar(this);
    }
}
