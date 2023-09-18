
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.target.TargetSpell;

/**
 *
 * @author jeffwadsworth
 */
public final class ErtaiWizardAdept extends CardImpl {

    public ErtaiWizardAdept(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {2}{U}{U}, {tap}: Counter target spell.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CounterTargetEffect(), new ManaCostsImpl<>("{2}{U}{U}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetSpell());
        this.addAbility(ability);
    }

    private ErtaiWizardAdept(final ErtaiWizardAdept card) {
        super(card);
    }

    @Override
    public ErtaiWizardAdept copy() {
        return new ErtaiWizardAdept(this);
    }
}
