package mage.cards.k;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KaylasReconstruction extends CardImpl {

    public KaylasReconstruction(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{W}{W}{W}");

        // Look at the top seven cards of your library. Put up to X artifact and/or creature cards with mana value 3 or less from among them onto the battlefield. Put the rest on the bottom of your library in a random order.
        this.getSpellAbility().addEffect(new KaylasReconstructionEffect());
    }

    private KaylasReconstruction(final KaylasReconstruction card) {
        super(card);
    }

    @Override
    public KaylasReconstruction copy() {
        return new KaylasReconstruction(this);
    }
}

class KaylasReconstructionEffect extends OneShotEffect {

    private static final FilterCard filter
            = new FilterCard("artifact and/or creature cards with mana value 3 or less");

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate()
        ));
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
    }

    KaylasReconstructionEffect() {
        super(Outcome.Benefit);
        staticText = "look at the top seven cards of your library. Put up to X artifact " +
                "and/or creature cards with mana value 3 or less from among them onto the battlefield. " +
                "Put the rest on the bottom of your library in a random order";
    }

    private KaylasReconstructionEffect(final KaylasReconstructionEffect effect) {
        super(effect);
    }

    @Override
    public KaylasReconstructionEffect copy() {
        return new KaylasReconstructionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, 7));
        TargetCardInLibrary target = new TargetCardInLibrary(0, source.getManaCostsToPay().getX(), filter);
        player.choose(outcome, cards, target, source, game);
        Cards toBattlefield = new CardsImpl(target.getTargets());
        player.moveCards(toBattlefield, Zone.BATTLEFIELD, source, game);
        cards.retainZone(Zone.LIBRARY, game);
        player.putCardsOnBottomOfLibrary(cards, game, source, false);
        return true;
    }
}
