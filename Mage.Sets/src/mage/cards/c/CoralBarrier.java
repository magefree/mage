
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.SquidToken;

/**
 *
 * @author LevelX2
 */
public final class CoralBarrier extends CardImpl {

    public CoralBarrier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");
        this.subtype.add(SubType.WALL);

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Defender
        this.addAbility(DefenderAbility.getInstance());
        // When Coral Barrier enters the battlefield, create a 1/1 blue Squid creature token with islandwalk.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new SquidToken()), false));

    }

    private CoralBarrier(final CoralBarrier card) {
        super(card);
    }

    @Override
    public CoralBarrier copy() {
        return new CoralBarrier(this);
    }
}
