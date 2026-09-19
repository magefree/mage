package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.abilities.Ability;
import mage.abilities.condition.common.ScryOrSurveilCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.AddContinuousEffectToGame;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.watchers.common.ScryOrSurveilWatcher;

/**
 *
 * @author muz
 */
public final class DesperateFuturescribe extends CardImpl {

    public DesperateFuturescribe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{U}");
        
        this.subtype.add(SubType.KOR);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // At the beginning of combat on your turn, another target creature you control gets +1/+1 until end of turn. 
        // If you've scried or surveilled this turn, put a +1/+1 counter on that creature instead.
        Ability ability = new BeginningOfCombatTriggeredAbility(new ConditionalOneShotEffect(
            new AddCountersTargetEffect(CounterType.P1P1.createInstance()), 
            new AddContinuousEffectToGame(new BoostTargetEffect(1, 1, Duration.EndOfTurn)),
            ScryOrSurveilCondition.instance, 
            "another target creature you control gets +1/+1 until end of turn. "
            + "If you've scried or surveilled this turn, put a +1/+1 counter on that creature instead"
        ));
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE));
        ability.addWatcher(new ScryOrSurveilWatcher());
        this.addAbility(ability);
    }

    private DesperateFuturescribe(final DesperateFuturescribe card) {
        super(card);
    }

    @Override
    public DesperateFuturescribe copy() {
        return new DesperateFuturescribe(this);
    }
}
