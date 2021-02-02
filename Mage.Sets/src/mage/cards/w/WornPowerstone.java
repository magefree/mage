
package mage.cards.w;

import java.util.UUID;
import mage.Mana;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 *
 * @author Backfir3
 */
public final class WornPowerstone extends CardImpl {

    public WornPowerstone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");
        this.addAbility(new EntersBattlefieldTappedAbility());
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, Mana.ColorlessMana(2), new TapSourceCost()));
    }

    private WornPowerstone(final WornPowerstone card) {
        super(card);
    }

    @Override
    public WornPowerstone copy() {
        return new WornPowerstone(this);
    }
}
