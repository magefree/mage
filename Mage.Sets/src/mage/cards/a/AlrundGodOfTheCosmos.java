package mage.cards.a;

import java.util.Collection;
import java.util.Set;
import mage.MageInt;
import mage.cards.CardSetInfo;
import mage.cards.ModalDoubleFacesCard;
import mage.constants.*;
import mage.game.Game;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfYourEndStepTriggeredAbility;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ChooseCardTypeEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ForetellAbility;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.filter.predicate.card.OwnerIdPredicate;
import mage.game.ExileZone;
import mage.players.Player;
import mage.util.CardUtil;

/**
 * @author jeffwadsworth
 */
public final class AlrundGodOfTheCosmos extends ModalDoubleFacesCard {

    public AlrundGodOfTheCosmos(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.GOD}, "{3}{U}{U}",
                "Hakka, Whispering Raven", new CardType[]{CardType.CREATURE}, new SubType[]{SubType.BIRD}, "{1}{U}"
        );

        // 1.
        // Alrund, God of the Cosmos
        // Legendary Creature - God
        this.getLeftHalfCard().addSuperType(SuperType.LEGENDARY);
        this.getLeftHalfCard().setPT(new MageInt(1), new MageInt(1));

        // Alrund gets +1/+1 for each card in your hand and each foretold card you own in exile.
        Effect effect = new BoostSourceEffect(AlrundGodOfTheCosmosValue.instance, AlrundGodOfTheCosmosValue.instance, Duration.EndOfGame);
        effect.setText("{this} gets +1/+1 for each card in your hand and each foretold card you own in exile.");
        Ability ability = new SimpleStaticAbility(effect);
        this.getLeftHalfCard().addAbility(ability);

        //  At the beginning of your end step, choose a card type, then reveal the top two cards of your library. Put all cards of the chosen type into your hand and the rest on the bottom of your library in any order.
        Ability ability2 = new BeginningOfYourEndStepTriggeredAbility(new ChooseCardTypeEffect(Outcome.Neutral), false);
        ability2.addEffect(new AlrundGodOfTheCosmosEffect());
        this.getLeftHalfCard().addAbility(ability2);

        // 2.
        // Hakka, Whispering Raven
        // Legendary Creature — Bird 
        this.getRightHalfCard().addSuperType(SuperType.LEGENDARY);
        this.getRightHalfCard().setPT(new MageInt(2), new MageInt(3));

        // Flying
        this.getRightHalfCard().addAbility(FlyingAbility.getInstance());

        // Whenever Hakka, Whispering Raven deals combat damage to a player, return it to its owner’s hand, then scry 2.
        Ability ability3 = new DealsCombatDamageToAPlayerTriggeredAbility(new ReturnToHandSourceEffect().setText("return it to its owner's hand"), false);
        ability3.addEffect(new ScryEffect(2).concatBy(", then"));
        this.getRightHalfCard().addAbility(ability3);

    }

    private AlrundGodOfTheCosmos(final AlrundGodOfTheCosmos card) {
        super(card);
    }

    @Override
    public AlrundGodOfTheCosmos copy() {
        return new AlrundGodOfTheCosmos(this);
    }
}

class AlrundGodOfTheCosmosEffect extends OneShotEffect {

    public AlrundGodOfTheCosmosEffect() {
        super(Outcome.Neutral);
        staticText = ", then reveal the top two cards of your library. Put all cards of the chosen type revealed this way into your hand and the rest on the bottom of your library in any order";
    }

    public AlrundGodOfTheCosmosEffect(final AlrundGodOfTheCosmosEffect effect) {
        super(effect);
    }

    @Override
    public AlrundGodOfTheCosmosEffect copy() {
        return new AlrundGodOfTheCosmosEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        String chosenCardType = (String) game.getState().getValue(source.getSourceId() + "_type");
        Cards cardsToHand = new CardsImpl();
        Cards cardsToBottomOfLibrary = new CardsImpl();
        if (controller != null) {
            Set<Card> twoCardsFromTop = controller.getLibrary().getTopCards(game, 2);
            Cards cards = new CardsImpl();
            cards.addAll(twoCardsFromTop);
            controller.revealCards(source, cards, game);
            for (Card card : cards.getCards(game)) {
                if (card.getCardType(game).toString().contains(chosenCardType)) {
                    cardsToHand.add(card);
                } else {
                    cardsToBottomOfLibrary.add(card);
                }
            }
            controller.moveCards(cardsToHand, Zone.HAND, source, game);
            controller.putCardsOnBottomOfLibrary(cardsToBottomOfLibrary, game, source, true);
            return true;
        }
        return false;
    }
}

enum AlrundGodOfTheCosmosValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Player controller = game.getPlayer(sourceAbility.getControllerId());
        if (controller != null) {
            Collection<ExileZone> exileZones = game.getState().getExile().getExileZones();
            Cards cardsForetoldInExileZones = new CardsImpl();
            FilterCard filter = new FilterCard();
            filter.add(new OwnerIdPredicate(controller.getId()));
            filter.add(new AbilityPredicate(ForetellAbility.class));
            for (ExileZone exile : exileZones) {
                for (Card card : exile.getCards(filter, game)) {
                    // verify that the card is actually Foretold
                    UUID exileId = CardUtil.getExileZoneId(card.getId().toString() + "foretellAbility", game);
                    if (exileId != null) {
                        if (game.getState().getExile().getExileZone(exileId) != null) {
                            cardsForetoldInExileZones.add(card);
                        }
                    }
                }
            }
            return controller.getHand().size() + cardsForetoldInExileZones.size();
        }
        return 0;
    }

    @Override
    public DynamicValue copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return "";
    }
}
