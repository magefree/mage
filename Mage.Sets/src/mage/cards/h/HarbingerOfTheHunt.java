
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.filter.predicate.mageobject.AnotherPredicate;

/**
 *
 * @author fireshoes
 */
public final class HarbingerOfTheHunt extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature without flying");
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent("other creature with flying");

    static {
        filter.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
        filter2.add(new AbilityPredicate(FlyingAbility.class));
        filter2.add(AnotherPredicate.instance);
    }

    public HarbingerOfTheHunt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}{G}");
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // {2}{R}: Harbinger of the Hunt deals 1 damage to each creature without flying.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageAllEffect(1, filter), new ManaCostsImpl<>("{2}{R}")));
        
        // {2}{G}: Harbinger of the Hunt deals 1 damage to each other creature with flying.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageAllEffect(1, filter2), new ManaCostsImpl<>("{2}{G}")));
    }

    private HarbingerOfTheHunt(final HarbingerOfTheHunt card) {
        super(card);
    }

    @Override
    public HarbingerOfTheHunt copy() {
        return new HarbingerOfTheHunt(this);
    }
}
