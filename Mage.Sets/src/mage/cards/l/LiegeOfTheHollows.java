package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.token.SquirrelToken;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.ManaUtil;

import java.util.UUID;

/**
 * @author arcox
 */
public final class LiegeOfTheHollows extends CardImpl {

    public LiegeOfTheHollows(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // When Liege of the Hollows dies, each player may pay any amount of mana.
        // Then each player creates a number of 1/1 green Squirrel creature tokens equal to the amount of mana they paid this way.
        this.addAbility(new DiesTriggeredAbility(new LiegeOfTheHollowsEffect()));
    }

    public LiegeOfTheHollows(final LiegeOfTheHollows card) {
        super(card);
    }

    @Override
    public LiegeOfTheHollows copy() {
        return new LiegeOfTheHollows(this);
    }
}

class LiegeOfTheHollowsEffect extends OneShotEffect {

    public LiegeOfTheHollowsEffect() {
        super(Outcome.Detriment);
        this.staticText = "each player may pay any amount of mana. Then each player creates a number "
                + "of 1/1 green Squirrel creature tokens equal to the amount of mana they paid this way";
    }

    public LiegeOfTheHollowsEffect(final LiegeOfTheHollowsEffect effect) {
        super(effect);
    }

    @Override
    public LiegeOfTheHollowsEffect copy() {
        return new LiegeOfTheHollowsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    int numSquirrels = ManaUtil.playerPaysXGenericMana(false, "Liege of the Hollows", player, source, game);
                    if (numSquirrels > 0) {
                        Effect effect = new CreateTokenTargetEffect(new SquirrelToken(), numSquirrels);
                        effect.setTargetPointer(new FixedTarget(playerId));
                        effect.apply(game, source);
                    }
                }
            }

            // prevent undo
            controller.resetStoredBookmark(game);
            return true;
        }
        return false;
    }
}
