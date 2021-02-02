
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.DamageMultiEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanentAmount;

/**
 *
 * @author LevelX2
 */
public final class SpreadingFlames extends CardImpl {

    public SpreadingFlames(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{6}{R}");

        // Spreading Flames deals 6 damage divided as you choose among any number of target creatures.
        this.getSpellAbility().addEffect(new DamageMultiEffect(6));
        this.getSpellAbility().addTarget(new TargetCreaturePermanentAmount(6));
    }

    private SpreadingFlames(final SpreadingFlames card) {
        super(card);
    }

    @Override
    public SpreadingFlames copy() {
        return new SpreadingFlames(this);
    }
}
