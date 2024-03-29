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
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.AbstractMap;
import java.util.UUID;
import java.util.function.Function;

/**
 *
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

//Based on Boonweaver Giant / Dark Supplicant / Gate to the Afterlife / Mishra, Artificer Prodigy
class AgencyOutfitterEffect extends OneShotEffect {

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


        Cards cards = new CardsImpl();
        AbstractMap.SimpleImmutableEntry<Card, Boolean> card1 = searchEverywhereForCard("Magnifying Glass", controller, source, game);
        cards.add(card1.getKey());
        AbstractMap.SimpleImmutableEntry<Card, Boolean> card2 = searchEverywhereForCard("Thinking Cap", controller, source, game);
        cards.add(card2.getKey());
        if (!cards.isEmpty()) {
            controller.moveCards(cards, Zone.BATTLEFIELD, source, game);
        }
        if (card1.getValue() || card2.getValue()) {
            controller.shuffleLibrary(source, game);
        }
        return true;
    }

    //Uses AbstractMap.SimpleImmutableEntry as a generic Pair class
    //Create a local lambda function to reduce code duplication
    private AbstractMap.SimpleImmutableEntry<Card, Boolean> searchEverywhereForCard (String name, Player controller, Ability source, Game game) {
        FilterCard filter = new FilterCard(name);
        filter.add(new NamePredicate(name));
        Card card = null;
        boolean searchedLibrary = false;

        // Choose card from graveyard
        if (controller.chooseUse(Outcome.Neutral, "Search your graveyard for "+name+"?", source, game)) {
            TargetCardInYourGraveyard target = new TargetCardInYourGraveyard(filter);
            if (controller.choose(Outcome.PutCardInPlay, controller.getGraveyard(), target, source, game)) {
                card = game.getCard(target.getFirstTarget());
            }
        }

        // Choose card from your hand
        if (card == null && controller.chooseUse(Outcome.Neutral, "Search your hand for "+name+"?", source, game)) {
            TargetCardInHand target = new TargetCardInHand(filter);
            if (controller.choose(Outcome.PutCardInPlay, controller.getHand(), target, source, game)) {
                card = game.getCard(target.getFirstTarget());
            }
        }

        // Choose a card from your library
        if (card == null && controller.chooseUse(Outcome.Neutral, "Search your library for "+name+"?", source, game)) {
            TargetCardInLibrary target = new TargetCardInLibrary(filter);
            if (controller.searchLibrary(target, source, game)) {
                card = game.getCard(target.getFirstTarget());
            }
            searchedLibrary = true;
        }
        return new AbstractMap.SimpleImmutableEntry<>(card, searchedLibrary);
    };
}
