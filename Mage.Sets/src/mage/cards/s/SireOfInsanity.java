package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class SireOfInsanity extends CardImpl {

    public SireOfInsanity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{R}");
        this.subtype.add(SubType.DEMON);

        this.power = new MageInt(6);
        this.toughness = new MageInt(4);

        // At the beginning of each end step, each player discards their hand.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new SireOfInsanityEffect(), TargetController.ANY, false
        ));
    }

    private SireOfInsanity(final SireOfInsanity card) {
        super(card);
    }

    @Override
    public SireOfInsanity copy() {
        return new SireOfInsanity(this);
    }

}

class SireOfInsanityEffect extends OneShotEffect {
    SireOfInsanityEffect() {
        super(Outcome.Discard);
        staticText = "each player discards their hand";
    }

    private SireOfInsanityEffect(final SireOfInsanityEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player sourcePlayer = game.getPlayer(source.getControllerId());
        if (sourcePlayer == null) {
            return false;
        }
        for (UUID playerId : game.getState().getPlayersInRange(sourcePlayer.getId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                player.discard(player.getHand(), false, source, game);
            }
        }
        return true;
    }

    @Override
    public SireOfInsanityEffect copy() {
        return new SireOfInsanityEffect(this);
    }
}
