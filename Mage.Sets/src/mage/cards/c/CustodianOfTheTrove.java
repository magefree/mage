
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class CustodianOfTheTrove extends CardImpl {

    public CustodianOfTheTrove(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{3}");
        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // Defender
        this.addAbility(DefenderAbility.getInstance());
        
        // Custodian of the Trove enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
    }

    private CustodianOfTheTrove(final CustodianOfTheTrove card) {
        super(card);
    }

    @Override
    public CustodianOfTheTrove copy() {
        return new CustodianOfTheTrove(this);
    }
}
