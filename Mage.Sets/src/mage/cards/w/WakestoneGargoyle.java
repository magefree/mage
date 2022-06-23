
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.combat.CanAttackAsThoughItDidntHaveDefenderAllEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;

/**
 *
 * @author LevelX2
 */
public final class WakestoneGargoyle extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("Creatures you control with defender");
    
    static {
        filter.add(new AbilityPredicate(DefenderAbility.class));
    }
    
    public WakestoneGargoyle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add(SubType.GARGOYLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Defender
        this.addAbility(DefenderAbility.getInstance());
        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // {1}{W}: Creatures you control with defender can attack this turn as though they didn't have defender.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new CanAttackAsThoughItDidntHaveDefenderAllEffect(Duration.EndOfTurn, filter), new ManaCostsImpl<>("{1}{W}") ));
    }

    private WakestoneGargoyle(final WakestoneGargoyle card) {
        super(card);
    }

    @Override
    public WakestoneGargoyle copy() {
        return new WakestoneGargoyle(this);
    }
}
