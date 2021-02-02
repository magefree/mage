
package mage.cards.g;

import java.util.UUID;
import mage.Mana;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.ReturnToHandChosenControlledPermanentEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterControlledPermanent;

/**
 *
 * @author Loki
 */
public final class GolgariRotFarm extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledLandPermanent();

    public GolgariRotFarm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},null);

        // Golgari Rot Farm enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
        // When Golgari Rot Farm enters the battlefield, return a land you control to its owner's hand.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ReturnToHandChosenControlledPermanentEffect(filter)));
        // {tap}: Add {B}{G}.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, new Mana(0, 0, 1, 0, 1, 0, 0, 0), new TapSourceCost()));
    }

    private GolgariRotFarm(final GolgariRotFarm card) {
        super(card);
    }

    @Override
    public GolgariRotFarm copy() {
        return new GolgariRotFarm(this);
    }
}
