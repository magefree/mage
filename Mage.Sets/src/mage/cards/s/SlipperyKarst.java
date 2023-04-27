

package mage.cards.s;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.CyclingAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author Backfir3
 */
public final class SlipperyKarst extends CardImpl {

    public SlipperyKarst(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        this.addAbility(new EntersBattlefieldTappedAbility());
        this.addAbility(new GreenManaAbility());
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private SlipperyKarst(final SlipperyKarst card) {
        super(card);
    }

    @Override
    public SlipperyKarst copy() {
        return new SlipperyKarst(this);
    }
}
