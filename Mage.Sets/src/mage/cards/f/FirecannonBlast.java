
package mage.cards.f;

import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.RaidCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.common.PlayerAttackedWatcher;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class FirecannonBlast extends CardImpl {

    public FirecannonBlast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}{R}");

        // Firecannon Blast deals 3 damage to target creature.
        // Raid - Firecannon Blast deals 6 damage to that creature instead if you attacked with a creature this turn.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DamageTargetEffect(3),
                new DamageTargetEffect(6),
                new InvertCondition(RaidCondition.instance),
                "{this} deals 3 damage to target creature.<br><i>Raid</i> &mdash; {this} deals 6 damage instead if you attacked with a creature this turn"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
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
