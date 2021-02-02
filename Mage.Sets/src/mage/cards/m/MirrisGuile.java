
package mage.cards.m;

import java.util.UUID;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.LookLibraryControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;

/**
 *
 * @author emerald000
 */
public final class MirrisGuile extends CardImpl {

    public MirrisGuile(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{G}");


        // At the beginning of your upkeep, you may look at the top three cards of your library, then put them back in any order.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new LookLibraryControllerEffect(3), TargetController.YOU, true));
    }

    private MirrisGuile(final MirrisGuile card) {
        super(card);
    }

    @Override
    public MirrisGuile copy() {
        return new MirrisGuile(this);
    }
}
