package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author Loki
 */
public final class IdentityCrisis extends CardImpl {

    public IdentityCrisis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{W}{W}{B}{B}");

        this.getSpellAbility().addEffect(new IdentityCrisisEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private IdentityCrisis(final IdentityCrisis card) {
        super(card);
    }

    @Override
    public IdentityCrisis copy() {
        return new IdentityCrisis(this);
    }
}

class IdentityCrisisEffect extends OneShotEffect {

    IdentityCrisisEffect() {
        super(Outcome.Exile);
        staticText = "Exile all cards from target player's hand and graveyard";
    }

    private IdentityCrisisEffect(final IdentityCrisisEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getFirstTarget());
        Player player = game.getPlayer(source.getFirstTarget());
        if (controller == null || player == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        cards.addAll(player.getHand());
        cards.addAll(player.getGraveyard());
        return controller.moveCards(cards, Zone.EXILED, source, game);
    }

    @Override
    public IdentityCrisisEffect copy() {
        return new IdentityCrisisEffect(this);
    }
}
