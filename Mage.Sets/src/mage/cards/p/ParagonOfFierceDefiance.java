
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author Quercitron
 */
public final class ParagonOfFierceDefiance extends CardImpl {

    private static final FilterCreaturePermanent filterCreatures = new FilterCreaturePermanent("red creatures");
    private static final FilterControlledCreaturePermanent filterCreature = new FilterControlledCreaturePermanent("another target red creature you control");
    
    static {
        filterCreatures.add(new ColorPredicate(ObjectColor.RED));
        filterCreature.add(AnotherPredicate.instance);
        filterCreature.add(new ColorPredicate(ObjectColor.RED));
    }
    
    public ParagonOfFierceDefiance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Other red creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, filterCreatures, true)));
        
        // {R}, {T}: Another target red creature you control gains haste until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn), new ManaCostsImpl<>("{R}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetControlledCreaturePermanent(filterCreature));
        this.addAbility(ability);
    }

    private ParagonOfFierceDefiance(final ParagonOfFierceDefiance card) {
        super(card);
    }

    @Override
    public ParagonOfFierceDefiance copy() {
        return new ParagonOfFierceDefiance(this);
    }
}
