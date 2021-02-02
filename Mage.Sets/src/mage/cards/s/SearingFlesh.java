
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetOpponentOrPlaneswalker;

/**
 *
 * @author Jgod
 */
public final class SearingFlesh extends CardImpl {

    public SearingFlesh(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{6}{R}");

        // Searing Flesh deals 7 damage to target opponent.
        this.getSpellAbility().addTarget(new TargetOpponentOrPlaneswalker());
        this.getSpellAbility().addEffect(new DamageTargetEffect(7));
    }

    private SearingFlesh(final SearingFlesh card) {
        super(card);
    }

    @Override
    public SearingFlesh copy() {
        return new SearingFlesh(this);
    }
}
