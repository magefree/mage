

package mage.cards.h;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.LookLibraryControllerEffect;
import mage.abilities.mana.BlueManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class HalimarDepths extends CardImpl {

    public HalimarDepths(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},null);
                // Halimar Depths enters the battlefield tapped.
                this.addAbility(new EntersBattlefieldTappedAbility());
                // When Halimar Depths enters the battlefield, look at the top three cards of your library, then put them back in any order.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new LookLibraryControllerEffect(3)));
        this.addAbility(new BlueManaAbility());
    }

    private HalimarDepths(final HalimarDepths card) {
        super(card);
    }

    @Override
    public HalimarDepths copy() {
        return new HalimarDepths(this);
    }

}
