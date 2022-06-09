

package mage.cards.r;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.CyclingAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author Backfir3
 */
public final class RemoteIsle extends CardImpl {

    public RemoteIsle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        this.addAbility(new EntersBattlefieldTappedAbility());
        this.addAbility(new BlueManaAbility());
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private RemoteIsle(final RemoteIsle card) {
        super(card);
    }

    @Override
    public RemoteIsle copy() {
        return new RemoteIsle(this);
    }
}
