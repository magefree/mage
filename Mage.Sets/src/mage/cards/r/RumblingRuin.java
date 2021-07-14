package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.combat.CantBlockAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterOpponentsCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RumblingRuin extends CardImpl {

    public RumblingRuin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{R}");

        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // When Rumbling Ruin enters the battlefield, count the number of +1/+1 counters on creatures you control. Creatures your opponents control with power less than or equal to that number can't block this turn.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new RumblingRuinEffect()));
    }

    private RumblingRuin(final RumblingRuin card) {
        super(card);
    }

    @Override
    public RumblingRuin copy() {
        return new RumblingRuin(this);
    }
}

class RumblingRuinEffect extends OneShotEffect {

    RumblingRuinEffect() {
        super(Outcome.Benefit);
        staticText = "count the number of +1/+1 counters on creatures you control. " +
                "Creatures your opponents control with power less than or equal to that number can't block this turn.";
    }

    private RumblingRuinEffect(final RumblingRuinEffect effect) {
        super(effect);
    }

    @Override
    public RumblingRuinEffect copy() {
        return new RumblingRuinEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int counter = 1;
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(source.getControllerId())) {
            if (permanent == null || !permanent.isCreature(game)) {
                continue;
            }
            counter += permanent.getCounters(game).getCount(CounterType.P1P1);
        }
        FilterCreaturePermanent filter = new FilterOpponentsCreaturePermanent();
        filter.add(new PowerPredicate(ComparisonType.FEWER_THAN, counter));
        game.addEffect(new CantBlockAllEffect(filter, Duration.EndOfTurn), source);
        return true;
    }
}