package mage.sets.fifthdawn;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author andyfries
 */
public class Acquire extends CardImpl{
    public Acquire(UUID ownerId) {
        super(ownerId, 21, "Acquire", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{3}{U}{U}");
        this.expansionSetCode = "5DN";


        // Search target opponent's library for an artifact card and put that card onto the battlefield under your control.
        // Then that player shuffles his or her library.
        this.getSpellAbility().addEffect(new AcquireEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    public Acquire(final Acquire card) {
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
        filter.add(new CardTypePredicate(CardType.ARTIFACT));
    }

    public AcquireEffect() {
        super(Outcome.PutCardInPlay);
        staticText = "Search target opponent's library for an artifact card and put that card onto the battlefield under your control. Then that player shuffles his or her library";
    }

    public AcquireEffect(final AcquireEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(source.getFirstTarget());
        Player controller = game.getPlayer(source.getControllerId());
        if (opponent != null && controller != null) {
            TargetCardInLibrary target = new TargetCardInLibrary(filter);
            controller.searchLibrary(target, game, opponent.getId());
            Card targetCard = game.getCard(target.getFirstTarget());
            if (targetCard != null) {
                controller.putOntoBattlefieldWithInfo(targetCard, game, Zone.LIBRARY, source.getSourceId());
            }
            opponent.shuffleLibrary(game);
            return true;
        }
        return false;
    }

    @Override
    public AcquireEffect copy() {
        return new AcquireEffect(this);
    }
}

