
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.NamePredicate;

/**
 *
 * @author jeffwadsworth
 */
public final class CylianSunsinger extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Cylian Sunsinger and each other creature with the same name");
    
    static {
        filter.add(new NamePredicate("Cylian Sunsinger"));
    }

    public CylianSunsinger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {R}{G}{W}: Cylian Sunsinger and each other creature with the same name as it get +3/+3 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostAllEffect(3, 3, Duration.EndOfTurn, filter, false), new ManaCostsImpl("{R}{G}{W}")));
        
    }

    private CylianSunsinger(final CylianSunsinger card) {
        super(card);
    }

    @Override
    public CylianSunsinger copy() {
        return new CylianSunsinger(this);
    }
}
