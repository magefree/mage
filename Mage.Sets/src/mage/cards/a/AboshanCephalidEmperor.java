
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.TapAllEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author cbt33
 */
public final class AboshanCephalidEmperor extends CardImpl {
    
static final FilterControlledCreaturePermanent filter1 = new FilterControlledCreaturePermanent("untapped Cephalid you control");
static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent("creatures without flying");

static {
    filter1.add(new SubtypePredicate(SubType.CEPHALID));
    filter2.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
}

    public AboshanCephalidEmperor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{U}{U}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.CEPHALID);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Tap an untapped Cephalid you control: Tap target permanent.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new TapTargetEffect(), new TapTargetCost(new TargetControlledCreaturePermanent(1, 1, filter1, true)));
        ability.addTarget(new TargetPermanent());
        this.addAbility(ability);
        
        // {U}{U}{U}: Tap all creatures without flying.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new TapAllEffect(filter2), new ManaCostsImpl("{U}{U}{U}")));
    }

    public AboshanCephalidEmperor(final AboshanCephalidEmperor card) {
        super(card);
    }

    @Override
    public AboshanCephalidEmperor copy() {
        return new AboshanCephalidEmperor(this);
    }
}
