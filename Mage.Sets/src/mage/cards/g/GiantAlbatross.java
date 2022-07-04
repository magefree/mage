package mage.cards.g;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.List;
import java.util.UUID;

/**
 * @author noahg
 */
public final class GiantAlbatross extends CardImpl {

    public GiantAlbatross(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Giant Albatross dies, you may pay {1}{U}. If you do, for each creature that dealt damage to Giant Albatross this turn, destroy that creature unless its controller pays 2 life. A creature destroyed this way can't be regenerated.
        Ability ability = new DiesSourceTriggeredAbility(new DoIfCostPaid(new GiantAlbatrossEffect(), new ManaCostsImpl<>("{1}{U}")));
        this.addAbility(ability);
    }

    private GiantAlbatross(final GiantAlbatross card) {
        super(card);
    }

    @Override
    public GiantAlbatross copy() {
        return new GiantAlbatross(this);
    }
}

class GiantAlbatrossEffect extends OneShotEffect {

    public GiantAlbatrossEffect() {
        super(Outcome.Detriment);
        this.staticText = "for each creature that dealt damage to {this} this turn, destroy that creature unless its controller pays 2 life. A creature destroyed this way can't be regenerated";
    }

    public GiantAlbatrossEffect(final GiantAlbatrossEffect effect) {
        super(effect);
    }

    @Override
    public GiantAlbatrossEffect copy() {
        return new GiantAlbatrossEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (sourcePermanent != null && controller != null) {
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    List<Permanent> creatures = game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, playerId, game);

                    Cost cost = new PayLifeCost(2);
                    for (Permanent creature : creatures) {
                        if (sourcePermanent.getDealtDamageByThisTurn().contains(new MageObjectReference(creature.getId(), game))) {
                            final StringBuilder sb = new StringBuilder("Pay 2 life? (Otherwise ").append(creature.getName()).append(" will be destroyed)");
                            if (cost.canPay(source, source, creature.getControllerId(), game) && player.chooseUse(Outcome.Benefit, sb.toString(), source, game)) {
                                cost.pay(source, game, source, creature.getControllerId(), true, null);
                            }
                            if (!cost.isPaid()) {
                                creature.destroy(source, game, true);
                            }
                        }
                    }
                }
            }
        }

        return false;
    }
}