
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.ExaltedAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.common.TargetAttackingOrBlockingCreature;

/**
 *
 * @author Plopman
 */
public final class DawnrayArcher extends CardImpl {

    public DawnrayArcher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARCHER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Exalted
        this.addAbility(new ExaltedAbility());
        // {W}, {tap}: Dawnray Archer deals 1 damage to target attacking or blocking creature.
       Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(1), new ManaCostsImpl<>("{W}"));
       ability.addTarget(new TargetAttackingOrBlockingCreature());
       ability.addCost(new TapSourceCost());
       this.addAbility(ability);
    }

    private DawnrayArcher(final DawnrayArcher card) {
        super(card);
    }

    @Override
    public DawnrayArcher copy() {
        return new DawnrayArcher(this);
    }
}
