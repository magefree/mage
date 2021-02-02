
package mage.cards.s;

import java.util.UUID;
import mage.abilities.condition.common.AddendumCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DamageEverythingEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author fireshoes
 */
public final class SulfurousBlast extends CardImpl {

    public SulfurousBlast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{R}{R}");

        // Sulfurous Blast deals 2 damage to each creature and each player. If you cast this spell during your main phase, Sulfurous Blast deals 3 damage to each creature and each player instead.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DamageEverythingEffect(3),
                new DamageEverythingEffect(2),
                AddendumCondition.instance,
                "Sulfurous Blast deals 2 damage to each creature and each player. If you cast this spell during your main phase, Sulfurous Blast deals 3 damage to each creature and each player instead"));
    }

    private SulfurousBlast(final SulfurousBlast card) {
        super(card);
    }

    @Override
    public SulfurousBlast copy() {
        return new SulfurousBlast(this);
    }
}
