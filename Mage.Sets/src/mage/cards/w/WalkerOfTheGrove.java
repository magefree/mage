
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.EvokeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.WalkerOfTheGroveToken;

/**
 *
 * @author LevelX2
 */
public final class WalkerOfTheGrove extends CardImpl {

    public WalkerOfTheGrove(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{G}{G}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // When Walker of the Grove leaves the battlefield, create a 4/4 green Elemental creature token.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new CreateTokenEffect(new WalkerOfTheGroveToken(), 1), false));
        // Evoke {4}{G}
        this.addAbility(new EvokeAbility("{4}{G}"));
    }

    private WalkerOfTheGrove(final WalkerOfTheGrove card) {
        super(card);
    }

    @Override
    public WalkerOfTheGrove copy() {
        return new WalkerOfTheGrove(this);
    }
}
