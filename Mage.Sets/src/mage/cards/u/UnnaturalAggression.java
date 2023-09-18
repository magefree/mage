
package mage.cards.u;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ExileTargetIfDiesEffect;
import mage.abilities.effects.common.FightTargetsEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.SecondTargetPointer;

/**
 *
 * @author LevelX2
 */
public final class UnnaturalAggression extends CardImpl {

    public UnnaturalAggression(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{G}");

        // Devoid
        this.addAbility(new DevoidAbility(this.color));

        // Target creature you control fights target creature an opponent controls.
        this.getSpellAbility().addEffect(new FightTargetsEffect(false));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE));
        // If the creature an opponent controls would die this turn, exile it instead.
        Effect effect = new ExileTargetIfDiesEffect();
        effect.setText("If the creature an opponent controls would die this turn, exile it instead");
        effect.setTargetPointer(new SecondTargetPointer());
        this.getSpellAbility().addEffect(effect);
    }

    private UnnaturalAggression(final UnnaturalAggression card) {
        super(card);
    }

    @Override
    public UnnaturalAggression copy() {
        return new UnnaturalAggression(this);
    }
}
