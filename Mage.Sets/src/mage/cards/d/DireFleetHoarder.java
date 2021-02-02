
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.TreasureToken;

/**
 *
 * @author TheElk801
 */
public final class DireFleetHoarder extends CardImpl {

    public DireFleetHoarder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // When Dire Fleet Hoarder dies, create a colorless Treasure artifact token with "{t}, Sacrifice this artifact: Add one mana of any color."
        this.addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(new TreasureToken())));
    }

    private DireFleetHoarder(final DireFleetHoarder card) {
        super(card);
    }

    @Override
    public DireFleetHoarder copy() {
        return new DireFleetHoarder(this);
    }
}
