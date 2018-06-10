
package mage.cards.f;

import java.util.UUID;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.RaidCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.common.PlayerAttackedWatcher;

/**
 *
 * @author LevelX2
 */
public final class FirecannonBlast extends CardImpl {

    public FirecannonBlast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}{R}");

        // Firecannon Blast deals 3 damage to target creature.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DamageTargetEffect(3),
                new InvertCondition(RaidCondition.instance),
                "{this} deals 3 damage to target creature"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        // Raid - Firecannon Blast deals 6 damage to that creature instead if you attacked with a creature this turn.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DamageTargetEffect(6, false),
                RaidCondition.instance,
                "<br/><br/><i>Raid</i> &mdash; {this} deals 6 damage to that creature instead if you attacked with a creature this turn"));
        this.getSpellAbility().addWatcher(new PlayerAttackedWatcher());
    }

    public FirecannonBlast(final FirecannonBlast card) {
        super(card);
    }

    @Override
    public FirecannonBlast copy() {
        return new FirecannonBlast(this);
    }
}
