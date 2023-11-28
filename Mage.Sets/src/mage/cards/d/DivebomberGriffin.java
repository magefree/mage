
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterAttackingOrBlockingCreature;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class DivebomberGriffin extends CardImpl {

    public DivebomberGriffin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}{W}");
        this.subtype.add(SubType.GRIFFIN);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        this.addAbility(FlyingAbility.getInstance());
        // {tap}, Sacrifice Divebomber Griffin: Divebomber Griffin deals 3 damage to target attacking or blocking creature.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(3, "it"), new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetCreaturePermanent(new FilterAttackingOrBlockingCreature()));
        this.addAbility(ability);
    }

    private DivebomberGriffin(final DivebomberGriffin card) {
        super(card);
    }

    @Override
    public DivebomberGriffin copy() {
        return new DivebomberGriffin(this);
    }
}
