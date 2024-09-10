

package mage.cards.j;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author Loki
 */
public final class JungleShrine extends CardImpl {

    public JungleShrine (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},null);
        this.addAbility(new EntersBattlefieldTappedAbility());
        this.addAbility(new RedManaAbility());
        this.addAbility(new GreenManaAbility());
        this.addAbility(new WhiteManaAbility());
    }

    private JungleShrine(final JungleShrine card) {
        super(card);
    }

    @Override
    public JungleShrine copy() {
        return new JungleShrine(this);
    }
}
