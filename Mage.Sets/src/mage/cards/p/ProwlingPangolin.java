package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author LevelX2
 */
public final class ProwlingPangolin extends CardImpl {

    public ProwlingPangolin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");
        this.subtype.add(SubType.BEAST, SubType.PANGOLIN);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // When Prowling Pangolin enters the battlefield, any player may sacrifice two creatures. If a player does, sacrifice Prowling Pangolin.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ProwlingPangolinEffect(), false));
    }

    private ProwlingPangolin(final ProwlingPangolin card) {
        super(card);
    }

    @Override
    public ProwlingPangolin copy() {
        return new ProwlingPangolin(this);
    }
}

class ProwlingPangolinEffect extends OneShotEffect {

    ProwlingPangolinEffect() {
        super(Outcome.Sacrifice);
        this.staticText = "any player may sacrifice two creatures. If a player does, sacrifice {this}";
    }

    ProwlingPangolinEffect(final ProwlingPangolinEffect effect) {
        super(effect);
    }

    @Override
    public ProwlingPangolinEffect copy() {
        return new ProwlingPangolinEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            boolean costPaid = false;
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Cost cost = new SacrificeTargetCost(new TargetControlledPermanent(2, 2, new FilterControlledCreaturePermanent("creatures"), true));
                Player player = game.getPlayer(playerId);
                if (player != null
                        && cost.canPay(source, source, playerId, game)
                        && player.chooseUse(Outcome.Sacrifice, "Sacrifice two creatures?", source, game)
                        && cost.pay(source, game, source, playerId, true, null)) {
                    costPaid = true;
                }
            }
            if (costPaid) {
                Permanent sourceObject = game.getPermanent(source.getSourceId());
                if (sourceObject != null) {
                    sourceObject.sacrifice(source, game);
                }
            }
            return true;
        }
        return false;
    }
}
