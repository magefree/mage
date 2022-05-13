
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.PreventDamageToTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Styxo
 */
public final class WellgabberApothecary extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("tapped Merfolk or Kithkin creature");

    static {
        filter.add(TappedPredicate.TAPPED);
        filter.add(Predicates.or(SubType.MERFOLK.getPredicate(), SubType.KITHKIN.getPredicate()));
    }

    public WellgabberApothecary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{W}");
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // {1}{W} : Prevent all damage that would be dealt to target tapped Merfolk or Kithkin creatuer this turn
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PreventDamageToTargetEffect(Duration.EndOfTurn, Integer.MAX_VALUE), new ManaCostsImpl("{1}{W}"));
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);

    }

    private WellgabberApothecary(final WellgabberApothecary card) {
        super(card);
    }

    @Override
    public WellgabberApothecary copy() {
        return new WellgabberApothecary(this);
    }
}
