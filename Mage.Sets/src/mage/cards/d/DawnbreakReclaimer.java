package mage.cards.d;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.card.OwnerIdPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public final class DawnbreakReclaimer extends CardImpl {

    public DawnbreakReclaimer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}{W}");
        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // At the beginning of your end step, choose a creature card in an opponent's graveyard, then that player chooses a creature card in your graveyard.
        // You may return those cards to the battlefield under their owners' control.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new DawnbreakReclaimerEffect(), TargetController.YOU, false));
    }

    private DawnbreakReclaimer(final DawnbreakReclaimer card) {
        super(card);
    }

    @Override
    public DawnbreakReclaimer copy() {
        return new DawnbreakReclaimer(this);
    }
}

class DawnbreakReclaimerEffect extends OneShotEffect {

    public DawnbreakReclaimerEffect() {
        super(Outcome.Detriment);
        this.staticText = "choose a creature card in an opponent's graveyard, then that player chooses a creature card in your graveyard. You may return those cards to the battlefield under their owners' control";
    }

    public DawnbreakReclaimerEffect(final DawnbreakReclaimerEffect effect) {
        super(effect);
    }

    @Override
    public DawnbreakReclaimerEffect copy() {
        return new DawnbreakReclaimerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        /**
         * 04.11.2015 If any opponent has a creature card in their graveyard as
         * Dawnbreak Reclaimer's ability resolves, then you must choose one of
         * those cards. You can't choose a different opponent with no creature
         * cards in their graveyard to avoid returning one of those cards.
         *
         * 04.11.2015 If there are no creature cards in any opponent's graveyard
         * as Dawnbreak Reclaimer's ability resolves, you'll still have the
         * option to return a creature card from your graveyard to the
         * battlefield. You choose which opponent will choose a creature card in
         * your graveyard.
         */
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null
                && sourceObject != null) {
            FilterCreatureCard filter = new FilterCreatureCard("a creature card in an opponent's graveyard");
            filter.add(TargetController.OPPONENT.getOwnerPredicate());
            TargetCard chosenCreatureOpponentGraveyard = new TargetCard(Zone.GRAVEYARD, filter);
            Player opponent = null;
            Card cardOpponentGraveyard = null;
            chosenCreatureOpponentGraveyard.setNotTarget(true);
            if (chosenCreatureOpponentGraveyard.canChoose(source.getControllerId(), source, game)) {
                controller.choose(Outcome.Detriment, chosenCreatureOpponentGraveyard, source, game);
                cardOpponentGraveyard = game.getCard(chosenCreatureOpponentGraveyard.getFirstTarget());
                if (cardOpponentGraveyard != null) {
                    opponent = game.getPlayer(cardOpponentGraveyard.getOwnerId());
                    game.informPlayers(sourceObject.getLogName() 
                            + ": " + controller.getLogName() 
                            + " has chosen " 
                            + cardOpponentGraveyard.getIdName() 
                            + " of " + opponent.getLogName());
                }
            }
            if (opponent == null) {
                // if no card from opponent was available controller has to chose an opponent to select a creature card in controllers graveyard
                TargetOpponent targetOpponent = new TargetOpponent(true);
                controller.choose(outcome, targetOpponent, source, game);
                opponent = game.getPlayer(targetOpponent.getFirstTarget());
                if (opponent != null) {
                    game.informPlayers(sourceObject.getLogName() 
                            + ": " + controller.getLogName() 
                            + " has chosen " 
                            + opponent.getLogName() 
                            + " to select a creature card from their graveyard");
                }
            }
            if (opponent != null) {
                FilterCreatureCard filterCreatureCard = 
                        new FilterCreatureCard("a creature card in " + controller.getName() + "'s the graveyard");
                filterCreatureCard.add(new OwnerIdPredicate(controller.getId()));
                TargetCardInGraveyard targetControllerGaveyard = new TargetCardInGraveyard(filterCreatureCard);
                targetControllerGaveyard.setNotTarget(true);
                Card controllerCreatureCard = null;
                if (targetControllerGaveyard.canChoose(opponent.getId(), source, game)
                        && opponent.choose(outcome, targetControllerGaveyard, source, game)) {
                    controllerCreatureCard = game.getCard(targetControllerGaveyard.getFirstTarget());
                    if (controllerCreatureCard != null) {
                        game.informPlayers(sourceObject.getLogName() 
                                + ": " + opponent.getLogName() 
                                + " has chosen " 
                                + controllerCreatureCard.getIdName() 
                                + " of " + controller.getLogName());
                    }
                }
                Set<Card> cards = new HashSet<>();
                if (cardOpponentGraveyard != null) {
                    cards.add(cardOpponentGraveyard);
                }
                if (controllerCreatureCard != null) {
                    cards.add(controllerCreatureCard);
                }
                if (!cards.isEmpty()) {
                    if (controller.chooseUse(
                            outcome,
                            "Return those cards to the battlefield under their owners' control?",
                            "Opponent's creature card: " 
                                    + (cardOpponentGraveyard == null 
                                            ? "none" : cardOpponentGraveyard.getLogName()) 
                                    + ", your creature card: " + (controllerCreatureCard == null 
                                            ? "none" : controllerCreatureCard.getLogName()),
                            null,
                            null,
                            source,
                            game)) {
                        controller.moveCards(cards, Zone.BATTLEFIELD, source, game, false, false, true, null);
                    }
                }
            }

            return true;
        }
        return false;

    }
}
