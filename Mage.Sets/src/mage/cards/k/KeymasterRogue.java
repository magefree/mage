
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandChosenControlledPermanentEffect;
import mage.abilities.keyword.CantBeBlockedSourceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledCreaturePermanent;

/**
 *
 * @author Plopman
 */
public final class KeymasterRogue extends CardImpl {

    public KeymasterRogue(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Keymaster Rogue can't be blocked.
        this.addAbility(new CantBeBlockedSourceAbility());
        // When Keymaster Rogue enters the battlefield, return a creature you control to its owner's hand.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ReturnToHandChosenControlledPermanentEffect(new FilterControlledCreaturePermanent())));
    }

    private KeymasterRogue(final KeymasterRogue card) {
        super(card);
    }

    @Override
    public KeymasterRogue copy() {
        return new KeymasterRogue(this);
    }
}
