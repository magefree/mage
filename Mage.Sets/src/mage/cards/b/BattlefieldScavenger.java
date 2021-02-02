
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.ExertCreatureControllerTriggeredAbility;
import mage.abilities.effects.common.RummageEffect;
import mage.abilities.keyword.ExertAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author anonymous
 */
public final class BattlefieldScavenger extends CardImpl {

    public BattlefieldScavenger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.JACKAL, SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // You may exert Battlefield Scavenger as it attacks.
        this.addAbility(new ExertAbility(null, false));

        // Whenever you exert a creature, you may discard a card. If you do, draw a card.
        this.addAbility(new ExertCreatureControllerTriggeredAbility(new RummageEffect()));
    }

    private BattlefieldScavenger(final BattlefieldScavenger card) {
        super(card);
    }

    @Override
    public BattlefieldScavenger copy() {
        return new BattlefieldScavenger(this);
    }
}
