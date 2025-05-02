package mage.cards.y;

import java.util.UUID;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.replacement.DealtDamageToCreatureBySourceDies;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.watchers.common.DamagedByWatcher;

/**
 *
 * @author LevelX
 */
public final class YamabushisStorm extends CardImpl {

    public YamabushisStorm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{R}");

        // Yamabushi's Storm deals 1 damage to each creature.
        this.getSpellAbility().addEffect(new DamageAllEffect(1, new FilterCreaturePermanent()));

        // If a creature dealt damage this way would die this turn, exile it instead.
        this.getSpellAbility().addEffect(new DealtDamageToCreatureBySourceDies(this, Duration.EndOfTurn));
        this.getSpellAbility().addWatcher(new DamagedByWatcher(false));
    }

    private YamabushisStorm(final YamabushisStorm card) {
        super(card);
    }

    @Override
    public YamabushisStorm copy() {
        return new YamabushisStorm(this);
    }

}
