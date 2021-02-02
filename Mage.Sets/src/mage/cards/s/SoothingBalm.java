
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.GainLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

/**
 *
 * @author Derpthemeus
 */
public final class SoothingBalm extends CardImpl {

    public SoothingBalm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{W}");

        // Target player gains 5 life.
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new GainLifeTargetEffect(5));
    }

    private SoothingBalm(final SoothingBalm card) {
        super(card);
    }

    @Override
    public SoothingBalm copy() {
        return new SoothingBalm(this);
    }
}
