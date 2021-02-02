

package mage.cards.c;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class CrumblingNecropolis extends CardImpl {

    public CrumblingNecropolis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},null);
        this.addAbility(new EntersBattlefieldTappedAbility());
        this.addAbility(new RedManaAbility());
        this.addAbility(new BlueManaAbility());
        this.addAbility(new BlackManaAbility());
    }

    private CrumblingNecropolis(final CrumblingNecropolis card) {
        super(card);
    }

    @Override
    public CrumblingNecropolis copy() {
        return new CrumblingNecropolis(this);
    }

}
