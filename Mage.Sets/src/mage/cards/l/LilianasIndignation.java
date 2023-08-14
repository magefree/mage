package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

import java.util.Objects;
import java.util.UUID;

/**
 * @author LevelX2
 */
public final class LilianasIndignation extends CardImpl {

    public LilianasIndignation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{B}");

        // Put the top X cards of your library into your graveyard. Target player loses 2 life for each creature card put into your graveyard this way.
        this.getSpellAbility().addEffect(new LilianasIndignationEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private LilianasIndignation(final LilianasIndignation card) {
        super(card);
    }

    @Override
    public LilianasIndignation copy() {
        return new LilianasIndignation(this);
    }
}

class LilianasIndignationEffect extends OneShotEffect {

    public LilianasIndignationEffect() {
        super(Outcome.LoseLife);
        this.staticText = "Mill X cards. Target player loses 2 life for each creature card put into your graveyard this way";
    }

    public LilianasIndignationEffect(final LilianasIndignationEffect effect) {
        super(effect);
    }

    @Override
    public LilianasIndignationEffect copy() {
        return new LilianasIndignationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        int xValue = source.getManaCostsToPay().getX();
        if (xValue < 1) {
            return true;
        }
        int creatures = controller
                .millCards(xValue, source, game)
                .getCards(game)
                .stream()
                .filter(Objects::nonNull)
                .filter(card -> game.getState().getZone(card.getId()) == Zone.GRAVEYARD)
                .filter(card1 -> card1.isCreature(game))
                .mapToInt(x -> 2)
                .sum();
        if (creatures > 0) {
            Player targetPlayer = game.getPlayer(source.getFirstTarget());
            if (targetPlayer != null) {
                game.getState().processAction(game);
                targetPlayer.loseLife(creatures, game, source, false);
            }
        }
        return true;
    }
}
