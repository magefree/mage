package mage.cards.i;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.condition.common.HellbentCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCardInLibrary;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class InfernalTutor extends CardImpl {

    public InfernalTutor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}");

        // Reveal a card from your hand. Search your library for a card with the same name as that card, reveal it, put it into your hand, then shuffle your library.
        this.getSpellAbility().addEffect(new InfernalTutorEffect());
        // Hellbent - If you have no cards in hand, instead search your library for a card, put it into your hand, then shuffle your library.
        Effect effect = new ConditionalOneShotEffect(
                new SearchLibraryPutInHandEffect(new TargetCardInLibrary(StaticFilters.FILTER_CARD), false, true),
                HellbentCondition.instance,
                "<br/><br/><i>Hellbent</i> &mdash; If you have no cards in hand, instead search your library for a card, put it into your hand, then shuffle");
        this.getSpellAbility().addEffect(effect);

    }

    private InfernalTutor(final InfernalTutor card) {
        super(card);
    }

    @Override
    public InfernalTutor copy() {
        return new InfernalTutor(this);
    }
}

class InfernalTutorEffect extends OneShotEffect {

    public InfernalTutorEffect() {
        super(Outcome.Benefit);
        this.staticText = "Reveal a card from your hand. Search your library for a card with the same name as that card, reveal it, put it into your hand, then shuffle";
    }

    public InfernalTutorEffect(final InfernalTutorEffect effect) {
        super(effect);
    }

    @Override
    public InfernalTutorEffect copy() {
        return new InfernalTutorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        if (controller != null && sourceObject != null) {
            if (!controller.getHand().isEmpty()) {
                Card cardToReveal = null;
                if (controller.getHand().size() > 1) {
                    Target target = new TargetCardInHand(StaticFilters.FILTER_CARD);
                    target.setNotTarget(true);
                    if (controller.chooseTarget(outcome, target, source, game)) {
                        cardToReveal = game.getCard(target.getFirstTarget());
                    }
                } else {
                    cardToReveal = controller.getHand().getRandom(game);
                }
                FilterCard filterCard;
                if (cardToReveal != null) {
                    controller.revealCards("from hand :" + sourceObject.getName(), new CardsImpl(cardToReveal), game);
                    String nameToSearch = CardUtil.getCardNameForSameNameSearch(cardToReveal);
                    filterCard = new FilterCard("card named " + nameToSearch);
                    filterCard.add(new NamePredicate(nameToSearch));
                } else {
                    filterCard = new FilterCard();
                }
                return new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filterCard), true, true).apply(game, source);

            }
            return true;
        }
        return false;
    }
}
