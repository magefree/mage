package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetOpponent;

/**
 * @author andyfries
 */
public final class Acquire extends CardImpl {

    public Acquire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{U}{U}");

        // Search target opponent's library for an artifact card and put that card onto the battlefield under your control.
        // Then that player shuffles their library.
        this.getSpellAbility().addEffect(new AcquireEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private Acquire(final Acquire card) {
        super(card);
    }

    @Override
    public Acquire copy() {
        return new Acquire(this);
    }
}

class AcquireEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("an artifact card");

    static {
        filter.add(CardType.ARTIFACT.getPredicate());
    }

    public AcquireEffect() {
        super(Outcome.PutCardInPlay);
        staticText = "Search target opponent's library for an artifact card and put that card onto the battlefield under your control. Then that player shuffles";
    }

    private AcquireEffect(final AcquireEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(source.getFirstTarget());
        Player controller = game.getPlayer(source.getControllerId());
        if (opponent != null && controller != null) {
            TargetCardInLibrary target = new TargetCardInLibrary(filter);
            controller.searchLibrary(target, source, game, opponent.getId());
            Card targetCard = game.getCard(target.getFirstTarget());
            if (targetCard != null) {
                controller.moveCards(targetCard, Zone.BATTLEFIELD, source, game);
            }
            opponent.shuffleLibrary(source, game);
            return true;
        }
        return false;
    }

    @Override
    public AcquireEffect copy() {
        return new AcquireEffect(this);
    }
}
