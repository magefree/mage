

package mage.cards.d;


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
 * @author Backfir3
 */
public final class DriftingMeadow extends CardImpl {

    public DriftingMeadow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        this.addAbility(new EntersBattlefieldTappedAbility());
        this.addAbility(new WhiteManaAbility());
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private DriftingMeadow(final DriftingMeadow card) {
        super(card);
    }

    @Override
    public DriftingMeadow copy() {
        return new DriftingMeadow(this);
    }
}
