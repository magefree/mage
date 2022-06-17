
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
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
public final class ScourgeOfKherRidges extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature without flying");
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent("other creature with flying");

    static {
        filter.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
        filter2.add(new AbilityPredicate(FlyingAbility.class));
        filter2.add(AnotherPredicate.instance);
    }

    public ScourgeOfKherRidges(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{6}{R}{R}");
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // {1}{R}: Scourge of Kher Ridges deals 2 damage to each creature without flying.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageAllEffect(2, filter), new ManaCostsImpl<>("{1}{R}"));
        this.addAbility(ability);
        
        // {5}{R}: Scourge of Kher Ridges deals 6 damage to each other creature with flying.
        Ability ability2 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageAllEffect(6, filter2), new ManaCostsImpl<>("{5}{R}"));
        this.addAbility(ability2);
    }

    private ScourgeOfKherRidges(final ScourgeOfKherRidges card) {
        super(card);
    }

    @Override
    public ScourgeOfKherRidges copy() {
        return new ScourgeOfKherRidges(this);
    }
}
