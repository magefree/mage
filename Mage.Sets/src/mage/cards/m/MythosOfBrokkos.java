package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.condition.CompoundCondition;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.ManaWasSpentCondition;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterPermanentCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MythosOfBrokkos extends CardImpl {

    public MythosOfBrokkos(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}{G}");

        // If {U}{B} was spent to cast Mythos of Brokkos, search your library for a card, put that card into your graveyard, then shuffle your library.
        // Return up to two permanent cards from your graveyard to your hand.
        this.getSpellAbility().addEffect(new MythosOfBrokkosEffect());
    }

    private MythosOfBrokkos(final MythosOfBrokkos card) {
        super(card);
    }

    @Override
    public MythosOfBrokkos copy() {
        return new MythosOfBrokkos(this);
    }
}

class MythosOfBrokkosEffect extends OneShotEffect {

    private static final Condition condition = new CompoundCondition(
            ManaWasSpentCondition.BLUE,
            ManaWasSpentCondition.BLACK
    );
    private static final FilterCard filter = new FilterPermanentCard("permanent cards");

    MythosOfBrokkosEffect() {
        super(Outcome.Benefit);
        staticText = "If {U}{B} was spent to cast this spell, search your library for a card, " +
                "put that card into your graveyard, then shuffle.<br>" +
                "Return up to two permanent cards from your graveyard to your hand.";
    }

    private MythosOfBrokkosEffect(final MythosOfBrokkosEffect effect) {
        super(effect);
    }

    @Override
    public MythosOfBrokkosEffect copy() {
        return new MythosOfBrokkosEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        if (condition.apply(game, source)) {
            TargetCardInLibrary targetCardInLibrary = new TargetCardInLibrary();
            if (player.searchLibrary(targetCardInLibrary, source, game)) {
                Card card = game.getCard(targetCardInLibrary.getFirstTarget());
                if (card != null) {
                    player.moveCards(card, Zone.GRAVEYARD, source, game);
                }
                player.shuffleLibrary(source, game);
            }
        }
        TargetCard targetCard = new TargetCardInYourGraveyard(0, 2, filter, true);
        player.choose(outcome, player.getGraveyard(), targetCard, source, game);
        Cards cards = new CardsImpl(targetCard.getTargets());
        return player.moveCards(cards, Zone.HAND, source, game);
    }

    @Override
    public Condition getCondition() {
        return condition;
    }
}
