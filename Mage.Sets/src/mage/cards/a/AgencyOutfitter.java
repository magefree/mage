package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardAndOrCard;
import mage.target.common.TargetCardAndOrCardInLibrary;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author notgreat
 */
public final class AgencyOutfitter extends CardImpl {

    public AgencyOutfitter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}{U}");
        this.subtype.add(SubType.SPHINX);
        this.subtype.add(SubType.DETECTIVE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Agency Outfitter enters the battlefield, you may search your graveyard, hand, and/or library for a card named Magnifying Glass and/or a card named Thinking Cap and put them onto the battlefield. If you search your library this way, shuffle.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new AgencyOutfitterEffect(), true));
    }

    private AgencyOutfitter(final AgencyOutfitter card) {
        super(card);
    }

    @Override
    public AgencyOutfitter copy() {
        return new AgencyOutfitter(this);
    }
}

class AgencyOutfitterEffect extends OneShotEffect {
    private static final String glassName = "Magnifying Glass";
    private static final String capName = "Thinking Cap";

    AgencyOutfitterEffect() {
        super(Outcome.UnboostCreature);
        this.staticText = "you may search your graveyard, hand, and/or library for a card named Magnifying Glass and/or a card named Thinking Cap and put them onto the battlefield. If you search your library this way, shuffle.";
    }

    private AgencyOutfitterEffect(final AgencyOutfitterEffect effect) {
        super(effect);
    }

    @Override
    public AgencyOutfitterEffect copy() {
        return new AgencyOutfitterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Card glassCard = null;
        Card capCard = null;
        if (controller.chooseUse(Outcome.Neutral, "Search your library?", source, game)) {
            TargetCardAndOrCardInLibrary libraryTarget = new TargetCardAndOrCardInLibrary(glassName, capName);
            if (controller.searchLibrary(libraryTarget, source, game)) {
                for (UUID id : libraryTarget.getTargets()) {
                    Card card = game.getCard(id);
                    if (card != null) {
                        if (CardUtil.haveSameNames(card, glassName, game)) {
                            glassCard = card;
                        } else if (CardUtil.haveSameNames(card, capName, game)) {
                            capCard = card;
                        }
                    }
                }
            }
            controller.shuffleLibrary(source, game);
        }

        if (glassCard == null || capCard == null) {
            FilterCard filter;
            TargetCard target;
            if (glassCard == null && capCard == null) {
                target = new TargetCardAndOrCard(glassName, capName);
                filter = target.getFilter();
            } else {
                String name = (glassCard == null ? glassName : capName);
                filter = new FilterCard();
                filter.add(new NamePredicate(name));
                target = new TargetCard(0, 1, Zone.ALL, filter);
            }
            target.withNotTarget(true);
            Cards cards = new CardsImpl();
            cards.addAllCards(controller.getHand().getCards(filter, source.getControllerId(), source, game));
            cards.addAllCards(controller.getGraveyard().getCards(filter, source.getControllerId(), source, game));
            if (!cards.isEmpty()) {
                controller.choose(outcome, cards, target, source, game);
                for (UUID id : target.getTargets()) {
                    Card card = game.getCard(id);
                    if (card != null) {
                        if (CardUtil.haveSameNames(card, glassName, game)) {
                            glassCard = card;
                        } else if (CardUtil.haveSameNames(card, capName, game)) {
                            capCard = card;
                        }
                    }
                }
            }
        }

        Cards foundCards = new CardsImpl();
        foundCards.add(glassCard);
        foundCards.add(capCard);
        if (!foundCards.isEmpty()) {
            controller.moveCards(foundCards, Zone.BATTLEFIELD, source, game);
        }
        return true;
    }
}
