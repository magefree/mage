
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.TreasureToken;

/**
 *
 * @author L_J
 */
public final class BrazenFreebooter extends CardImpl {

    public BrazenFreebooter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Brazen Freebooter enters the battlefield, create a colorless Treasure artifact token with "T, sacrifice this artifact: Add one mana of any color."
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new TreasureToken()), false));
    }

    private BrazenFreebooter(final BrazenFreebooter card) {
        super(card);
    }

    @Override
    public BrazenFreebooter copy() {
        return new BrazenFreebooter(this);
    }
}
