package mage.cards.t;

import mage.abilities.condition.common.VoidCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.AddContinuousEffectToGame;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.common.VoidWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TragicTrajectory extends CardImpl {

    public TragicTrajectory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}");

        // Target creature gets -2/-2 until end of turn.
        // Void -- That creature gets -10/-10 until end of turn instead if a nonland permanent left the battlefield this turn or a spell was warped this turn.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new AddContinuousEffectToGame(new BoostTargetEffect(-10, -10)),
                new AddContinuousEffectToGame(new BoostTargetEffect(-2, -2)),
                VoidCondition.instance, "Target creature gets -2/-2 until end of turn. <br>" +
                AbilityWord.VOID.formatWord() + "That creature gets -10/-10 until end of turn instead " +
                "if a nonland permanent left the battlefield this turn or a spell was warped this turn"
        ));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addHint(VoidCondition.getHint());
        this.getSpellAbility().addWatcher(new VoidWatcher());
    }

    private TragicTrajectory(final TragicTrajectory card) {
        super(card);
    }

    @Override
    public TragicTrajectory copy() {
        return new TragicTrajectory(this);
    }
}
