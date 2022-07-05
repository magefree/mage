

package mage.cards.p;


import java.util.UUID;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.CyclingAbility;
import mage.abilities.mana.BlackManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author Backfir3
 */
public final class PollutedMire extends CardImpl {

    public PollutedMire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        this.addAbility(new EntersBattlefieldTappedAbility());
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}")));
        this.addAbility(new BlackManaAbility());
    }

    private PollutedMire(final PollutedMire card) {
        super(card);
    }

    @Override
    public PollutedMire copy() {
        return new PollutedMire(this);
    }
}
