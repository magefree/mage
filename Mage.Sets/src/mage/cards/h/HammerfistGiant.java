
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DamageEverythingEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;

/**
 *
 * @author fireshoes
 */
public final class HammerfistGiant extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature without flying");
    
    static {
        filter.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
    }

    public HammerfistGiant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}{R}");
        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // {tap}: Hammerfist Giant deals 4 damage to each creature without flying and each player.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageEverythingEffect(4, filter), new TapSourceCost()));
    }

    private HammerfistGiant(final HammerfistGiant card) {
        super(card);
    }

    @Override
    public HammerfistGiant copy() {
        return new HammerfistGiant(this);
    }
}
