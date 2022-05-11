
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.DashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.MarduStrikeLeaderWarriorToken;

/**
 *
 * @author LevelX2
 */
public final class MarduStrikeLeader extends CardImpl {

    public MarduStrikeLeader(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever Mardu Strike Leader attacks, create a 2/1 black Warrior creature token.
        this.addAbility(new AttacksTriggeredAbility(new CreateTokenEffect(new MarduStrikeLeaderWarriorToken()), false));

        // Dash {3}{B}
        this.addAbility(new DashAbility("{3}{B}"));
    }

    private MarduStrikeLeader(final MarduStrikeLeader card) {
        super(card);
    }

    @Override
    public MarduStrikeLeader copy() {
        return new MarduStrikeLeader(this);
    }
}
