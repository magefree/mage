

package mage.cards.a;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author Loki
 */
public final class ArcaneSanctum extends CardImpl {

    public ArcaneSanctum (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},null);
        this.addAbility(new EntersBattlefieldTappedAbility());
        this.addAbility(new WhiteManaAbility());
        this.addAbility(new BlueManaAbility());
        this.addAbility(new BlackManaAbility());
    }

    private ArcaneSanctum(final ArcaneSanctum card) {
        super(card);
    }

    @Override
    public ArcaneSanctum copy() {
        return new ArcaneSanctum(this);
    }
}
