
package mage.cards.s;

import java.util.UUID;
import mage.abilities.condition.CompoundCondition;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.OpponentControlsPermanentCondition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.effects.common.PreventAllDamageToAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreatureInPlay;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 *
 * @author fireshoes
 */
public final class SivvisRuse extends CardImpl {
    
    private static final FilterPermanent filterMountain = new FilterPermanent();
    private static final FilterPermanent filterPlains = new FilterPermanent();

    static {
        filterMountain.add(new SubtypePredicate((SubType.MOUNTAIN)));
        filterPlains.add(new SubtypePredicate((SubType.PLAINS)));
    }

    public SivvisRuse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{W}{W}");

        // If an opponent controls a Mountain and you control a Plains, you may cast this spell without paying its mana cost.
        Condition condition = new CompoundCondition("If an opponent controls a Mountain and you control a Plains",
                new OpponentControlsPermanentCondition(filterMountain),
                new PermanentsOnTheBattlefieldCondition(filterPlains));
        this.addAbility(new AlternativeCostSourceAbility(null, condition));
        
        // Prevent all damage that would be dealt this turn to creatures you control.
        this.getSpellAbility().addEffect(new PreventAllDamageToAllEffect(Duration.EndOfTurn, new FilterControlledCreatureInPlay()));
    }

    public SivvisRuse(final SivvisRuse card) {
        super(card);
    }

    @Override
    public SivvisRuse copy() {
        return new SivvisRuse(this);
    }
}
