
package mage.cards.d;

import java.util.UUID;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author TheElk801
 */
public final class DarkNourishment extends CardImpl {

    public DarkNourishment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{B}");

        // Dark Nourishment deals 3 damage to any target. You gain 3 life.
        this.getSpellAbility().addEffect(new DamageTargetEffect(3));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
        this.getSpellAbility().addEffect(new GainLifeEffect(3));
    }

    private DarkNourishment(final DarkNourishment card) {
        super(card);
    }

    @Override
    public DarkNourishment copy() {
        return new DarkNourishment(this);
    }
}
