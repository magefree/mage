package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.asthought.PlayFromNotOwnHandZoneTargetEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;

import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IgniteTheFuture extends CardImpl {

    public IgniteTheFuture(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}");

        // Exile the top three cards of your library. Until the end of your next turn, you may play those cards. If this spell was cast from a graveyard, you may play cards this way without paying their mana costs.
        this.getSpellAbility().addEffect(new IgniteTheFutureEffect());

        // Flashback {7}{R}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl("{7}{R}")));
    }

    private IgniteTheFuture(final IgniteTheFuture card) {
        super(card);
    }

    @Override
    public IgniteTheFuture copy() {
        return new IgniteTheFuture(this);
    }
}

class IgniteTheFutureEffect extends OneShotEffect {

    IgniteTheFutureEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "Exile the top three cards of your library. " +
                "Until the end of your next turn, you may play those cards. " +
                "If this spell was cast from a graveyard, " +
                "you may play cards this way without paying their mana costs.";
    }

    private IgniteTheFutureEffect(final IgniteTheFutureEffect effect) {
        super(effect);
    }

    @Override
    public IgniteTheFutureEffect copy() {
        return new IgniteTheFutureEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Spell spell = (Spell) game.getStack().getStackObject(source.getSourceId());
        if (controller == null || spell == null) {
            return false;
        }
        Set<Card> cards = controller.getLibrary().getTopCards(game, 3);
        return PlayFromNotOwnHandZoneTargetEffect.exileAndPlayFromExile(game, source, cards,
                TargetController.YOU, Duration.UntilEndOfYourNextTurn, Zone.GRAVEYARD.equals(spell.getFromZone()), false, false);
    }
}
