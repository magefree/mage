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
import mage.abilities.costs.DynamicCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
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
import mage.filter.FilterCard;
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
        this.addAbility(new SimpleActivatedAbility(new ApexObservatoryEffect(), new TapSourceCost()));
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
                // non-unique identifier for now until global one is setup.  source won't work for the conditional check
                game.getState().setValue("ApexObservatory" + source.getControllerId().toString(), cardTypeChoice.getChoice());
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

    private static final FilterCard filter = new FilterCard("a spell");
    private final AlternativeCostSourceAbility alternativeCastingCostAbility = new ApexObservatoryAlternativeCostAbility(
            new SpellMatchesChosenTypeCondition(), null, filter, true, null
    );

    public ApexObservatoryEffect() {
        super(Duration.EndOfTurn, Layer.RulesEffects, SubLayer.NA, Outcome.Neutral);
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
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        controller.getAlternativeSourceCosts().add(alternativeCastingCostAbility);
        return true;
    }
}

class ApexObservatoryAlternativeCostAbility extends AlternativeCostSourceAbility {

    public ApexObservatoryAlternativeCostAbility(Condition condition, String rule, FilterCard filter, boolean onlyMana, DynamicCost dynamicCost) {
        super(condition, rule, filter, onlyMana, dynamicCost);
    }

    @Override
    public boolean isAvailable(Ability source, Game game) {
        return super.isAvailable(source, game) && !AlternativeCostSourceAbility.getActivatedStatus(game, source, getOriginalId(), false);
    }

    @Override
    public ApexObservatoryAlternativeCostAbility copy() {
        return new ApexObservatoryAlternativeCostAbility(condition, rule, filter, onlyMana, dynamicCost);
    }
}

class SpellMatchesChosenTypeCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        // investigating a global unique identifier for situations like this; source doesn't refer to the main card
        return checkSpell(game.getObject(source), game, "ApexObservatory" + source.getControllerId().toString());
    }

    public static boolean checkSpell(MageObject spell, Game game, String key) {
        if (spell instanceof Card) {
            Card card = (Card) spell;
            String chosenType = (String) game.getState().getValue(key);
            return chosenType != null && card.getCardType(game).toString().contains(chosenType);
        }
        return false;
    }
}
