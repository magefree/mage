
package mage.cards.s;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.GainLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

/**
 *
 * @author Plopman
 */
public final class StreamOfLife extends CardImpl {

    public StreamOfLife(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{X}{G}");


        // Target player gains X life.
        this.getSpellAbility().addEffect(new GainLifeTargetEffect(ManacostVariableValue.REGULAR));
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private StreamOfLife(final StreamOfLife card) {
        super(card);
    }

    @Override
    public StreamOfLife copy() {
        return new StreamOfLife(this);
    }
}
