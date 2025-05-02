package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.BlackGreenWormToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WrigglingGrub extends CardImpl {

    public WrigglingGrub(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.WORM);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Wriggling Grub dies, create two 1/1 black and green Worm creature tokens.
        this.addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(new BlackGreenWormToken(), 2)));
    }

    private WrigglingGrub(final WrigglingGrub card) {
        super(card);
    }

    @Override
    public WrigglingGrub copy() {
        return new WrigglingGrub(this);
    }
}
