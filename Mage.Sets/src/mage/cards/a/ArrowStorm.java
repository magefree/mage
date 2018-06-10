
package mage.cards.a;

import java.util.UUID;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.RaidCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;
import mage.watchers.common.PlayerAttackedWatcher;

/**
 *
 * @author LevelX2
 */
public final class ArrowStorm extends CardImpl {

    public ArrowStorm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}{R}");

        // Arrow Storm deals 4 damage to any target.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DamageTargetEffect(4),
                new InvertCondition(RaidCondition.instance),
                "{this} deals 4 damage to any target"));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
        // Raid - If you attacked with a creature this turn, instead Arrow Storm deals 5 damage to that creature or player and the damage can't be prevented.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DamageTargetEffect(5, false),
                RaidCondition.instance,
                "<br/><br/><i>Raid</i> &mdash; If you attacked with a creature this turn, instead {this} deals 5 damage to that permanent or player and the damage can't be prevented"));
        this.getSpellAbility().addWatcher(new PlayerAttackedWatcher());
    }

    public ArrowStorm(final ArrowStorm card) {
        super(card);
    }

    @Override
    public ArrowStorm copy() {
        return new ArrowStorm(this);
    }
}
