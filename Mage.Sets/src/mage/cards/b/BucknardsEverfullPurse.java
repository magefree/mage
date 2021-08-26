package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.TreasureToken;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BucknardsEverfullPurse extends CardImpl {

    public BucknardsEverfullPurse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // {1}, {T}: Roll a d4 and create a number of Treasure tokens equal to the result. The player to your right gains control of Bucknard's Everfull Purse.
        Ability ability = new SimpleActivatedAbility(new BucknardsEverfullPurseEffect(), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private BucknardsEverfullPurse(final BucknardsEverfullPurse card) {
        super(card);
    }

    @Override
    public BucknardsEverfullPurse copy() {
        return new BucknardsEverfullPurse(this);
    }
}

class BucknardsEverfullPurseEffect extends OneShotEffect {

    BucknardsEverfullPurseEffect() {
        super(Outcome.Benefit);
        staticText = "roll a d4 and create a number of Treasure tokens equal to the result. " +
                "The player to your right gains control of {this}";
    }

    private BucknardsEverfullPurseEffect(final BucknardsEverfullPurseEffect effect) {
        super(effect);
    }

    @Override
    public BucknardsEverfullPurseEffect copy() {
        return new BucknardsEverfullPurseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        new TreasureToken().putOntoBattlefield(
                player.rollDice(outcome, source, game, 4),
                game, source, source.getControllerId()
        );
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null) {
            return true;
        }
        UUID playerToRightId = game
                .getState()
                .getPlayersInRange(source.getControllerId(), game)
                .stream()
                .reduce((u1, u2) -> u2)
                .orElse(null);
        if (playerToRightId == null) {
            return false;
        }
        game.addEffect(new GainControlTargetEffect(
                Duration.Custom, true, playerToRightId
        ).setTargetPointer(new FixedTarget(permanent, game)), source);
        return true;
    }
}
