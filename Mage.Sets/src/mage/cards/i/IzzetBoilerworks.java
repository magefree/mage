
package mage.cards.i;

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
 * @author Loki
 */
public final class IzzetBoilerworks extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledLandPermanent();

    public IzzetBoilerworks(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // Izzet Boilerworks enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // When Izzet Boilerworks enters the battlefield, return a land you control to its owner's hand.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ReturnToHandChosenControlledPermanentEffect(filter), false));
        // {T}: Add {U}{R}.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, new Mana(0, 1, 0, 1, 0, 0, 0, 0), new TapSourceCost()));
    }

    private IzzetBoilerworks(final IzzetBoilerworks card) {
        super(card);
    }

    @Override
    public IzzetBoilerworks copy() {
        return new IzzetBoilerworks(this);
    }
}
