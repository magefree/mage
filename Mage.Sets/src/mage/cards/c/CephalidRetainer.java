
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author cbt33
 */
public final class CephalidRetainer extends CardImpl {
    
    static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature without flying");
    
    static {
        filter.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
    }

    public CephalidRetainer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}{U}");
        this.subtype.add(SubType.CEPHALID);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // {U}{U}: Tap target creature without flying.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new TapTargetEffect(), new ManaCostsImpl<>("{U}{U}"));
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }

    private CephalidRetainer(final CephalidRetainer card) {
        super(card);
    }

    @Override
    public CephalidRetainer copy() {
        return new CephalidRetainer(this);
    }
}
