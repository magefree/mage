
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetOpponentOrPlaneswalker;

/**
 *
 * @author LoneFox
 */
public final class StolenGrain extends CardImpl {

    public StolenGrain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{B}{B}");

        // Stolen Grain deals 5 damage to target opponent. You gain 5 life.
        this.getSpellAbility().addEffect(new DamageTargetEffect(5));
        this.getSpellAbility().addEffect(new GainLifeEffect(5));
        this.getSpellAbility().addTarget(new TargetOpponentOrPlaneswalker());
    }

    private StolenGrain(final StolenGrain card) {
        super(card);
    }

    @Override
    public StolenGrain copy() {
        return new StolenGrain(this);
    }
}
