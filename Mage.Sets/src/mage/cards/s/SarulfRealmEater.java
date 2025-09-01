package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.PutIntoGraveFromBattlefieldAllTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SarulfRealmEater extends CardImpl {

    private static final Condition condition = new SourceHasCounterCondition(CounterType.P1P1);

    public SarulfRealmEater(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.WOLF);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever a permanent an opponent controls is put into a graveyard from the battlefield, put a +1/+1 counter on Sarulf, Realm Eater.
        this.addAbility(new PutIntoGraveFromBattlefieldAllTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false,
                StaticFilters.FILTER_OPPONENTS_PERMANENT, false, false
        ));

        // At the beginning of your upkeep, if Sarulf has one or more +1/+1 counters on it, you may remove all of them. If you do, exile each other nonland permanent with converted mana cost less than or equal to the number of counters removed this way.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new SarulfRealmEaterEffect(), true).withInterveningIf(condition));
    }

    private SarulfRealmEater(final SarulfRealmEater card) {
        super(card);
    }

    @Override
    public SarulfRealmEater copy() {
        return new SarulfRealmEater(this);
    }
}

class SarulfRealmEaterEffect extends OneShotEffect {

    SarulfRealmEaterEffect() {
        super(Outcome.Benefit);
        staticText = "remove all of them. If you do, exile each other nonland permanent with mana value " +
                "less than or equal to the number of counters removed this way";
    }

    private SarulfRealmEaterEffect(final SarulfRealmEaterEffect effect) {
        super(effect);
    }

    @Override
    public SarulfRealmEaterEffect copy() {
        return new SarulfRealmEaterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (player == null || permanent == null) {
            return false;
        }
        int removedThisWay = permanent.removeAllCounters(CounterType.P1P1.getName(), source, game);
        FilterPermanent filter = new FilterNonlandPermanent();
        filter.add(new ManaValuePredicate(ComparisonType.OR_LESS, removedThisWay));
        filter.add(AnotherPredicate.instance);
        List<Permanent> permanents = game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game);
        if (!permanents.isEmpty()) {
            player.moveCards(new HashSet<>(permanents), Zone.EXILED, source, game);
        }
        return true;
    }
}
