
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public final class ElderOfLaurels extends CardImpl {

    public ElderOfLaurels(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ADVISOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // {3}{G}: Target creature gets +X/+X until end of turn, where X is the number of creatures you control.
        PermanentsOnBattlefieldCount amount = new PermanentsOnBattlefieldCount(new FilterControlledCreaturePermanent());
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new BoostTargetEffect(amount, amount, Duration.EndOfTurn, true),
                new ManaCostsImpl("{3}{G}"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    public ElderOfLaurels(final ElderOfLaurels card) {
        super(card);
    }

    @Override
    public ElderOfLaurels copy() {
        return new ElderOfLaurels(this);
    }
}
