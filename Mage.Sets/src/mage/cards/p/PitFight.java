package mage.cards.p;

import mage.abilities.effects.common.FightTargetsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class PitFight extends CardImpl {

    public PitFight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R/G}");

        // Target creature you control fights another target creature.
        this.getSpellAbility().addEffect(new FightTargetsEffect());
        TargetControlledCreaturePermanent target = new TargetControlledCreaturePermanent();
        target.setTargetTag(1);
        this.getSpellAbility().addTarget(target);

        TargetCreaturePermanent target2 = new TargetCreaturePermanent(StaticFilters.FILTER_ANOTHER_CREATURE_TARGET_2);
        target2.setTargetTag(2);
        this.getSpellAbility().addTarget(target2);
    }

    private PitFight(final PitFight card) {
        super(card);
    }

    @Override
    public PitFight copy() {
        return new PitFight(this);
    }
}
