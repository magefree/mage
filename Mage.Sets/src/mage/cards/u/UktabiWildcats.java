
package mage.cards.u;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author Quercitron
 */
public final class UktabiWildcats extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Forests you control");
    private static final FilterControlledPermanent sacrificeFilter = new FilterControlledPermanent("a Forest");

    static {
        filter.add(SubType.FOREST.getPredicate());
        sacrificeFilter.add(SubType.FOREST.getPredicate());
    }
    
    public UktabiWildcats(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}");
        this.subtype.add(SubType.CAT);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Uktabi Wildcats's power and toughness are each equal to the number of Forests you control.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerToughnessSourceEffect(new PermanentsOnBattlefieldCount(filter))));
        
        // {G}, Sacrifice a Forest: Regenerate Uktabi Wildcats.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new  RegenerateSourceEffect(), new ManaCostsImpl<>("{G}"));
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(1, 1, sacrificeFilter, true)));
        this.addAbility(ability);
    }

    private UktabiWildcats(final UktabiWildcats card) {
        super(card);
    }

    @Override
    public UktabiWildcats copy() {
        return new UktabiWildcats(this);
    }
}
