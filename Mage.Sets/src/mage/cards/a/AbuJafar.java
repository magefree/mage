
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.BlockedByIdPredicate;
import mage.filter.predicate.permanent.BlockingAttackerIdPredicate;

/**
 *
 * @author MarcoMarin
 */
public final class AbuJafar extends CardImpl {

    public AbuJafar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}");
        this.subtype.add(SubType.HUMAN);
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures blocking or blocked by it");
        filter.add(Predicates.or(new BlockedByIdPredicate(this.getId()),
                                 new BlockingAttackerIdPredicate(this.getId())));
        
        // When Abu Ja'far dies, destroy all creatures blocking or blocked by it. They can't be regenerated.        
        this.addAbility(new DiesSourceTriggeredAbility(new DestroyAllEffect(filter, true), false));
    }

    private AbuJafar(final AbuJafar card) {
        super(card);
    }

    @Override
    public AbuJafar copy() {
        return new AbuJafar(this);
    }
}
