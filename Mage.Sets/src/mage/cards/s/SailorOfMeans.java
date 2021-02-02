
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
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
public final class SailorOfMeans extends CardImpl {

    public SailorOfMeans(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // When Sailor of Means enters the battlefield, create a colorless Treasure artifact token with  "{t}, Sacrifice this artifact: Add one mana of any color."
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new TreasureToken())));
    }

    private SailorOfMeans(final SailorOfMeans card) {
        super(card);
    }

    @Override
    public SailorOfMeans copy() {
        return new SailorOfMeans(this);
    }
}
