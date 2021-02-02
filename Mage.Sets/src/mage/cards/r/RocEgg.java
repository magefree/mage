
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.RocEggToken;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class RocEgg extends CardImpl {

    private static RocEggToken rocEggToken = new RocEggToken();

    public RocEgg(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");
        this.subtype.add(SubType.BIRD, SubType.EGG);

        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        this.addAbility(DefenderAbility.getInstance());
        this.addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(rocEggToken, 1), false));
    }

    private RocEgg(final RocEgg card) {
        super(card);
    }

    @Override
    public RocEgg copy() {
        return new RocEgg(this);
    }

}
