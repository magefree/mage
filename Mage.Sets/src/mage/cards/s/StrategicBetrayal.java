package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StrategicBetrayal extends CardImpl {

    public StrategicBetrayal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}");

        // Target opponent exiles a creature they control and their graveyard.
        this.getSpellAbility().addEffect(new StrategicBetrayalEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private StrategicBetrayal(final StrategicBetrayal card) {
        super(card);
    }

    @Override
    public StrategicBetrayal copy() {
        return new StrategicBetrayal(this);
    }
}

class StrategicBetrayalEffect extends OneShotEffect {

    StrategicBetrayalEffect() {
        super(Outcome.Benefit);
        staticText = "target opponent exiles a creature they control and their graveyard";
    }

    private StrategicBetrayalEffect(final StrategicBetrayalEffect effect) {
        super(effect);
    }

    @Override
    public StrategicBetrayalEffect copy() {
        return new StrategicBetrayalEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(player.getGraveyard());
        if (game.getBattlefield().contains(
                StaticFilters.FILTER_CONTROLLED_CREATURE,
                player.getId(), source, game, 1
        )) {
            TargetPermanent target = new TargetControlledCreaturePermanent();
            target.withNotTarget(true);
            player.choose(Outcome.DestroyPermanent, target, source, game);
            cards.add(target.getFirstTarget());
        }
        return player.moveCards(cards, Zone.EXILED, source, game);
    }
}
