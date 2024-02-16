
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.VariableCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.costs.mana.VariableManaCost;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterMana;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Quercitron
 */
public final class CrimsonHellkite extends CardImpl {

    private static final FilterMana filterRedMana = new FilterMana();

    static {
        filterRedMana.setRed(true);
    }
    
    public CrimsonHellkite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{6}{R}{R}{R}");
        this.subtype.add(SubType.DRAGON);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // {X}, {tap}: Crimson Hellkite deals X damage to target creature. Spend only red mana on X.
        Effect effect = new DamageTargetEffect(ManacostVariableValue.REGULAR);
        effect.setText("{this} deals X damage to target creature. Spend only red mana on X");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl<>("{X}"));
        ability.addCost(new TapSourceCost());
        VariableCost variableCost = ability.getManaCostsToPay().getVariableCosts().get(0);
        if (variableCost instanceof VariableManaCost) {
            ((VariableManaCost) variableCost).setFilter(filterRedMana);
        }
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private CrimsonHellkite(final CrimsonHellkite card) {
        super(card);
    }

    @Override
    public CrimsonHellkite copy() {
        return new CrimsonHellkite(this);
    }
}
