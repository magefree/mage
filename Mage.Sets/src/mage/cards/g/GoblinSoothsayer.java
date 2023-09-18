
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author BursegSardaukar
 */
public final class GoblinSoothsayer extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("red creatures");
    private static final FilterControlledPermanent filter2 = new FilterControlledPermanent(SubType.GOBLIN, "Goblin");
    
    static {
        filter.add(new ColorPredicate(ObjectColor.RED));
    }
    
    public GoblinSoothsayer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {R}, {T}, Sacrifice a Goblin: Red creatures get +1/+1 until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostAllEffect(1,1,Duration.EndOfTurn, filter, false), new ManaCostsImpl<>("{R}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(filter2)));
        this.addAbility(ability);
        
    }

    private GoblinSoothsayer(final GoblinSoothsayer card) {
        super(card);
    }

    @Override
    public GoblinSoothsayer copy() {
        return new GoblinSoothsayer(this);
    }
}
