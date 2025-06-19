package mage.cards.a;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceCardType;
import mage.constants.*;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.*;
import java.util.stream.Collectors;
import mage.abilities.SpellAbility;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;

/**
 * @author jeffwadsworth
 */
public class ApexObservatory extends CardImpl {

    public ApexObservatory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, null);

        this.nightCard = true;

        // Apex Observatory enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // As it enters, choose a card type shared among two exiled cards used to craft it.
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
            // chase the exile zone down...
            ExileZone exileZone = game.getState().getExile().getExileZone(CardUtil.getExileZoneId(game, source, game.getState().getZoneChangeCounter(mageObject.getId()) - 1));
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
            // handle situations like the double-faced instant/land Jwari Disruption // Jwari Ruins
            if (sharedCardTypes.isEmpty()) {
                game.informPlayers(mageObject.getIdName() + " No exiled cards shared a type in exile, so nothing is done.");
                if (mageObject instanceof Permanent) {
                    ((Permanent) mageObject).addInfo("chosen type", CardUtil.addToolTipMarkTags("No exiled cards have the same card type."), game);
                }
                return false;
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

class ApexObservatoryEffect extends OneShotEffect {

    ApexObservatoryEffect() {
        super(Outcome.Benefit);
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
        String chosenCardType = (String) game.getState().getValue("ApexObservatoryType_" + source.getSourceId().toString());
        if (chosenCardType == null) {
            return false;
        }
        game.addEffect(new ApexObservatoryCastWithoutManaEffect(chosenCardType, source.getControllerId()), source);
        return true;
    }
}

class ApexObservatoryCastWithoutManaEffect extends CostModificationEffectImpl {

    private final String chosenCardType;
    private final UUID playerId;
    private boolean used = false;

    ApexObservatoryCastWithoutManaEffect(String chosenCardType, UUID playerId) {
        super(Duration.EndOfTurn, Outcome.Benefit, CostModificationType.SET_COST);
        this.chosenCardType = chosenCardType;
        this.playerId = playerId;
        staticText = "The next spell you cast this turn of the chosen type can be cast without paying its mana cost";
    }

    private ApexObservatoryCastWithoutManaEffect(final ApexObservatoryCastWithoutManaEffect effect) {
        super(effect);
        this.chosenCardType = effect.chosenCardType;
        this.playerId = effect.playerId;
        this.used = effect.used;
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        // Ask the player if they want to use the effect
        Player controller = game.getPlayer(playerId);
        if (controller != null) {
            MageObject spell = abilityToModify.getSourceObject(game);
            if (spell != null && !game.isSimulation()) {
                String message = "Cast " + spell.getIdName() + " without paying its mana cost?";
                if (controller.chooseUse(Outcome.Benefit, message, source, game)) {
                    // Set the cost to zero
                    abilityToModify.getManaCostsToPay().clear();
                    // Mark as used
                    used = true;
                    game.informPlayers(controller.getLogName() + " casts " + spell.getIdName() + " without paying its mana cost.");
                    return true;
                } else {
                    // Player chose not to use the effect
                    return false;
                }
            }
        }
        return false;
    }

    @Override
    public ApexObservatoryCastWithoutManaEffect copy() {
        return new ApexObservatoryCastWithoutManaEffect(this);
    }

    @Override
    public boolean isInactive(Ability source, Game game) {
        return used || super.isInactive(source, game);
    }

    @Override
    public boolean applies(Ability ability, Ability source, Game game) {
        if (used) {
            return false;
        }
        if (!ability.isControlledBy(playerId)) {
            return false;
        }
        if (!(ability instanceof SpellAbility)) {
            return false;
        }
        MageObject object = game.getObject(ability.getSourceId());
        if (object != null && object.getCardType(game).stream()
                .anyMatch(cardType -> cardType.toString().equals(chosenCardType))) {
            return true;
        }
        return false;
    }
}
