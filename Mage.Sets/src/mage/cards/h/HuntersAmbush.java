
package mage.cards.h;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.effects.common.PreventAllDamageByAllPermanentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author LevelX2
 */
public final class HuntersAmbush extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nongreen creatures");
    
    static {
        filter.add(Predicates.not(new ColorPredicate(ObjectColor.GREEN)));
    }
    
    public HuntersAmbush(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{G}");


        // Prevent all combat damage that would be dealt by nongreen creatures this turn.
        this.getSpellAbility().addEffect(new PreventAllDamageByAllPermanentsEffect(filter, Duration.EndOfTurn, true));
    }

    private HuntersAmbush(final HuntersAmbush card) {
        super(card);
    }

    @Override
    public HuntersAmbush copy() {
        return new HuntersAmbush(this);
    }
}
