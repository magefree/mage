package mage.cards.b;

import mage.abilities.effects.common.FightTargetsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

import static mage.filter.StaticFilters.FILTER_ANOTHER_CREATURE_TARGET_2;

/**
 * @author intimidatingant
 */
public final class BloodFeud extends CardImpl {

    public BloodFeud(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{R}{R}");

        // Target creature fights another target creature.
        this.getSpellAbility().addEffect(new FightTargetsEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent().setTargetTag(1));
        this.getSpellAbility().addTarget(new TargetPermanent(FILTER_ANOTHER_CREATURE_TARGET_2).setTargetTag(2));
    }

    private BloodFeud(final BloodFeud card) {
        super(card);
    }

    @Override
    public BloodFeud copy() {
        return new BloodFeud(this);
    }
}
