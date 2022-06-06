package mage.cards.d;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ChooseACardNameEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author maxlebedev
 */
public final class DiviningWitch extends CardImpl {

    public DiviningWitch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SPELLSHAPER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {1}{B}, {tap}, Discard a card: Name a card. Exile the top six cards of your library. Reveal cards from the top of your library until you reveal the named card, then put that card into your hand. Exile all other cards revealed this way.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DiviningWitchEffect(), new ManaCostsImpl<>("{1}{B}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new DiscardTargetCost(new TargetCardInHand()));
        this.addAbility(ability);
    }

    private DiviningWitch(final DiviningWitch card) {
        super(card);
    }

    @Override
    public DiviningWitch copy() {
        return new DiviningWitch(this);
    }

    private static class DiviningWitchEffect extends OneShotEffect {

        DiviningWitchEffect() {
            super(Outcome.Benefit);
            this.staticText = "choose a card name. Exile the top six cards of your library, " +
                    "then reveal cards from the top of your library until you reveal a card with the chosen name. " +
                    "Put that card into your hand and exile all other cards revealed this way";
        }

        DiviningWitchEffect(final DiviningWitchEffect effect) {
            super(effect);
        }

        @Override
        public DiviningWitchEffect copy() {
            return new DiviningWitchEffect(this);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Player controller = game.getPlayer(source.getControllerId());
            MageObject sourceObject = game.getObject(source);
            if (controller == null || sourceObject == null) {
                return false;
            }
            // Name a card.
            String cardName = ChooseACardNameEffect.TypeOfName.ALL.getChoice(controller, game, source, false);
            // Exile the top six cards of your library,
            controller.moveCards(controller.getLibrary().getTopCards(game, 6), Zone.EXILED, source, game);

            // then reveal cards from the top of your library until you reveal the named card.
            Cards cardsToReveal = new CardsImpl();
            Card cardToHand = null;
            for (Card card : controller.getLibrary().getCards(game)) {
                cardsToReveal.add(card);
                // Put that card into your hand
                if (CardUtil.haveSameNames(card, cardName, game)) {
                    cardToHand = card;
                    break;
                }
            }
            controller.moveCards(cardToHand, Zone.HAND, source, game);
            controller.revealCards(sourceObject.getIdName(), cardsToReveal, game);
            cardsToReveal.remove(cardToHand);
            controller.moveCards(cardsToReveal, Zone.EXILED, source, game);
            return true;
        }
    }
}
