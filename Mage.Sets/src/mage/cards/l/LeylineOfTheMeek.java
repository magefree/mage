
package mage.cards.l;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.keyword.LeylineAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;

/**
 *
 * @author emerald000
 */
public final class LeylineOfTheMeek extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Creature tokens");
    static {
        filter.add(new TokenPredicate());
    }

    public LeylineOfTheMeek(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{W}{W}");


        // If Leyline of the Meek is in your opening hand, you may begin the game with it on the battlefield.
        this.addAbility(LeylineAbility.getInstance());
        
        // Creature tokens get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostAllEffect(1, 1, Duration.WhileOnBattlefield, filter, false)));
    }

    public LeylineOfTheMeek(final LeylineOfTheMeek card) {
        super(card);
    }

    @Override
    public LeylineOfTheMeek copy() {
        return new LeylineOfTheMeek(this);
    }
}
