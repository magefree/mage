
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.TargetSpell;

/**
 *
 * @author fireshoes
 */
public final class DisruptivePitmage extends CardImpl {

    public DisruptivePitmage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}: Counter target spell unless its controller pays {1}.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CounterUnlessPaysEffect(new GenericManaCost(1)), new TapSourceCost());
        ability.addTarget(new TargetSpell());
        this.addAbility(ability);
        
        // Morph {U}
        this.addAbility(new MorphAbility(new ManaCostsImpl("{U}")));
    }

    private DisruptivePitmage(final DisruptivePitmage card) {
        super(card);
    }

    @Override
    public DisruptivePitmage copy() {
        return new DisruptivePitmage(this);
    }
}