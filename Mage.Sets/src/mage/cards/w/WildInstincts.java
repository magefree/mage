
package mage.cards.w;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.FightTargetsEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class WildInstincts extends CardImpl {

    public WildInstincts(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}");

        // Target creature you control gets +2/+2 until end of turn. It fights target creature an opponent controls.
        Effect boostTargetEffect = new BoostTargetEffect(2, 2, Duration.EndOfTurn);
        getSpellAbility().addEffect(boostTargetEffect);
        getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        Effect fightTargetsEffect = new FightTargetsEffect();
        fightTargetsEffect.setText("It fights target creature an opponent controls. " +
                "<i>(Each deals damage equal to its power to the other.)</i>");
        getSpellAbility().addEffect(fightTargetsEffect);

        getSpellAbility().addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE));
    }

    private WildInstincts(final WildInstincts card) {
        super(card);
    }

    @Override
    public WildInstincts copy() {
        return new WildInstincts(this);
    }
}
