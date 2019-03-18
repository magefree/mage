
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.SupertypePredicate;
import mage.target.TargetPermanent;

/**
 *
 * @author fireshoes
 */
public final class EmpressGalina extends CardImpl {
    
    private static final FilterPermanent filter = new FilterPermanent("legendary permanent");

    static {
        filter.add(new SupertypePredicate(SuperType.LEGENDARY));
    }

    public EmpressGalina(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}{U}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.MERFOLK);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {U}{U}, {tap}: Gain control of target legendary permanent.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainControlTargetEffect(Duration.Custom), new ManaCostsImpl("{U}{U}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    public EmpressGalina(final EmpressGalina card) {
        super(card);
    }

    @Override
    public EmpressGalina copy() {
        return new EmpressGalina(this);
    }
}
