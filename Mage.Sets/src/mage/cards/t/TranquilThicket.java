
package mage.cards.t;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.CyclingAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 *
 * @author Plopman
 */
public final class TranquilThicket extends CardImpl {

    public TranquilThicket(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // Tranquil Thicket enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
        // {tap}: Add {G}.
        this.addAbility(new GreenManaAbility());
        // Cycling {G}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{G}")));
    }

    private TranquilThicket(final TranquilThicket card) {
        super(card);
    }

    @Override
    public TranquilThicket copy() {
        return new TranquilThicket(this);
    }
}
