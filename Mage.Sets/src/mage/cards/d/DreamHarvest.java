package mage.cards.d;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.asthought.PlayFromNotOwnHandZoneTargetEffect;
import mage.cards.*;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTargets;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DreamHarvest extends CardImpl {

    public DreamHarvest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{U/B}{U/B}");

        // Each opponent exiles cards from the top of their library until they have exiled cards with total mana value 5 or greater this way. Until end of turn, you may cast cards exiled this way without paying their mana costs.
        this.getSpellAbility().addEffect(new DreamHarvestEffect());
    }

    private DreamHarvest(final DreamHarvest card) {
        super(card);
    }

    @Override
    public DreamHarvest copy() {
        return new DreamHarvest(this);
    }
}

class DreamHarvestEffect extends OneShotEffect {

    DreamHarvestEffect() {
        super(Outcome.Benefit);
        staticText = "each opponent exiles cards from the top of their library until they have exiled cards " +
                "with total mana value 5 or greater this way. Until end of turn, " +
                "you may cast cards exiled this way without paying their mana costs";
    }

    private DreamHarvestEffect(final DreamHarvestEffect effect) {
        super(effect);
    }

    @Override
    public DreamHarvestEffect copy() {
        return new DreamHarvestEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Cards exiled = new CardsImpl();
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            Player player = game.getPlayer(opponentId);
            if (player == null) {
                continue;
            }
            Cards cards = new CardsImpl();
            for (Card card : player.getLibrary().getCards(game)) {
                player.moveCards(card, Zone.EXILED, source, game);
                cards.add(card);
                if (cards.getCards(game).stream().mapToInt(MageObject::getManaValue).sum() >= 5) {
                    break;
                }
            }
            exiled.addAll(cards);
        }
        game.addEffect(new PlayFromNotOwnHandZoneTargetEffect(
                Zone.EXILED, TargetController.YOU, Duration.EndOfTurn, true, true
        ).setTargetPointer(new FixedTargets(exiled, game)), source);
        return true;
    }
}
