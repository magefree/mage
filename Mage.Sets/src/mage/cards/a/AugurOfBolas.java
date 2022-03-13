package mage.cards.a;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

import java.util.Set;
import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class AugurOfBolas extends CardImpl {

    private static final FilterCard filter = new FilterCard("an instant or sorcery card");

    static {
        filter.add(Predicates.or(CardType.INSTANT.getPredicate(), CardType.SORCERY.getPredicate()));
    }

    public AugurOfBolas(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // When Augur of Bolas enters the battlefield, look at the top three cards of your library. You may reveal an instant or sorcery card from among them and put it into your hand. Put the rest on the bottom of your library in any order.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new AugurOfBolasEffect()));
    }

    private AugurOfBolas(final AugurOfBolas card) {
        super(card);
    }

    @Override
    public AugurOfBolas copy() {
        return new AugurOfBolas(this);
    }
}

class AugurOfBolasEffect extends OneShotEffect {

    public AugurOfBolasEffect() {
        super(Outcome.DrawCard);
        this.staticText = "look at the top three cards of your library. You may reveal an instant or sorcery card from among them and put it into your hand. Put the rest on the bottom of your library in any order";
    }

    public AugurOfBolasEffect(final AugurOfBolasEffect effect) {
        super(effect);
    }

    @Override
    public AugurOfBolasEffect copy() {
        return new AugurOfBolasEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        if (controller != null && sourceObject != null) {
            Cards topCards = new CardsImpl();
            topCards.addAll(controller.getLibrary().getTopCards(game, 3));
            if (!topCards.isEmpty()) {
                controller.lookAtCards(sourceObject.getIdName(), topCards, game);
                int number = topCards.count(new FilterInstantOrSorceryCard(), source.getControllerId(), source, game);
                if (number > 0) {
                    if (controller.chooseUse(outcome, "Reveal an instant or sorcery card from the looked at cards and put it into your hand?", source, game)) {
                        Card card = null;
                        if (number == 1) {
                            Set<Card> cards = topCards.getCards(new FilterInstantOrSorceryCard(), source.getControllerId(), source, game);
                            if (!cards.isEmpty()) {
                                card = cards.iterator().next();
                            }
                        } else {
                            TargetCard target = new TargetCard(Zone.LIBRARY, new FilterInstantOrSorceryCard());
                            controller.chooseTarget(outcome, topCards, target, source, game);
                            card = topCards.get(target.getFirstTarget(), game);
                        }
                        if (card != null) {
                            controller.moveCards(card, Zone.HAND, source, game);
                            controller.revealCards(sourceObject.getIdName(), new CardsImpl(card), game);
                            topCards.remove(card);
                        }
                    }
                }
                controller.putCardsOnBottomOfLibrary(topCards, game, source, true);
            }
            return true;
        }
        return false;
    }
}
