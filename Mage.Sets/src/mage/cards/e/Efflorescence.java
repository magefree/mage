package mage.cards.e;

import java.util.UUID;

import mage.abilities.condition.common.YouGainedLifeCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.common.ControllerGainedLifeCount;
import mage.abilities.effects.common.AddContinuousEffectToGame;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.common.PlayerGainedLifeWatcher;

/**
 *
 * @author muz
 */
public final class Efflorescence extends CardImpl {

    public Efflorescence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{G}");

        // Put two +1/+1 counters on target creature.
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance(2)));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Infusion -- If you gained life this turn, that creature also gains trample and indestructible until end of turn.
        this.getSpellAbility().addEffect(
            new ConditionalOneShotEffect(
                new AddContinuousEffectToGame(
                        new GainAbilityTargetEffect(TrampleAbility.getInstance()),
                        new GainAbilityTargetEffect(IndestructibleAbility.getInstance())
                ), YouGainedLifeCondition.getZero(),
                AbilityWord.INFUSION.formatWord() +
                    "If you gained life this turn, " +
                    "that creature also gains trample and indestructible until end of turn"
            )
        );
        this.getSpellAbility().addHint(ControllerGainedLifeCount.getHint());
        this.getSpellAbility().addWatcher(new PlayerGainedLifeWatcher());
    }

    private Efflorescence(final Efflorescence card) {
        super(card);
    }

    @Override
    public Efflorescence copy() {
        return new Efflorescence(this);
    }
}
