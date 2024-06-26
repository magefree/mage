package mage.cards.a;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AddContinuousEffectToGame;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceCardType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.filter.common.FilterOwnedCard;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author jeffwadsworth
 */
public class ApexObservatory extends CardImpl {

    public ApexObservatory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, null);

        this.nightCard = true;

        // Apex Observatory enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // Apex Observatory enters the battlefield tapped. As it enters, choose a card type shared among two exiled cards used to craft it.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseCardTypeEffect()));

        // The next spell you cast this turn of the chosen type can be cast without paying its mana cost.
        this.addAbility(new SimpleActivatedAbility(new AddContinuousEffectToGame(new ApexObservatoryEffect()), new TapSourceCost()));
    }

    private ApexObservatory(final ApexObservatory card) {
        super(card);
    }

    @Override
    public ApexObservatory copy() {
        return new ApexObservatory(this);
    }
}

class ChooseCardTypeEffect extends OneShotEffect {

    public ChooseCardTypeEffect() {
        super(Outcome.Neutral);
        staticText = "choose a card type shared among two exiled cards used to craft it.";
    }

    protected ChooseCardTypeEffect(final ChooseCardTypeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject mageObject = game.getPermanentEntering(source.getSourceId());
        List<CardType> exiledCardsCardType = new ArrayList<>();
        if (mageObject == null) {
            mageObject = game.getObject(source);
        }
        if (controller != null && mageObject != null) {
            Permanent permanent = source.getSourcePermanentIfItStillExists(game);
            if (permanent == null) {
                return false;
            }
            ExileZone exileZone = (ExileZone) game.getExile().getExileZone(CardUtil.getExileZoneId(game, source, +1));
            if (exileZone == null) {
                return false;
            }
            for (Card card : exileZone.getCards(game)) {
                exiledCardsCardType.addAll(card.getCardType(game));
            }
            Choice cardTypeChoice = new ChoiceCardType();
            cardTypeChoice.getChoices().clear();
            cardTypeChoice.getChoices().addAll(exiledCardsCardType.stream().map(CardType::toString).collect(Collectors.toList()));
            // find only card types that each card shares; some cards have more than 1 card type
            Map<String, Integer> cardTypeCounts = new HashMap<>();
            for (String cardType : cardTypeChoice.getChoices()) {
                cardTypeCounts.put(cardType, 0);
            }

            for (Card c : exileZone.getCards(game)) {
                for (CardType cardType : c.getCardType(game)) {
                    if (cardTypeCounts.containsKey(cardType.toString())) {
                        cardTypeCounts.put(cardType.toString(), cardTypeCounts.get(cardType.toString()) + 1);
                    }
                }
            }

            List<String> sharedCardTypes = new ArrayList<>();
            int numExiledCards = exileZone.getCards(game).size();
            for (Map.Entry<String, Integer> entry : cardTypeCounts.entrySet()) {
                if (entry.getValue() == numExiledCards) {
                    sharedCardTypes.add(entry.getKey());
                }
            }

            cardTypeChoice.getChoices().retainAll(sharedCardTypes);
            if (controller.choose(Outcome.Benefit, cardTypeChoice, game)) {
                if (!game.isSimulation()) {
                    game.informPlayers(mageObject.getName() + ": " + controller.getLogName() + " has chosen " + cardTypeChoice.getChoice());
                }
                game.getState().setValue("ApexObservatoryType_" + source.getSourceId().toString(), cardTypeChoice.getChoice());
                if (mageObject instanceof Permanent) {
                    ((Permanent) mageObject).addInfo("chosen type", CardUtil.addToolTipMarkTags("Chosen card type: " + cardTypeChoice.getChoice()), game);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public ChooseCardTypeEffect copy() {
        return new ChooseCardTypeEffect(this);
    }
}

class ApexObservatoryEffect extends ContinuousEffectImpl {

    ApexObservatoryEffect() {
        super(Duration.EndOfTurn, Outcome.Benefit);
        staticText = "The next spell you cast this turn of the chosen type can be cast without paying its mana cost.";
    }

    private ApexObservatoryEffect(final ApexObservatoryEffect effect) {
        super(effect);
    }

    @Override
    public ApexObservatoryEffect copy() {
        return new ApexObservatoryEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        String chosenCardType = (String) game.getState().getValue("ApexObservatoryType_" + source.getSourceId().toString());
        if (controller != null
                && chosenCardType != null) {
            Card apexObservatory = game.getCard(source.getSourceId());
            if (apexObservatory != null) {
                Boolean wasItUsed = (Boolean) game.getState().getValue(
                        apexObservatory.getId().toString());
                if (wasItUsed == null) {
                    ApexObservatoryAlternativeCostAbility alternateCostAbility = new ApexObservatoryAlternativeCostAbility(chosenCardType);
                    alternateCostAbility.setSourceId(source.getSourceId());
                    controller.getAlternativeSourceCosts().add(alternateCostAbility);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.RulesEffects;
    }
}

class ApexObservatoryAlternativeCostAbility extends AlternativeCostSourceAbility {

    private boolean wasActivated;

    ApexObservatoryAlternativeCostAbility(String chosenCardType) {
        super(new SpellMatchesChosenTypeCondition(chosenCardType), null, new FilterOwnedCard(), true, null);
    }

    private ApexObservatoryAlternativeCostAbility(final ApexObservatoryAlternativeCostAbility ability) {
        super(ability);
        this.wasActivated = ability.wasActivated;
    }

    @Override
    public ApexObservatoryAlternativeCostAbility copy() {
        return new ApexObservatoryAlternativeCostAbility(this);
    }

    @Override
    public boolean askToActivateAlternativeCosts(Ability ability, Game game) {
        Player controller = game.getPlayer(ability.getControllerId());
        Card apexObservatory = game.getCard(this.getSourceId());
        if (controller != null
                && apexObservatory != null) {
            if (controller.chooseUse(Outcome.Benefit, "Use "
                    + apexObservatory.getLogName() + " to pay no mana costs for this spell?", ability, game)) {
                wasActivated = super.askToActivateAlternativeCosts(ability, game);
                if (wasActivated) {
                    game.getState().setValue(apexObservatory.getId().toString(), true);
                }
            }
        }
        return wasActivated;
    }
}

class SpellMatchesChosenTypeCondition implements Condition {

    final private String chosenCardType;

    public SpellMatchesChosenTypeCondition(String chosenCardType) {
        this.chosenCardType = chosenCardType;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return checkSpell(game.getObject(source), game, chosenCardType);
    }

    public static boolean checkSpell(MageObject spell, Game game, String chosenCardType) {
        if (spell instanceof Card) {
            Card card = (Card) spell;
            return chosenCardType != null
                    && card.getCardType(game).toString().contains(chosenCardType);
        }
        return false;
    }
}
