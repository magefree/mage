
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.common.TargetAttackingOrBlockingCreature;

/**
 *
 * @author Backfir3
 */
public final class BallistaSquad extends CardImpl {

    public BallistaSquad(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add(SubType.HUMAN, SubType.REBEL);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {X}{W}, {T}: Ballista Squad deals X damage to target attacking or blocking creature.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(ManacostVariableValue.REGULAR), new ManaCostsImpl<>("{X}{W}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetAttackingOrBlockingCreature());
        this.addAbility(ability);
    }

    private BallistaSquad(final BallistaSquad card) {
        super(card);
    }

    @Override
    public BallistaSquad copy() {
        return new BallistaSquad(this);
    }
}
