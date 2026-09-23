package mage.cards.w;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.dynamicvalue.common.CardTypesInGraveyardCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.hint.HintUtils;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.*;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterPermanentCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInYourGraveyard;
import mage.util.CardUtil;

import java.awt.*;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 *
 * @author Lashed
 */
public final class WinterCynicalOpportunist extends CardImpl {

    public WinterCynicalOpportunist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{G}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Whenever Winter attacks, mill three cards.
        this.addAbility(new AttacksTriggeredAbility(new MillCardsControllerEffect(3), false));

        // Delirium -- At the beginning of your end step, you may exile any number of cards from your graveyard with four or more card types among them. If you do, put a permanent card from among them onto the battlefield with a finality counter on it.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                TargetController.YOU,
                new WinterCynicalOpportunistDeliriumEffect(),
                true)
                .setAbilityWord(AbilityWord.DELIRIUM)
                .withInterveningIf(DeliriumCondition.instance)
                .addHint(CardTypesInGraveyardCount.YOU.getHint()));
    }


    private WinterCynicalOpportunist(final WinterCynicalOpportunist card) {
        super(card);
    }

    @Override
    public WinterCynicalOpportunist copy() {
        return new WinterCynicalOpportunist(this);
    }

}

class WinterCynicalOpportunistDeliriumEffect extends OneShotEffect {

    public WinterCynicalOpportunistDeliriumEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "exile any number of cards from your graveyard. " +
                "If you do, put a permanent card from among them onto the battlefield with a finality counter on it";
    }

    private WinterCynicalOpportunistDeliriumEffect(final WinterCynicalOpportunistDeliriumEffect effect) {
        super(effect);
    }

    @Override
    public WinterCynicalOpportunistDeliriumEffect copy() {
        return new WinterCynicalOpportunistDeliriumEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller == null || sourceObject == null) {
            return false;
        }

        // Let the player select cards to exile
        DeliriumTarget target = new DeliriumTarget();
        if (!target.canChoose(controller.getId(), source, game) ||
                !controller.choose(Outcome.Exile, target, source, game)) {
            return false;
        }

        // Exile the selected cards
        Set<Card> cardsToExile = target.getTargets().stream()
                .map(game::getCard)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        if (cardsToExile.isEmpty()) {
            return false;
        }

        controller.moveCards(cardsToExile, Zone.EXILED, source, game);

        // Filter permanent cards from the exiled cards
        FilterCard filter = new FilterPermanentCard("permanent card from among the exiled cards to put onto the battlefield");
        filter.add(new WinterCynicalOpportunistExiledPredicate(cardsToExile));

        TargetCard targetPermanent = new TargetCard(1, 1, Zone.EXILED, filter);
        Cards permanentCards = new CardsImpl();
        cardsToExile.stream()
                .filter(card -> card.isPermanent(game))
                .forEach(permanentCards::add);
        
        if (controller.choose(Outcome.PutCardInPlay, permanentCards, targetPermanent, source, game)) {

            Card cardToPut = game.getCard(targetPermanent.getFirstTarget());
            if (cardToPut != null) {
                controller.moveCards(cardToPut, Zone.BATTLEFIELD, source, game);

                // Add a finality counter
                Card permanentCard = game.getPermanent(cardToPut.getId());
                if (permanentCard != null) {
                    permanentCard.addCounters(CounterType.FINALITY.createInstance(), source.getControllerId(), source, game);
                }

                return true;
            }
        }

        return false;
    }
}

class DeliriumTarget extends TargetCardInYourGraveyard {

    DeliriumTarget() {
        super(0, Integer.MAX_VALUE, StaticFilters.FILTER_CARD, true);
        this.targetName = "any number of cards from your graveyard";
    }

    private DeliriumTarget(final DeliriumTarget target) {
        super(target);
    }

    @Override
    public DeliriumTarget copy() {
        return new DeliriumTarget(this);
    }

    @Override
    public String getMessage(Game game) {
        String text = "Select " + CardUtil.addArticle(targetName);
        Set<CardType> types = getCardTypes(this.getTargets(), game);
        text += " (selected " + this.getTargets().size() + " cards; card types: ";
        text += HintUtils.prepareText(
                types.size() + " of 4",
                types.size() >= 4 ? Color.GREEN : Color.RED
        );
        text += " [" + types.stream().map(CardType::toString).collect(Collectors.joining(", ")) + "])";
        return text;
    }

    @Override
    public boolean isChosen(Game game) {
        return super.isChosen(game) && getCardTypesCount(this.getTargets(), game) >= 4;
    }

    @Override
    public boolean canChoose(UUID sourceControllerId, Ability source, Game game) {
        if (!super.canChoose(sourceControllerId, source, game)) {
            return false;
        }
        // Check that selecting all the possible cards would have >= 4 different card types
        return getCardTypesCount(this.possibleTargets(sourceControllerId, source, game), game) >= 4;
    }

    private static Set<CardType> getCardTypes(Collection<UUID> cardIds, Game game) {
        return cardIds
                .stream()
                .map(game::getCard)
                .filter(Objects::nonNull)
                .flatMap(c -> c.getCardType(game).stream())
                .collect(Collectors.toSet());
    }

    private static int getCardTypesCount(Collection<UUID> cardIds, Game game) {
        return getCardTypes(cardIds, game).size();
    }
}

class WinterCynicalOpportunistExiledPredicate implements mage.filter.predicate.Predicate<Card> {

    private final Set<Card> exiledCards;

    public WinterCynicalOpportunistExiledPredicate(Set<Card> exiledCards) {
        this.exiledCards = exiledCards;
    }


    @Override
    public boolean apply(Card input, Game game) {
        return exiledCards.contains(input);
    }
}