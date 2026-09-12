package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author muz
 */
public final class SupportMission extends CardImpl {

    public SupportMission(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");


        // Whenever a creature you control enters, put a quest counter on this enchantment. Put a +1/+1 counter on that creature. If this enchantment has five or more quest counters on it, instead put two +1/+1 counters on that creature.
        Ability ability = new EntersBattlefieldControlledTriggeredAbility(
            Zone.BATTLEFIELD,
            new AddCountersSourceEffect(CounterType.QUEST.createInstance()),
            StaticFilters.FILTER_PERMANENT_A_CREATURE,
            false,
            SetTargetPointer.PERMANENT
        );
        ability.addEffect(new SupportMissionEffect());
        this.addAbility(ability);
    }

    private SupportMission(final SupportMission card) {
        super(card);
    }

    @Override
    public SupportMission copy() {
        return new SupportMission(this);
    }
}

class SupportMissionEffect extends OneShotEffect {

    public SupportMissionEffect() {
        super(Outcome.Benefit);
        this.staticText = "Put a +1/+1 counter on that creature. If this enchantment has five or more quest counters on it, instead put two +1/+1 counters on that creature";
    }

    private SupportMissionEffect(final SupportMissionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (permanent != null && sourcePermanent != null) {
            int questCounters = sourcePermanent.getCounters(game).getCount(CounterType.QUEST);
            int countersToAdd = questCounters >= 5 ? 2 : 1;
            permanent.addCounters(CounterType.P1P1.createInstance(countersToAdd), source, game);
            return true;
        }
        return false;
    }

    @Override
    public SupportMissionEffect copy() {
        return new SupportMissionEffect(this);
    }
}
