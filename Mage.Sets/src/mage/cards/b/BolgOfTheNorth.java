package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.OneShotEffect;
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
        this.addAbility(new EntersBattlefieldTriggeredAbility(new BolgOfTheNorthSacrificeEffect()));
    }

    private BolgOfTheNorth(final BolgOfTheNorth card) {
        super(card);
    }

    @Override
    public BolgOfTheNorth copy() {
        return new BolgOfTheNorth(this);
    }
}

class BolgOfTheNorthSacrificeEffect extends OneShotEffect {

    BolgOfTheNorthSacrificeEffect() {
        super(Outcome.Benefit);
        staticText = "you may sacrifice another creature. When you do, {this} deals damage equal to that creature's "
                + "power to another target creature. If excess damage was dealt this way, amass Goblins X, "
                + "where X is that excess damage";
    }

    private BolgOfTheNorthSacrificeEffect(final BolgOfTheNorthSacrificeEffect effect) {
        super(effect);
    }

    @Override
    public BolgOfTheNorthSacrificeEffect copy() {
        return new BolgOfTheNorthSacrificeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        SacrificeTargetCost cost = new SacrificeTargetCost(StaticFilters.FILTER_ANOTHER_CREATURE);
        if (!cost.canPay(source, source, player.getId(), game)
                || !player.chooseUse(Outcome.Sacrifice, "Sacrifice another creature?", source, game)
                || !cost.pay(source, game, source, player.getId(), false)) {
            return false;
        }

        // The cost keeps the creature's characteristics from before it was sacrificed.
        int power = cost.getPermanents().get(0).getPower().getValue();
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(new BolgOfTheNorthEffect(power), false);
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_ANOTHER_TARGET_CREATURE));
        game.fireReflexiveTriggeredAbility(ability, source);
        return true;
    }
}

class BolgOfTheNorthEffect extends OneShotEffect {

    private final int amount;

    BolgOfTheNorthEffect(int amount) {
        super(Outcome.Benefit);
        this.amount = amount;
        staticText = "{this} deals damage equal to that creature's power to another target creature. "
                + "If excess damage was dealt this way, amass Goblins X, where X is that excess damage";
    }

    private BolgOfTheNorthEffect(final BolgOfTheNorthEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public BolgOfTheNorthEffect copy() {
        return new BolgOfTheNorthEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent targetCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (targetCreature == null) {
            return false;
        }

        int lethal = targetCreature.getLethalDamage(source.getSourceId(), game);
        int dealt = targetCreature.damage(amount, source, game);
        int excess = Math.max(dealt - lethal, 0);
        if (excess > 0) {
            new AmassEffect(excess, SubType.GOBLIN).apply(game, source);
        }
        return true;
    }
}
