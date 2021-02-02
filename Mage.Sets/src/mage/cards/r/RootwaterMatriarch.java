
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.EnchantedTargetCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class RootwaterMatriarch extends CardImpl {

    public RootwaterMatriarch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}{U}");
        this.subtype.add(SubType.MERFOLK);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // {T}: Gain control of target creature for as long as that creature is enchanted
        ConditionalContinuousEffect effect = new ConditionalContinuousEffect(new GainControlTargetEffect(Duration.Custom), EnchantedTargetCondition.instance,
                "Gain control of target creature for as long as that creature is enchanted");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private RootwaterMatriarch(final RootwaterMatriarch card) {
        super(card);
    }

    @Override
    public RootwaterMatriarch copy() {
        return new RootwaterMatriarch(this);
    }
}
