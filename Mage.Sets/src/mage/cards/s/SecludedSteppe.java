
package mage.cards.s;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.CyclingAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author Plopman
 */
public final class SecludedSteppe extends CardImpl {

    public SecludedSteppe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // Secluded Steppe enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
        // {tap}: Add {W}.
        this.addAbility(new WhiteManaAbility());
        // Cycling {W}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{W}")));
    }

    private SecludedSteppe(final SecludedSteppe card) {
        super(card);
    }

    @Override
    public SecludedSteppe copy() {
        return new SecludedSteppe(this);
    }
}
