package mage.cards.b;

import mage.abilities.effects.common.FightTargetsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author intimidatingant
 */
public final class BloodFeud extends CardImpl {

    public BloodFeud(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{R}{R}");

        // Target creature fights another target creature.
        this.getSpellAbility().addEffect(new FightTargetsEffect());
        TargetCreaturePermanent target = new TargetCreaturePermanent();
        target.setTargetTag(1);
        this.getSpellAbility().addTarget(target);

        TargetCreaturePermanent target2 = new TargetCreaturePermanent(StaticFilters.FILTER_ANOTHER_CREATURE_TARGET_2);
        target2.setTargetTag(2);
        this.getSpellAbility().addTarget(target2);
    }

    private BloodFeud(final BloodFeud card) {
        super(card);
    }

    @Override
    public BloodFeud copy() {
        return new BloodFeud(this);
    }
}
