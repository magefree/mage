package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ChooseACardNameEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author TheElk801
 */
public final class DesperateResearch extends CardImpl {

    public DesperateResearch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}");

        // Choose a card name other than a basic land card name. Reveal the top seven cards of your library and put all of them with that name into your hand. Exile the rest.
        this.getSpellAbility().addEffect(new ChooseACardNameEffect(ChooseACardNameEffect.TypeOfName.NOT_BASIC_LAND_NAME));
        this.getSpellAbility().addEffect(new DesperateResearchEffect());
    }

    private DesperateResearch(final DesperateResearch card) {
        super(card);
    }

    @Override
    public DesperateResearch copy() {
        return new DesperateResearch(this);
    }
}

class DesperateResearchEffect extends OneShotEffect {

    public DesperateResearchEffect() {
        super(Outcome.Benefit);
        this.staticText = "Reveal the top seven cards of your library and put all of them with that name into your hand. Exile the rest";
    }

    private DesperateResearchEffect(final DesperateResearchEffect effect) {
        super(effect);
    }

    @Override
    public DesperateResearchEffect copy() {
        return new DesperateResearchEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        String cardName = (String) game.getState().getValue(source.getSourceId().toString() + ChooseACardNameEffect.INFO_KEY);
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || cardName == null) {
            return false;
        }
        Cards cardsToExile = new CardsImpl(player.getLibrary().getTopCards(game, 7));
        player.revealCards(source, cardsToExile, game);
        FilterCard filter = new FilterCard();
        filter.add(new NamePredicate(cardName));
        Cards cardsToKeep = new CardsImpl(cardsToExile.getCards(filter, game));
        cardsToExile.removeAll(cardsToKeep);
        player.moveCards(cardsToKeep, Zone.HAND, source, game);
        player.moveCards(cardsToExile, Zone.EXILED, source, game);
        return true;
    }
}
