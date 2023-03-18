package mage.cards.t;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.MiracleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;

/**
 * @author sprangg
 */
public final class TriumphOfSaintKatherine extends CardImpl {

    public TriumphOfSaintKatherine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);
        
        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Praesidium Protectiva — When Triumph of Saint Katherine dies, exile it and the top six cards of your library in a face-down pile.
        // If you do, shuffle that pile and put it back on top of your library.
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new TriumphOfSaintKatherineEffect().setText("shuffle that pile and put it back on top of your library"), false);
        this.addAbility(new DiesSourceTriggeredAbility(new DoWhenCostPaid(
                ability, new TriumphOfSaintKatherineCost(), null, false
        )).withFlavorWord("Praesidium Protectiva"));

        // Miracle {1}{W}
        this.addAbility(new MiracleAbility("{1}{W}"));
    }

    private TriumphOfSaintKatherine(final TriumphOfSaintKatherine card) {
        super(card);
    }

    @Override
    public TriumphOfSaintKatherine copy() {
        return new TriumphOfSaintKatherine(this);
    }
}

class TriumphOfSaintKatherineCost extends CostImpl {
    
    private final int amount = 6;
    private static ExileZone exileZone;
    
    public TriumphOfSaintKatherineCost() {
        this.text = "exile it and the top six cards of your library in a face-down pile";
    }

    private TriumphOfSaintKatherineCost(final TriumphOfSaintKatherineCost cost) {
        super(cost);
    }
    
    public static ExileZone getExileZone() {
        return exileZone;
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Player controller = game.getPlayer(controllerId);
        if (controller == null) {
            return paid;
        }
        Card card = game.getCard(source.getSourceId());
        if (card != null && game.getState().getZone(source.getSourceId()) == Zone.GRAVEYARD) {
            Set<Card> exiledCards = new HashSet<>();
            UUID exileId = UUID.randomUUID();
            exiledCards.add(card);
            for (Card libraryCard : controller.getLibrary().getTopCards(game, amount)) {
                exiledCards.add(libraryCard);
            }
            controller.moveCardsToExile(exiledCards, source, game, false, exileId, "");
            exileZone = game.getExile().getExileZone(exileId);
            if (exileZone != null) {
                for (Card exiledCard : exileZone.getCards(game)) {
                    if (exiledCard != null) {
                        exiledCard.setFaceDown(true, game);
                    }
                }
            }
            paid = true;
        }
        return paid;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        Player controller = game.getPlayer(controllerId);
        if (controller == null) {
            return false;
        }
        return controller.getLibrary().size() >= amount
                && game.getCard(source.getSourceId()) != null
                && game.getState().getZone(source.getSourceId()) == Zone.GRAVEYARD;
    }

    @Override
    public TriumphOfSaintKatherineCost copy() {
        return new TriumphOfSaintKatherineCost(this);
    }
}

class TriumphOfSaintKatherineEffect extends OneShotEffect {

    TriumphOfSaintKatherineEffect() {
        super(Outcome.Benefit);
        this.staticText = "shuffle that pile and put it back on top of your library";
    }

    TriumphOfSaintKatherineEffect(final TriumphOfSaintKatherineEffect effect) {
        super(effect);
    }

    @Override
    public TriumphOfSaintKatherineEffect copy() {
        return new TriumphOfSaintKatherineEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Cards cardsToLibrary = new CardsImpl();
            ExileZone exileZone = TriumphOfSaintKatherineCost.getExileZone();
            if (exileZone != null) {
                for (Card card : exileZone.getCards(game)) {
                    if (card != null) {
                        cardsToLibrary.add(card);
                    }
                }
            }
            return controller.putCardsOnTopOfLibrary(cardsToLibrary, game, source, false);
        }
        return false;
    }
}