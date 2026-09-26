package mage.cards.j;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.AttacksAllTriggeredAbility;
import mage.abilities.common.delayed.UntilYourNextTurnDelayedTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.keyword.EmpowerJaceEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author muz
 */
public final class JaceRealitySculptor extends CardImpl {

    private static final FilterControlledPermanent filterIsland =
    new FilterControlledPermanent(SubType.ISLAND, "Islands you control");
    private static final DynamicValue islands = new PermanentsOnBattlefieldCount(filterIsland);

    public JaceRealitySculptor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{3}{U}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.JACE);
        this.setStartingLoyalty(5);

        // +1: Empower Jace X, where X is the number of Islands you control.
        this.addAbility(new LoyaltyAbility(new EmpowerJaceEffect(islands), 1));

        // −3: Until your next turn, whenever a creature attacks you or a planeswalker you control, it gets -5/-0 until end of turn.
        this.addAbility(new LoyaltyAbility(new CreateDelayedTriggeredAbilityEffect(
            new UntilYourNextTurnDelayedTriggeredAbility(
                new AttacksAllTriggeredAbility(
                    new BoostTargetEffect(-5, 0).setText("it gets -5/-0 until end of turn"),
                    false,
                    StaticFilters.FILTER_PERMANENT_CREATURE,
                    SetTargetPointer.PERMANENT,
                    true
                )
            )
        ), -3));

        // 0: Exile all but the bottom card of each opponent's library. Activate only if there are twenty-five or more loyalty counters among Jaces you control.
        this.addAbility(new LoyaltyAbility(new JaceRealitySculptorExileLibrariesEffect(), 0)
            .setCondition(JaceRealitySculptorLoyaltyCondition.instance));
    }

    private JaceRealitySculptor(final JaceRealitySculptor card) {
        super(card);
    }

    @Override
    public JaceRealitySculptor copy() {
        return new JaceRealitySculptor(this);
    }
}

enum JaceRealitySculptorLoyaltyCondition implements Condition {
    instance;

    static final FilterControlledPermanent filterJace = new FilterControlledPermanent(SubType.JACE, "Jaces you control");
    static {
        filterJace.add(CardType.PLANESWALKER.getPredicate());
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int loyalty = game.getBattlefield().getActivePermanents(filterJace, source.getControllerId(), source, game)
            .stream()
            .mapToInt(permanent -> permanent.getCounters(game).getCount(CounterType.LOYALTY))
            .sum();
        return loyalty >= 25;
    }

    @Override
    public String toString() {
        return "there are twenty-five or more loyalty counters among Jaces you control";
    }
}

class JaceRealitySculptorExileLibrariesEffect extends OneShotEffect {

    JaceRealitySculptorExileLibrariesEffect() {
        super(Outcome.Benefit);
        staticText = "Exile all but the bottom card of each opponent's library.";
    }

    private JaceRealitySculptorExileLibrariesEffect(final JaceRealitySculptorExileLibrariesEffect effect) {
        super(effect);
    }

    @Override
    public JaceRealitySculptorExileLibrariesEffect copy() {
        return new JaceRealitySculptorExileLibrariesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            Player opponent = game.getPlayer(opponentId);
            if (opponent == null) {
                continue;
            }
            int cardsToExile = Math.max(0, opponent.getLibrary().size() - 1);
            if (cardsToExile > 0) {
                controller.moveCards(
                    opponent.getLibrary().getTopCards(game, cardsToExile),
                    Zone.EXILED,
                    source, game
                );
            }
        }
        return true;
    }
}
