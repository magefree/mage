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
public final class CarrionWurm extends CardImpl {

    public CarrionWurm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}{B}");
        this.subtype.add(SubType.ZOMBIE, SubType.WURM);

        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // Whenever Carrion Wurm attacks or blocks, any player may exile three cards from their graveyard.
        // If a player does, Carrion Wurm assigns no combat damage this turn.
        this.addAbility(new AttacksOrBlocksTriggeredAbility(new CarrionWurmEffect(), false));
    }

    private CarrionWurm(final CarrionWurm card) {
        super(card);
    }

    @Override
    public CarrionWurm copy() {
        return new CarrionWurm(this);
    }
}

class CarrionWurmEffect extends OneShotEffect {

    public CarrionWurmEffect() {
        super(Outcome.Neutral);
        this.staticText = "any player may exile three cards from their graveyard. If a player does, {this} assigns no combat damage this turn";
    }

    public CarrionWurmEffect(final CarrionWurmEffect effect) {
        super(effect);
    }

    @Override
    public CarrionWurmEffect copy() {
        return new CarrionWurmEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentOrLKI(game);
        if (permanent == null) {
            return false;
        }
        ExileFromGraveCost cost = new ExileFromGraveCost(new TargetCardInYourGraveyard(3));
        String message = "Exile three cards from your graveyard to have " + permanent.getIdName() + " assign no combat damage?";
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            cost.clearPaid();
            if (player != null
                    && player.canRespond()
                    && cost.canPay(source, source, playerId, game)
                    && player.chooseUse(Outcome.Benefit, message, source, game)
                    && cost.pay(source, game, source, playerId, false, null)) {
                if (!game.isSimulation()) {
                    game.informPlayers(player.getLogName() + " exiles three cards to have " + permanent.getLogName() + " assign no combat damage.");
                }
                game.addEffect(new AssignNoCombatDamageSourceEffect(Duration.EndOfTurn), source);
                break;
            }
        }
        return true;
    }
}
