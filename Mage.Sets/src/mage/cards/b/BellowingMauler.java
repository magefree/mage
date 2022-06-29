package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfYourEndStepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

/**
 *
 * @author weirddan455
 */
public final class BellowingMauler extends CardImpl {

    public BellowingMauler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");

        this.subtype.add(SubType.OGRE);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(6);

        // At the beginning of your end step, each player loses 4 life unless they sacrifice a nontoken creature.
        this.addAbility(new BeginningOfYourEndStepTriggeredAbility(new BellowingMaulerEffect(), false));
    }

    private BellowingMauler(final BellowingMauler card) {
        super(card);
    }

    @Override
    public BellowingMauler copy() {
        return new BellowingMauler(this);
    }
}

class BellowingMaulerEffect extends OneShotEffect {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("nontoken creature");

    static {
        filter.add(TokenPredicate.FALSE);
    }

    public BellowingMaulerEffect() {
        super(Outcome.Sacrifice);
        this.staticText = "each player loses 4 life unless they sacrifice a nontoken creature";
    }

    private BellowingMaulerEffect(final BellowingMaulerEffect effect) {
        super(effect);
    }

    @Override
    public BellowingMaulerEffect copy() {
        return new BellowingMaulerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                boolean sacrificed = false;
                TargetPermanent target = new TargetPermanent(1, 1, filter, true);
                if (target.canChoose(playerId, source, game)
                        && player.chooseUse(Outcome.Sacrifice, "Sacrifice a nontoken creature or lose 4 life?", null, "Sacrifice", "Lose 4 life", source, game)) {
                    player.chooseTarget(Outcome.Sacrifice, target, source, game);
                    Permanent permanent = game.getPermanent(target.getFirstTarget());
                    sacrificed = permanent != null && permanent.sacrifice(source, game);
                }
                if (!sacrificed) {
                    player.loseLife(4, game, source, false);
                }
            }
        }
        return true;
    }
}
