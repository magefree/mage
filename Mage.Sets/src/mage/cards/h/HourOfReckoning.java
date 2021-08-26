
package mage.cards.h;

import java.util.UUID;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.keyword.ConvokeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;

/**
 *
 * @author Wehk
 */
public final class HourOfReckoning extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nontoken creatures");
    
    static {
        filter.add(TokenPredicate.FALSE);
    }
    
    public HourOfReckoning(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{W}{W}{W}");

        // Convoke
        this.addAbility(new ConvokeAbility());
        
        // Destroy all nontoken creatures.
        this.getSpellAbility().addEffect(new DestroyAllEffect(filter));
    }

    private HourOfReckoning(final HourOfReckoning card) {
        super(card);
    }

    @Override
    public HourOfReckoning copy() {
        return new HourOfReckoning(this);
    }
}
