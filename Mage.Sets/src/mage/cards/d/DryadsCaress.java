
package mage.cards.d;

import java.util.UUID;
import mage.abilities.condition.common.ManaWasSpentCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.UntapAllControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author Wehk
 */
public final class DryadsCaress extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature on the battlefield");
    private static final String rule = "untap all creatures you control";
    
    public DryadsCaress(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{4}{G}{G}");

        // You gain 1 life for each creature on the battlefield. 
        this.getSpellAbility().addEffect(new GainLifeEffect(new PermanentsOnBattlefieldCount(filter)));

        //If {W} was spent to cast Dryad's Caress, untap all creatures you control.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new UntapAllControllerEffect(new FilterControlledCreaturePermanent(), rule), 
                ManaWasSpentCondition.WHITE, "If {W} was spent to cast this spell, untap all creatures you control"));
    }

    private DryadsCaress(final DryadsCaress card) {
        super(card);
    }

    @Override
    public DryadsCaress copy() {
        return new DryadsCaress(this);
    }
}
