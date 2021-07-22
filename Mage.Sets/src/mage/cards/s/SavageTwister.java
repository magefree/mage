
package mage.cards.s;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.DamageAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class SavageTwister extends CardImpl {

    public SavageTwister(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{X}{R}{G}");


        // Savage Twister deals X damage to each creature.
        this.getSpellAbility().addEffect(new DamageAllEffect(ManacostVariableValue.REGULAR, new FilterCreaturePermanent()));
    }

    private SavageTwister(final SavageTwister card) {
        super(card);
    }

    @Override
    public SavageTwister copy() {
        return new SavageTwister(this);
    }
}
