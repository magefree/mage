
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksOrBlocksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldOrDiesSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.RepairAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.DroidToken;

/**
 *
 * @author Styxo
 */
public final class TankDroid extends CardImpl {

    public TankDroid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}{W}{U}{B}");
        this.subtype.add(SubType.DROID);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Whenever Tank Droid enters the battlefield, attacks, blocks or dies, create a 1/1 colorless Droid token creature.
        Ability ability = new AttacksOrBlocksTriggeredAbility(new CreateTokenEffect(new DroidToken()), false);
        this.addAbility(ability);
        ability = new EntersBattlefieldOrDiesSourceTriggeredAbility(new CreateTokenEffect(new DroidToken()), false);
        this.addAbility(ability);

        // Repair 3
        this.addAbility(new RepairAbility(3));
    }

    private TankDroid(final TankDroid card) {
        super(card);
    }

    @Override
    public TankDroid copy() {
        return new TankDroid(this);
    }
}
