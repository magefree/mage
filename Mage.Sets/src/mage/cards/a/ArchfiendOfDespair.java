
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.CantGainLifeAllEffect;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.watchers.common.PlayerLostLifeWatcher;

/**
 *
 * @author TheElk801
 */
public final class ArchfiendOfDespair extends CardImpl {

    public ArchfiendOfDespair(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{B}{B}");

        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Your opponents can't gain life.
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new CantGainLifeAllEffect(
                        Duration.WhileOnBattlefield,
                        TargetController.OPPONENT
                )
        ));

        // At the beginning of each end step, each opponent loses life equal to the life that player lost this turn. (Damage causes loss of life.)
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new ArchfiendOfDespairEffect(), TargetController.ANY, false));
    }

    private ArchfiendOfDespair(final ArchfiendOfDespair card) {
        super(card);
    }

    @Override
    public ArchfiendOfDespair copy() {
        return new ArchfiendOfDespair(this);
    }
}

class ArchfiendOfDespairEffect extends OneShotEffect {

    public ArchfiendOfDespairEffect() {
        super(Outcome.LoseLife);
        this.staticText = "each opponent loses life equal to the life that player lost this turn";
    }

    private ArchfiendOfDespairEffect(final ArchfiendOfDespairEffect effect) {
        super(effect);
    }

    @Override
    public ArchfiendOfDespairEffect copy() {
        return new ArchfiendOfDespairEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        PlayerLostLifeWatcher watcher = game.getState().getWatcher(PlayerLostLifeWatcher.class);
        if (controller != null && watcher != null) {
            for (UUID playerId : game.getOpponents(controller.getId())) {
                Player opponent = game.getPlayer(playerId);
                if (opponent != null) {
                    int lifeLost = watcher.getLifeLost(playerId);
                    if (lifeLost > 0) {
                        opponent.loseLife(lifeLost, game, source, false);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
