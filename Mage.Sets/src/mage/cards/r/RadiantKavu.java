
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.PreventAllDamageByAllPermanentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author LoneFox
 */
public final class RadiantKavu extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("blue creatures and black creatures");
    static {
        filter.add(Predicates.or(new ColorPredicate(ObjectColor.BLUE), new ColorPredicate(ObjectColor.BLACK)));
    }

    public RadiantKavu(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}{G}{W}");
        this.subtype.add(SubType.KAVU);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {R}{G}{W}: Prevent all combat damage blue creatures and black creatures would deal this turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, (new PreventAllDamageByAllPermanentsEffect(filter,
           Duration.EndOfTurn, true)), new ManaCostsImpl<>("{R}{G}{W}")));
    }

    private RadiantKavu(final RadiantKavu card) {
        super(card);
    }

    @Override
    public RadiantKavu copy() {
        return new RadiantKavu(this);
    }
}
