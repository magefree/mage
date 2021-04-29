package mage.cards.e;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterNonlandCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.ApprovingObject;

/**
 * @author ciaccona007 & L_J
 */
public final class EtaliPrimalStorm extends CardImpl {

    public EtaliPrimalStorm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{R}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELDER);
        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Whenever Etali, Primal Storm attacks, exile the top card of each player's library, 
        // then you may cast any number of nonland cards exiled this way without paying their mana costs.
        this.addAbility(new AttacksTriggeredAbility(new EtaliPrimalStormEffect(), false));

    }

    private EtaliPrimalStorm(final EtaliPrimalStorm card) {
        super(card);
    }

    @Override
    public EtaliPrimalStorm copy() {
        return new EtaliPrimalStorm(this);
    }
}

class EtaliPrimalStormEffect extends OneShotEffect {

    private static final FilterNonlandCard filter = new FilterNonlandCard("nonland cards");

    public EtaliPrimalStormEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "exile the top card of each player's library, then you may cast "
                + "any number of spells from among those cards without paying their mana costs";
    }

    public EtaliPrimalStormEffect(final EtaliPrimalStormEffect effect) {
        super(effect);
    }

    @Override
    public EtaliPrimalStormEffect copy() {
        return new EtaliPrimalStormEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null
                && sourceObject != null) {
            // move cards from library to exile
            Set<Card> currentExiledCards = new HashSet<>();
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    if (!player.getLibrary().getTopCards(game, 1).isEmpty()) {
                        Card topCard = player.getLibrary().getFromTop(game);
                        if (topCard != null) {
                            if (filter.match(topCard, source.getSourceId(), source.getControllerId(), game)) {
                                currentExiledCards.add(topCard);
                            }
                            controller.moveCardsToExile(topCard, source, game, true, source.getSourceId(), sourceObject.getIdName());
                        }
                    }
                }
            }

            // cast the possible cards without paying the mana
            Cards cardsToCast = new CardsImpl();
            cardsToCast.addAll(currentExiledCards);
            boolean alreadyCast = false;
            while (controller.canRespond() && !cardsToCast.isEmpty()) {
                if (!controller.chooseUse(Outcome.PlayForFree, "Cast a"
                        + (alreadyCast ? "nother" : "") + " card exiled with "
                        + sourceObject.getLogName() + " without paying its mana cost?", source, game)) {
                    break;
                }

                TargetCard targetCard = new TargetCard(1, Zone.EXILED, new FilterCard("nonland card to cast for free"));
                if (!controller.choose(Outcome.PlayForFree, cardsToCast, targetCard, game)) {
                    break;
                }

                alreadyCast = true;
                Card card = game.getCard(targetCard.getFirstTarget());
                if (card != null) {
                    game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), Boolean.TRUE);
                    Boolean cardWasCast = controller.cast(controller.chooseAbilityForCast(card, game, true),
                            game, true, new ApprovingObject(source, game));
                    game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), null);
                    if (!cardWasCast) {
                        game.informPlayer(controller, "You're not able to cast "
                                + card.getIdName() + " or you canceled the casting.");
                    }
                    cardsToCast.remove(card);
                }
            }
            return true;
        }
        return false;
    }
}
