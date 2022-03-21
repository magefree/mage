
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.BlockingAttackerIdPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public final class FlowstoneSalamander extends CardImpl {

    public FlowstoneSalamander(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}{R}");
        this.subtype.add(SubType.SALAMANDER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // {R}: Flowstone Salamander deals 1 damage to target creature blocking it.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(1), new ManaCostsImpl("{R}"));
        FilterCreaturePermanent filter = new FilterCreaturePermanent("creature blocking it");
        filter.add(new BlockingAttackerIdPredicate(this.getId()));
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }

    private FlowstoneSalamander(final FlowstoneSalamander card) {
        super(card);
    }

    @Override
    public FlowstoneSalamander copy() {
        return new FlowstoneSalamander(this);
    }
}
