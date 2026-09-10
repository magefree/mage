package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.keyword.AmassEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;

/**
 *
 * @author muz
 */
public final class BolgOfTheNorth extends CardImpl {

    public BolgOfTheNorth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // When Bolg enters, you may sacrifice another creature.
        // When you do, Bolg deals damage equal to that creature's power to another target creature. If excess damage was dealt this way, amass Goblins X, where X is that excess damage.
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(new BolgOfTheNorthEffect(), false);
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_ANOTHER_TARGET_CREATURE));

        this.addAbility(new EntersBattlefieldTriggeredAbility(new DoWhenCostPaid(
            ability,
            new SacrificeTargetCost(StaticFilters.FILTER_ANOTHER_CREATURE),
            "Sacrifice another creature?"
        )));
    }

    private BolgOfTheNorth(final BolgOfTheNorth card) {
        super(card);
    }

    @Override
    public BolgOfTheNorth copy() {
        return new BolgOfTheNorth(this);
    }
}

class BolgOfTheNorthEffect extends OneShotEffect {

    BolgOfTheNorthEffect() {
        super(Outcome.Benefit);
        staticText = "{this} deals damage equal to that creature's power to another target creature. If excess damage was dealt this way, amass Goblins X, where X is that excess damage";
    }

    private BolgOfTheNorthEffect(final BolgOfTheNorthEffect effect) {
        super(effect);
    }

    @Override
    public BolgOfTheNorthEffect copy() {
        return new BolgOfTheNorthEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        SacrificeTargetCost cost = source
            .getCosts()
            .stream()
            .filter(SacrificeTargetCost.class::isInstance)
            .map(SacrificeTargetCost.class::cast)
            .findFirst()
            .orElse(null);
        if (cost == null) {
            return false;
        }

        Permanent permanent = cost.getPermanents().get(0);
        if (permanent == null) {
            return false;
        }

        int amount = permanent.getPower().getValue();
        if (amount < 1) {
            return false;
        }

        Permanent targetCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (targetCreature == null) {
            return false;
        }

        int excess = targetCreature.damageWithExcess(amount, source, game);
        if (excess > 0) {
            new AmassEffect(excess, SubType.GOBLIN).apply(game, source);
        }
        return true;
    }
}
