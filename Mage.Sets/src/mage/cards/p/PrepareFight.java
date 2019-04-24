
package mage.cards.p;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.*;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.AftermathAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.target.Target;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author stravant
 */
public final class PrepareFight extends SplitCard {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature you don't control");

    static {
        filter.add(new ControllerPredicate(TargetController.NOT_YOU));
    }

    public PrepareFight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, new CardType[]{CardType.SORCERY}, "{1}{W}", "{3}{G}", SpellAbilityType.SPLIT_AFTERMATH);

        // Prepare
        // Untap target creature. It gets +2/+2 and gains lifelink until end of turn.
        getLeftHalfCard().getSpellAbility().addTarget(new TargetCreaturePermanent());
        Effect effect = new UntapTargetEffect();
        effect.setText("Untap target creature.");
        getLeftHalfCard().getSpellAbility().addEffect(effect);
        effect = new BoostTargetEffect(2, 2, Duration.EndOfTurn);
        effect.setText("It gets +2/+2");
        getLeftHalfCard().getSpellAbility().addEffect(effect);
        effect = new GainAbilityTargetEffect(LifelinkAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("and gains lifelink until end of turn.");
        getLeftHalfCard().getSpellAbility().addEffect(effect);

        // to
        // Fight
        // Target creature you control fights target creature you don't control.
        getRightHalfCard().addAbility(new AftermathAbility().setRuleAtTheTop(true));
        getRightHalfCard().getSpellAbility().addEffect(new FightTargetsEffect());
        getRightHalfCard().getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        Target target = new TargetCreaturePermanent(filter);
        getRightHalfCard().getSpellAbility().addTarget(target);
    }

    public PrepareFight(final PrepareFight card) {
        super(card);
    }

    @Override
    public PrepareFight copy() {
        return new PrepareFight(this);
    }
}
