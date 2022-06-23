
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.OrCost;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.TargetSpell;

/**
 *
 * @author L_J
 */
public final class ThrullWizard extends CardImpl {
    private static final FilterSpell filter = new FilterSpell("black spell");
    static{
        filter.add(new ColorPredicate(ObjectColor.BLACK));
    }

    public ThrullWizard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.THRULL);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        
        // {1}{B}: Counter target black spell unless that spell's controller pays {B} or {3}.
        Cost cost = new OrCost("pay {B} or pay {3}", new ColoredManaCost(ColoredManaSymbol.B), new GenericManaCost(3));
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CounterUnlessPaysEffect(cost), new ManaCostsImpl<>("{1}{B}"));
        ability.addTarget(new TargetSpell(filter));
        this.addAbility(ability);
    }

    private ThrullWizard(final ThrullWizard card) {
        super(card);
    }

    @Override
    public ThrullWizard copy() {
        return new ThrullWizard(this);
    }
}
