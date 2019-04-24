
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.SetPowerSourceEffect;
import mage.abilities.keyword.DashAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class KolaghanForerunners extends CardImpl {

    public KolaghanForerunners(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.BERSERKER);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Kolaghan Forerunners' power is equal to the number of creatures you control.
        Effect effect = new SetPowerSourceEffect(new PermanentsOnBattlefieldCount(new FilterControlledCreaturePermanent("creatures you control")), Duration.EndOfGame);
        this.addAbility(new SimpleStaticAbility(Zone.ALL, effect));

        // Dash {2}{R} <i.(You may cast this spell for its dash cost. If you do it gains haste and it's returned to its owner's hand at the beginning of the next end step.)</i>
        this.addAbility(new DashAbility(this, "{2}{R}"));
    }

    public KolaghanForerunners(final KolaghanForerunners card) {
        super(card);
    }

    @Override
    public KolaghanForerunners copy() {
        return new KolaghanForerunners(this);
    }
}
