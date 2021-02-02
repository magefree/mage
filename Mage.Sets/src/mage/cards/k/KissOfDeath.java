
package mage.cards.k;

import java.util.UUID;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetOpponentOrPlaneswalker;

/**
 *
 * @author fireshoes
 */
public final class KissOfDeath extends CardImpl {

    public KissOfDeath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{B}{B}");

        // Kiss of Death deals 4 damage to target opponent. You gain 4 life.
        this.getSpellAbility().addEffect(new DamageTargetEffect(4));
        this.getSpellAbility().addEffect(new GainLifeEffect(4));
        this.getSpellAbility().addTarget(new TargetOpponentOrPlaneswalker());
    }

    private KissOfDeath(final KissOfDeath card) {
        super(card);
    }

    @Override
    public KissOfDeath copy() {
        return new KissOfDeath(this);
    }
}
