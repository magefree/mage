
package mage.cards.n;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author cbt33
 */
public final class NeedForSpeed extends CardImpl {
   


    public NeedForSpeed(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{R}");


        // Sacrifice a land: Target creature gains haste until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, 
                                                   new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn), 
                                                   new SacrificeTargetCost(new TargetControlledPermanent(new FilterControlledPermanent("land"))));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private NeedForSpeed(final NeedForSpeed card) {
        super(card);
    }

    @Override
    public NeedForSpeed copy() {
        return new NeedForSpeed(this);
    }
}
