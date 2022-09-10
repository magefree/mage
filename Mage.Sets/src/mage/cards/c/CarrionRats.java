package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksOrBlocksTriggeredAbility;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.AssignNoCombatDamageSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 *
 * @author awjackson
 */
public final class CarrionRats extends CardImpl {

    public CarrionRats(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}");
        this.subtype.add(SubType.RAT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever Carrion Rats attacks or blocks, any player may exile a card from their graveyard.
        // If a player does, Carrion Rats assigns no combat damage this turn.
        this.addAbility(new AttacksOrBlocksTriggeredAbility(new CarrionRatsEffect(), false));
    }

    private CarrionRats(final CarrionRats card) {
        super(card);
    }

    @Override
    public CarrionRats copy() {
        return new CarrionRats(this);
    }
}

class CarrionRatsEffect extends OneShotEffect {

    public CarrionRatsEffect() {
        super(Outcome.Neutral);
        this.staticText = "any player may exile a card from their graveyard. If a player does, {this} assigns no combat damage this turn";
    }

    public CarrionRatsEffect(final CarrionRatsEffect effect) {
        super(effect);
    }

    @Override
    public CarrionRatsEffect copy() {
        return new CarrionRatsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentOrLKI(game);
        if (permanent == null) {
            return false;
        }
        ExileFromGraveCost cost = new ExileFromGraveCost(new TargetCardInYourGraveyard());
        String message = "Exile a card from your graveyard to have " + permanent.getIdName() + " assign no combat damage?";
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            cost.clearPaid();
            if (player != null
                    && player.canRespond()
                    && cost.canPay(source, source, playerId, game)
                    && player.chooseUse(Outcome.Benefit, message, source, game)
                    && cost.pay(source, game, source, playerId, false, null)) {
                if (!game.isSimulation()) {
                    game.informPlayers(player.getLogName() + " exiles a card to have " + permanent.getLogName() + " assign no combat damage.");
                }
                game.addEffect(new AssignNoCombatDamageSourceEffect(Duration.EndOfTurn), source);
                break;
            }
        }
        return true;
    }
}
