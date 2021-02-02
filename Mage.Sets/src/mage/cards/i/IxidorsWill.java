
package mage.cards.i;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.TargetSpell;

/**
 *
 * @author Quercitron
 */
public final class IxidorsWill extends CardImpl {

    private static final FilterCreaturePermanent FILTER = new FilterCreaturePermanent(SubType.WIZARD, "Wizard");

    public IxidorsWill(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{U}");

        // Counter target spell unless its controller pays {2} for each Wizard on the battlefield.
        Effect effect = new CounterUnlessPaysEffect(new PermanentsOnBattlefieldCount(FILTER, 2));
        effect.setText("Counter target spell unless its controller pays {2} for each Wizard on the battlefield");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    private IxidorsWill(final IxidorsWill card) {
        super(card);
    }

    @Override
    public IxidorsWill copy() {
        return new IxidorsWill(this);
    }
}
