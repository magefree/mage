package mage.cards.m;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.cards.*;
import mage.constants.*;
import mage.game.Game;
import mage.game.command.emblems.MordenkainenEmblem;
import mage.game.permanent.token.DogIllusionToken;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author weirddan455
 */
public final class Mordenkainen extends CardImpl {

    public Mordenkainen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{4}{U}{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.MORDENKAINEN);
        this.setStartingLoyalty(5);

        // +2: Draw two cards, then put a card from your hand on the bottom of your library.
        this.addAbility(new LoyaltyAbility(new MordenkainenDrawEffect(), 2));

        // −2: Create a blue Dog Illusion creature token with "This creature's power and toughness are each equal to twice the number of cards in your hand."
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new DogIllusionToken()), -2));

        // −10: Exchange your hand and library, then shuffle. You get an emblem with "You have no maximum hand size."
        Ability ability = new LoyaltyAbility(new MordenkainenExchangeEffect(), -10);
        ability.addEffect(new GetEmblemEffect(new MordenkainenEmblem()));
        this.addAbility(ability);
    }

    private Mordenkainen(final Mordenkainen card) {
        super(card);
    }

    @Override
    public Mordenkainen copy() {
        return new Mordenkainen(this);
    }
}

class MordenkainenDrawEffect extends OneShotEffect {

    public MordenkainenDrawEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Draw two cards, then put a card from your hand on the bottom of your library";
    }

    private MordenkainenDrawEffect(final MordenkainenDrawEffect effect) {
        super(effect);
    }

    @Override
    public MordenkainenDrawEffect copy() {
        return new MordenkainenDrawEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        controller.drawCards(2, source, game);
        TargetCardInHand target = new TargetCardInHand();
        if (controller.chooseTarget(Outcome.Discard, target, source, game)) {
            Card card = game.getCard(target.getFirstTarget());
            if (card != null) {
                controller.moveCardToLibraryWithInfo(card, source, game, Zone.HAND, false, false);
            }
        }
        return true;
    }
}

class MordenkainenExchangeEffect extends OneShotEffect {

    public MordenkainenExchangeEffect() {
        super(Outcome.Benefit);
        this.staticText = "Exchange your hand and library, then shuffle";
    }

    private MordenkainenExchangeEffect(final MordenkainenExchangeEffect effect) {
        super(effect);
    }

    @Override
    public MordenkainenExchangeEffect copy() {
        return new MordenkainenExchangeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Cards hand = new CardsImpl(controller.getHand());
        Cards library = new CardsImpl(controller.getLibrary().getCardList());
        controller.putCardsOnTopOfLibrary(hand, game, source, false);
        controller.moveCards(library, Zone.HAND, source, game);
        controller.shuffleLibrary(source, game);
        return true;
    }
}
