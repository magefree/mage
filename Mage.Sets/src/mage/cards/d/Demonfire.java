
package mage.cards.d;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.HellbentCondition;
import mage.abilities.decorator.ConditionalContinuousRuleModifyingEffect;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CantBeCounteredSourceEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.replacement.DealtDamageToCreatureBySourceDies;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.target.common.TargetAnyTarget;
import mage.watchers.common.DamagedByWatcher;

/**
 *
 * @author JotaPeRL
 */
public final class Demonfire extends CardImpl {

    public Demonfire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{X}{R}");

        // Demonfire deals X damage to any target.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DamageTargetEffect(new ManacostVariableValue()),
                new InvertCondition(HellbentCondition.instance),
                "{this} deals X damage to any target"));

        // If a creature dealt damage this way would die this turn, exile it instead.
        this.getSpellAbility().addEffect(new DealtDamageToCreatureBySourceDies(this, Duration.EndOfTurn));
        this.getSpellAbility().addWatcher(new DamagedByWatcher());

        // Hellbent - If you have no cards in hand, Demonfire can't be countered and the damage can't be prevented.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DamageTargetEffect(new ManacostVariableValue(), false),
                HellbentCondition.instance,
                "<br/><i>Hellbent</i> &mdash; If you have no cards in hand, this spell can't be countered and the damage can't be prevented."));
        // can't be countered
        Effect effect = new CantBeCounteredSourceEffect();
        effect.setText("");
        this.addAbility(new SimpleStaticAbility(Zone.STACK, new ConditionalContinuousRuleModifyingEffect(
                (CantBeCounteredSourceEffect) effect,
                HellbentCondition.instance)));

        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    public Demonfire(final Demonfire card) {
        super(card);
    }

    @Override
    public Demonfire copy() {
        return new Demonfire(this);
    }
}
