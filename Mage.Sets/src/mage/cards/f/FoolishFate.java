package mage.cards.f;

import mage.abilities.condition.common.YouGainedLifeCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.common.ControllerGainedLifeCount;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.LoseLifeTargetControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.common.PlayerGainedLifeWatcher;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class FoolishFate extends CardImpl {

    public FoolishFate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}");

        // Destroy target creature.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Infusion -- If you gained life this turn, that creature's controller loses 3 life.
        this.getSpellAbility().addEffect(
                new ConditionalOneShotEffect(
                        new LoseLifeTargetControllerEffect(3)
                                .setText("that creature's controller loses 3 life"),
                        YouGainedLifeCondition.getZero()
                ).concatBy("<br>" + AbilityWord.INFUSION.formatWord()));
        this.getSpellAbility().addHint(ControllerGainedLifeCount.getHint());
        this.getSpellAbility().addWatcher(new PlayerGainedLifeWatcher());
    }

    private FoolishFate(final FoolishFate card) {
        super(card);
    }

    @Override
    public FoolishFate copy() {
        return new FoolishFate(this);
    }
}
