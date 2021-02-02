
package mage.cards.r;

import java.util.UUID;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.MultipliedValue;
import mage.abilities.dynamicvalue.common.PermanentsTargetOpponentControlsCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.target.common.TargetOpponent;

/**
 *
 * @author fireshoes
 */
public final class RenewingDawn extends CardImpl {
    
    private static final FilterPermanent filter = new FilterPermanent("Mountain");
    
    static {
        filter.add(SubType.MOUNTAIN.getPredicate());
    }

    public RenewingDawn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{W}");

        // You gain 2 life for each Mountain target opponent controls.
        DynamicValue amount = new MultipliedValue(new PermanentsTargetOpponentControlsCount(filter), 2);
        Effect effect = new GainLifeEffect(amount);
        effect.setText("You gain 2 life for each Mountain target opponent controls");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private RenewingDawn(final RenewingDawn card) {
        super(card);
    }

    @Override
    public RenewingDawn copy() {
        return new RenewingDawn(this);
    }
}
