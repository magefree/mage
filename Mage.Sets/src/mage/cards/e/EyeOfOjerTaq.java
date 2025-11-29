package mage.cards.e;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.EntersBattlefieldEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TapSourceEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.keyword.CraftAbility;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.Card;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.choices.Choice;
import mage.choices.ChoiceCardType;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyardBattlefieldOrStack;
import mage.util.CardUtil;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author jeffwadsworth
 */
public final class EyeOfOjerTaq extends TransformingDoubleFacedCard {

    public EyeOfOjerTaq(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{}, new CardType[]{CardType.ARTIFACT}, new SubType[]{}, "{3}",
                "Apex Observatory",
                new SuperType[]{}, new CardType[]{CardType.ARTIFACT}, new SubType[]{}, ""
        );

        // Eye of Ojer Taq
        // {T}: Add one mana of any color.
        this.getLeftHalfCard().addAbility(new AnyColorManaAbility());

        // Craft with two that share a card type {6} ({6}, Exile this artifact, Exile the two from among other permanents you control and/or cards from your graveyard: Return this card transformed under its owner's control. Craft only as a sorcery.)
        this.getLeftHalfCard().addAbility(new CraftAbility(
                "{6}", "two that share a card type", new EyeOfOjerTaqTarget()
        ));

        // Apex Observatory

        // This artifact enters tapped. As it enters, choose a card type shared among two exiled cards used to craft it.
        EntersBattlefieldEffect entersEffect = new EntersBattlefieldEffect(new TapSourceEffect(true)
                .setText("{this} enters tapped"));
        entersEffect.addEffect(new ChooseCardTypeEffect()
                .concatBy("As it enters,"));
        Ability observatoryAbility = new SimpleStaticAbility(entersEffect);
        this.getRightHalfCard().addAbility(observatoryAbility);

        // The next spell you cast this turn of the chosen type can be cast without paying its mana cost.
        this.getRightHalfCard().addAbility(new SimpleActivatedAbility(new ApexObservatoryEffect(), new TapSourceCost()));
    }

    private EyeOfOjerTaq(final EyeOfOjerTaq card) {
        super(card);
    }

    @Override
    public EyeOfOjerTaq copy() {
        return new EyeOfOjerTaq(this);
    }
}

class EyeOfOjerTaqTarget extends TargetCardInGraveyardBattlefieldOrStack {

    private static final FilterCard filterCard = new FilterCard();
    private static final FilterControlledPermanent filterPermanent = new FilterControlledPermanent();

    static {
        filterCard.add(TargetController.YOU.getOwnerPredicate());
        filterPermanent.add(AnotherPredicate.instance);
    }

    EyeOfOjerTaqTarget() {
        super(2, 2, filterCard, filterPermanent);
    }

    private EyeOfOjerTaqTarget(final EyeOfOjerTaqTarget target) {
        super(target);
    }

    @Override
    public EyeOfOjerTaqTarget copy() {
        return new EyeOfOjerTaqTarget(this);
    }

    @Override
    public boolean canTarget(UUID playerId, UUID id, Ability source, Game game) {
        if (!super.canTarget(playerId, id, source, game)) {
            return false;
        }
        Card cardObject = game.getCard(id);
        return cardObject != null
                && this.getTargets()
                .stream()
                .map(game::getCard)
                .noneMatch(c -> sharesCardtype(cardObject, c, game));
    }

    public static boolean sharesCardtype(Card card1, Card card2, Game game) {
        if (card1.getId().equals(card2.getId())) {
            return false;
        }
        for (CardType type : card1.getCardType(game)) {
            if (card2.getCardType(game).contains(type)) {
                return false; // see comment in original code
            }
        }
        return true;
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
        Player controller = game.getPlayer(playerId);
        if (controller != null) {
            MageObject spell = abilityToModify.getSourceObject(game);
            if (spell != null && !game.isSimulation()) {
                String message = "Cast " + spell.getIdName() + " without paying its mana cost?";
                if (controller.chooseUse(Outcome.Benefit, message, source, game)) {
                    abilityToModify.getManaCostsToPay().clear();
                    used = true;
                }
            }
        }
        return true;
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
        return object != null && object.getCardType(game).stream()
                .anyMatch(cardType -> cardType.toString().equals(chosenCardType));
    }

    @Override
    public ApexObservatoryCastWithoutManaEffect copy() {
        return new ApexObservatoryCastWithoutManaEffect(this);
    }
}
