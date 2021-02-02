
package mage.cards.n;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author LevelX2
 */
public final class NomadOutpost extends CardImpl {

    public NomadOutpost(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // Nomad Outpost enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
        // {T}: Add {R}, {W}, or {B}.
        this.addAbility(new RedManaAbility());
        this.addAbility(new WhiteManaAbility());
        this.addAbility(new BlackManaAbility());
    }

    private NomadOutpost(final NomadOutpost card) {
        super(card);
    }

    @Override
    public NomadOutpost copy() {
        return new NomadOutpost(this);
    }
}
