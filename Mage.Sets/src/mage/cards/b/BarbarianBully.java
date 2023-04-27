package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BarbarianBully extends CardImpl {

    public BarbarianBully(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.BARBARIAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Discard a card at random: Barbarian Bully gets +2/+2 until end of turn unless a player has Barbarian Bully deal 4 damage to them. Activate this ability only once each turn.
        this.addAbility(new LimitedTimesPerTurnActivatedAbility(Zone.BATTLEFIELD, new BarbarianBullyEffect(), new DiscardCardCost(true)));
    }

    private BarbarianBully(final BarbarianBully card) {
        super(card);
    }

    @Override
    public BarbarianBully copy() {
        return new BarbarianBully(this);
    }
}

class BarbarianBullyEffect extends OneShotEffect {

    public BarbarianBullyEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "{this} gets +2/+2 until end of turn "
                + "unless a player has {this} deal 4 damage to them";
    }

    public BarbarianBullyEffect(final BarbarianBullyEffect effect) {
        super(effect);
    }

    @Override
    public BarbarianBullyEffect copy() {
        return new BarbarianBullyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (permanent == null) {
            return false;
        }
        boolean costPaid = false;
        for (UUID playerId : game.getState().getPlayerList(source.getControllerId())) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            if (player.chooseUse(Outcome.UnboostCreature, "Have " + permanent.getName() + " deal 4 damage to you?", source, game)) {
                player.damage(4, permanent.getId(), source, game);
                costPaid = true;
            }
        }
        if (!costPaid) {
            game.addEffect(new BoostSourceEffect(2, 2, Duration.EndOfTurn), source);
        }
        return true;
    }
}
