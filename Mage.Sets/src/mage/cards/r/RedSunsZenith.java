
package mage.cards.r;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.ShuffleSpellEffect;
import mage.abilities.effects.common.replacement.DealtDamageToCreatureBySourceDies;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetAnyTarget;
import mage.watchers.common.DamagedByWatcher;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class RedSunsZenith extends CardImpl {

    public RedSunsZenith(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{X}{R}");

        // Red Sun's Zenith deals X damage to any target.
        // If a creature dealt damage this way would die this turn, exile it instead.
        // Shuffle Red Sun's Zenith into its owner's library.
        this.getSpellAbility().addTarget(new TargetAnyTarget());
        this.getSpellAbility().addEffect(new DamageTargetEffect(ManacostVariableValue.REGULAR));
        this.getSpellAbility().addEffect(new DealtDamageToCreatureBySourceDies(this, Duration.EndOfTurn));
        this.getSpellAbility().addEffect(ShuffleSpellEffect.getInstance());
        this.getSpellAbility().addWatcher(new DamagedByWatcher(false));
    }

    private RedSunsZenith(final RedSunsZenith card) {
        super(card);
    }

    @Override
    public RedSunsZenith copy() {
        return new RedSunsZenith(this);
    }

}
