
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.TreasureToken;

/**
 *
 * @author LevelX2
 */
public final class GleamingBarrier extends CardImpl {

    public GleamingBarrier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}");

        this.subtype.add(SubType.WALL);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // When Gleaming Barrier dies, create a colorless Treasure artifact token with "{t}, Sacrifice this artifact: Add one mana of any color."
        this.addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(new TreasureToken())));
    }

    private GleamingBarrier(final GleamingBarrier card) {
        super(card);
    }

    @Override
    public GleamingBarrier copy() {
        return new GleamingBarrier(this);
    }
}
