
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Zone;
import mage.filter.common.FilterBlockingCreature;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class WarTorchGoblin extends CardImpl {

    public WarTorchGoblin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {R}, Sacrifice War-Torch Goblin: War-Torch Goblin deals 2 damage to target blocking creature.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(2, "it"), new ColoredManaCost(ColoredManaSymbol.R));
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetCreaturePermanent(new FilterBlockingCreature()));
        this.addAbility(ability);
    }

    private WarTorchGoblin(final WarTorchGoblin card) {
        super(card);
    }

    @Override
    public WarTorchGoblin copy() {
        return new WarTorchGoblin(this);
    }
}
