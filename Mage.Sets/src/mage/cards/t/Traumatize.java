package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class Traumatize extends CardImpl {

    public Traumatize(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{U}{U}");

        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new TraumatizeEffect());
    }

    private Traumatize(final Traumatize card) {
        super(card);
    }

    @Override
    public Traumatize copy() {
        return new Traumatize(this);
    }
}

class TraumatizeEffect extends OneShotEffect {

    public TraumatizeEffect() {
        super(Outcome.Detriment);
        staticText = "Target player mills half their library, rounded down";
    }

    public TraumatizeEffect(final TraumatizeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        if (player != null) {
            int amount = player.getLibrary().size() / 2;
            player.millCards(amount, source, game);
            return true;
        }
        return false;
    }

    @Override
    public TraumatizeEffect copy() {
        return new TraumatizeEffect(this);
    }

}
