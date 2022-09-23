package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MutalithVortexBeast extends CardImpl {

    public MutalithVortexBeast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}{R}");

        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Warp Vortex -- When Mutalith Vortex Beast enters the battlefield, flip a coin for each opponent you have. For each flip you win, draw a card. For each flip you lose, Mutalith Vortex Beast deals 3 damage to that player.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new MutalithVortexBeastEffect()).withFlavorWord("Warp Vortex"));
    }

    private MutalithVortexBeast(final MutalithVortexBeast card) {
        super(card);
    }

    @Override
    public MutalithVortexBeast copy() {
        return new MutalithVortexBeast(this);
    }
}

class MutalithVortexBeastEffect extends OneShotEffect {

    MutalithVortexBeastEffect() {
        super(Outcome.Benefit);
        staticText = "flip a coin for each opponent you have. For each flip you win, draw a card. " +
                "For each flip you lose, {this} deals 3 damage to that player";
    }

    private MutalithVortexBeastEffect(final MutalithVortexBeastEffect effect) {
        super(effect);
    }

    @Override
    public MutalithVortexBeastEffect copy() {
        return new MutalithVortexBeastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            if (player.flipCoin(source, game, true)) {
                player.drawCards(1, source, game);
                continue;
            }
            Player opponent = game.getPlayer(opponentId);
            if (opponent != null) {
                opponent.damage(3, source, game);
            }
        }
        return true;
    }
}
