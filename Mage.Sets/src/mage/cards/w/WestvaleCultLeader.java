
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.SetPowerToughnessSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.permanent.token.HumanClericToken;

/**
 *
 * @author fireshoes
 */
public final class WestvaleCultLeader extends CardImpl {

    final private static FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("creatures you control");

    public WestvaleCultLeader(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);
        this.color.setWhite(true);

        // this card is the second face of double-faced card
        this.nightCard = true;

        // Westvale Cult Leader's power and toughness are each equal to the number of creatures you control.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SetPowerToughnessSourceEffect(new PermanentsOnBattlefieldCount(filter), Duration.WhileOnBattlefield)));

        // At the beginning of your end step, create a 1/1 white and black Human Cleric creature token.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new CreateTokenEffect(new HumanClericToken()), TargetController.YOU, false));
    }

    public WestvaleCultLeader(final WestvaleCultLeader card) {
        super(card);
    }

    @Override
    public WestvaleCultLeader copy() {
        return new WestvaleCultLeader(this);
    }
}
