package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCardInYourGraveyard;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class SecretSalvage extends CardImpl {

    private static final FilterCard filter = new FilterNonlandCard("nonland card from your graveyard");

    public SecretSalvage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}{B}");

        // Exile target nonland card from your graveyard. Search your library for any number of cards with the same name as that card,
        // reveal them, and put them into your hand. Then shuffle your library.
        getSpellAbility().addEffect(new SecretSalvageEffect());
        getSpellAbility().addTarget(new TargetCardInYourGraveyard(filter));
    }

    private SecretSalvage(final SecretSalvage card) {
        super(card);
    }

    @Override
    public SecretSalvage copy() {
        return new SecretSalvage(this);
    }
}

class SecretSalvageEffect extends OneShotEffect {

    public SecretSalvageEffect() {
        super(Outcome.DrawCard);
        staticText = "Exile target nonland card from your graveyard. " +
                "Search your library for any number of cards with the same name as that card, " +
                "reveal them, put them into your hand, then shuffle";
    }

    public SecretSalvageEffect(final SecretSalvageEffect effect) {
        super(effect);
    }

    @Override
    public SecretSalvageEffect copy() {
        return new SecretSalvageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card targetCard = game.getCard(getTargetPointer().getFirst(game, source));
        if (controller == null || targetCard == null) {
            return false;
        }
        controller.moveCards(targetCard, Zone.EXILED, source, game);
        String nameToSearch = CardUtil.getCardNameForSameNameSearch(targetCard);
        FilterCard nameFilter = new FilterCard("card named " + nameToSearch);
        nameFilter.add(new NamePredicate(nameToSearch));
        return new SearchLibraryPutInHandEffect(new TargetCardInLibrary(
                0, Integer.MAX_VALUE, nameFilter
        ), true, true).apply(game, source);
    }
}
