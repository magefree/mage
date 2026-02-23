package mage.cards.o;

import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.common.PermanentsSacrificedThisTurnCount;
import mage.abilities.effects.common.AddContinuousEffectToGame;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.meta.OrTriggeredAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.token.ClueArtifactToken;
import mage.target.common.TargetAttackingCreature;
import mage.watchers.common.PermanentsSacrificedWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ObsessivePursuit extends CardImpl {

    public ObsessivePursuit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");

        // When this enchantment enters and at the beginning of your upkeep, you lose 1 life and create a Clue token.
        Ability ability = new OrTriggeredAbility(
                Zone.BATTLEFIELD, new LoseLifeSourceControllerEffect(1),
                new EntersBattlefieldTriggeredAbility(null), new BeginningOfUpkeepTriggeredAbility(null)
        ).setTriggerPhrase("When {this} enters and at the beginning of your upkeep, ");
        ability.addEffect(new CreateTokenEffect(new ClueArtifactToken()).concatBy("and"));
        this.addAbility(ability);

        // Whenever you attack, put X +1/+1 counters on target attacking creature, where X is the number of permanents you've sacrificed this turn. If X is three or greater, that creature gains lifelink until end of turn.
        ability = new AttacksWithCreaturesTriggeredAbility(new AddCountersTargetEffect(
                CounterType.P1P1.createInstance(), PermanentsSacrificedThisTurnCount.YOU
        ), 1).addHint(PermanentsSacrificedThisTurnCount.YOU.getHint());
        ability.addEffect(new ConditionalOneShotEffect(
                new AddContinuousEffectToGame(new GainAbilityTargetEffect(LifelinkAbility.getInstance())),
                ObsessivePursuitCondition.instance, "If X is three or greater, that creature gains lifelink until end of turn"
        ));
        ability.addTarget(new TargetAttackingCreature());
        this.addAbility(ability, new PermanentsSacrificedWatcher());
    }

    private ObsessivePursuit(final ObsessivePursuit card) {
        super(card);
    }

    @Override
    public ObsessivePursuit copy() {
        return new ObsessivePursuit(this);
    }
}

enum ObsessivePursuitCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return PermanentsSacrificedThisTurnCount.YOU.calculate(game, source, null) >= 3;
    }
}
