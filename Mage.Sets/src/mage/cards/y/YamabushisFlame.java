package mage.cards.y;

import java.util.UUID;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.replacement.DealtDamageToCreatureBySourceDies;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetAnyTarget;
import mage.watchers.common.DamagedByWatcher;

/**
 *
 * @author LevelX
 */
public final class YamabushisFlame extends CardImpl {

    public YamabushisFlame(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{R}");

        // Yamabushi's Flame deals 3 damage to any target. 
        this.getSpellAbility().addEffect(new DamageTargetEffect(3));
        this.getSpellAbility().addTarget(new TargetAnyTarget());

        // If a creature dealt damage this way would die this turn, exile it instead.
        this.getSpellAbility().addEffect(new DealtDamageToCreatureBySourceDies(this, Duration.EndOfTurn));
        this.getSpellAbility().addWatcher(new DamagedByWatcher(false));
    }

    private YamabushisFlame(final YamabushisFlame card) {
        super(card);
    }

    @Override
    public YamabushisFlame copy() {
        return new YamabushisFlame(this);
    }
}
