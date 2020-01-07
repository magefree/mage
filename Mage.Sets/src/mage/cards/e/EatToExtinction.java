package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EatToExtinction extends CardImpl {

    public EatToExtinction(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{B}");

        // Exile target creature or planeswalker. Look at the top card of your library. You may put that card into your graveyard.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());
        this.getSpellAbility().addEffect(new EatToExtinctionEffect());
    }

    private EatToExtinction(final EatToExtinction card) {
        super(card);
    }

    @Override
    public EatToExtinction copy() {
        return new EatToExtinction(this);
    }
}

class EatToExtinctionEffect extends OneShotEffect {

    EatToExtinctionEffect() {
        super(Outcome.Benefit);
        staticText = "Look at the top card of your library. You may put that card into your graveyard.";
    }

    private EatToExtinctionEffect(final EatToExtinctionEffect effect) {
        super(effect);
    }

    @Override
    public EatToExtinctionEffect copy() {
        return new EatToExtinctionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || player.getLibrary().size() == 0) {
            return false;
        }
        Card card = player.getLibrary().getFromTop(game);
        player.lookAtCards("Top card of your library", card, game);
        if (player.chooseUse(Outcome.AIDontUseIt, "Put the top card of your library into your graveyard?", source, game)) {
            player.moveCards(card, Zone.GRAVEYARD, source, game);
        }
        return true;
    }
}