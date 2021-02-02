
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
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class WildInstincts extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature an opponent controls");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public WildInstincts(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}");

        // Target creature you control gets +2/+2 until end of turn. It fights target creature an opponent controls.
        Effect effect = new BoostTargetEffect(2, 2, Duration.EndOfTurn);
        getSpellAbility().addEffect(effect);
        getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        effect = new FightTargetsEffect();
        effect.setText("It fights target creature an opponent controls <i>(Each deals damage equal to its power to each other)</i>");
        getSpellAbility().addEffect(effect);
        getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
    }

    private WildInstincts(final WildInstincts card) {
        super(card);
    }

    @Override
    public WildInstincts copy() {
        return new WildInstincts(this);
    }
}
