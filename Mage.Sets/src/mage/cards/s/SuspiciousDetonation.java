package mage.cards.s;

import mage.abilities.common.CantBeCounteredSourceAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.SacrificedArtifactThisTurnCondition;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.common.PermanentsSacrificedWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SuspiciousDetonation extends CardImpl {

    public SuspiciousDetonation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{R}");

        // This spell costs {3} less to cast if you've sacrificed an artifact this turn.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SpellCostReductionSourceEffect(3, SacrificedArtifactThisTurnCondition.instance)
        ).setRuleAtTheTop(true).addHint(SacrificedArtifactThisTurnCondition.getHint()), new PermanentsSacrificedWatcher());

        // This spell can't be countered.
        this.addAbility(new CantBeCounteredSourceAbility());

        // Suspicious Detonation deals 4 damage to target creature.
        this.getSpellAbility().addEffect(new DamageTargetEffect(4));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private SuspiciousDetonation(final SuspiciousDetonation card) {
        super(card);
    }

    @Override
    public SuspiciousDetonation copy() {
        return new SuspiciousDetonation(this);
    }
}
