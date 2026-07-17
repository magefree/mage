package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.YouGainedLifeCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.common.ControllerGainedLifeCount;
import mage.abilities.effects.common.AddContinuousEffectToGame;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetOpponentsCreaturePermanent;
import mage.watchers.common.PlayerGainedLifeWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PoisonersApprentice extends CardImpl {

    public PoisonersApprentice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.ORC);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Infusion -- When this creature enters, target creature an opponent controls gets -4/-4 until end of turn if you gained life this turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ConditionalOneShotEffect(
                new AddContinuousEffectToGame(new BoostTargetEffect(-4, -4)),
                YouGainedLifeCondition.getZero(), "target creature an opponent controls " +
                "gets -4/-4 until end of turn if you gained life this turn"
        ));
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability.setAbilityWord(AbilityWord.INFUSION)
                .addHint(ControllerGainedLifeCount.getHint()), new PlayerGainedLifeWatcher());
    }

    private PoisonersApprentice(final PoisonersApprentice card) {
        super(card);
    }

    @Override
    public PoisonersApprentice copy() {
        return new PoisonersApprentice(this);
    }
}
