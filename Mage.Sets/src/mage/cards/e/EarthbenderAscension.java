package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LandfallAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.effects.keyword.EarthbendTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetControlledLandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EarthbenderAscension extends CardImpl {

    public EarthbenderAscension(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");

        // When this enchantment enters, earthbend 2. Then search your library for a basic land card, put it onto the battlefield tapped, then shuffle.
        Ability ability = new EntersBattlefieldTriggeredAbility(new EarthbendTargetEffect(2));
        ability.addEffect(new SearchLibraryPutInPlayEffect(
                new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND), true
        ).concatBy("Then"));
        ability.addTarget(new TargetControlledLandPermanent());
        this.addAbility(ability);

        // Landfall -- Whenever a land you control enters, put a quest counter on this enchantment. When you do, if it has four or more quest counters on it, put a +1/+1 counter on target creature you control. It gains trample until end of turn.
        this.addAbility(new LandfallAbility(new EarthbenderAscensionEffect()));
    }

    private EarthbenderAscension(final EarthbenderAscension card) {
        super(card);
    }

    @Override
    public EarthbenderAscension copy() {
        return new EarthbenderAscension(this);
    }
}

class EarthbenderAscensionEffect extends OneShotEffect {

    private static final Condition condition = new SourceHasCounterCondition(CounterType.QUEST, ComparisonType.MORE_THAN, 3);

    EarthbenderAscensionEffect() {
        super(Outcome.Benefit);
        staticText = "put a quest counter on {this}. When you do, if it has four or more quest counters on it, " +
                "put a +1/+1 counter on target creature you control. It gains trample until end of turn";
    }

    private EarthbenderAscensionEffect(final EarthbenderAscensionEffect effect) {
        super(effect);
    }

    @Override
    public EarthbenderAscensionEffect copy() {
        return new EarthbenderAscensionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null || !permanent.addCounters(CounterType.QUEST.createInstance(), source, game)) {
            return false;
        }
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()), false, "When you do, " +
                "if it has four or more quest counters on it, put a +1/+1 counter on target creature you control. " +
                "It gains trample until end of turn", condition
        );
        ability.addEffect(new GainAbilityTargetEffect(TrampleAbility.getInstance()));
        ability.addTarget(new TargetControlledCreaturePermanent());
        game.fireReflexiveTriggeredAbility(ability, source);
        return true;
    }
}
