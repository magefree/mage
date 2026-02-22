package mage.cards.e;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.EntersBattlefieldEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TapSourceEffect;
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
import mage.watchers.common.SpellsCastWatcher;

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
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        Set<UUID> possibleTargets = super.possibleTargets(sourceControllerId, source, game);
        Set<UUID> targetIds = new HashSet<>(this.getTargets());
        if (targetIds.isEmpty()) {
            return possibleTargets;
        }
        Set<CardType> unionTypes = new HashSet<>();
        for (UUID tId : this.getTargets()) {
            Card tCard = game.getCard(tId);
            if (tCard != null) {
                unionTypes.addAll(tCard.getCardType(game));
            }
        }
        return possibleTargets.stream()
                .filter(id -> {
                    if (targetIds.contains(id)) {
                        return true;
                    }
                    Card card = game.getCard(id);
                    if (card == null) {
                        return false;
                    }
                    for (CardType type : card.getCardType(game)) {
                        if (unionTypes.contains(type)) {
                            return true;
                        }
                    }
                    return false;
                })
                .collect(Collectors.toSet());
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
        // this should be returned true, but the invert works.
        // if you note the code logic issue, please speak up.
        for (CardType type : card1.getCardType(game)) {
            if (card2.getCardType(game).contains(type)) {
                return false;
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
        Set<CardType> exiledCardsCardType = new HashSet<>();
        if (mageObject == null) {
            mageObject = game.getObject(source);
        }
        if (controller != null && mageObject != null) {
            Permanent permanent = source.getSourcePermanentIfItStillExists(game);
            if (permanent == null) {
                return false;
            }
            ExileZone exileZone = game.getState()
                    .getExile()
                    .getExileZone(CardUtil
                            .getExileZoneId(game, permanent.getMainCard().getId(), permanent.getMainCard().getZoneChangeCounter(game)));
            if (exileZone == null) {
                return false;
            }
            List<String> sharedCardTypes = new ArrayList<>();
            for (Card card : exileZone.getCards(game)) {
                for (CardType cardType : card.getCardType(game)) {
                    if (!exiledCardsCardType.add(cardType)) {
                        sharedCardTypes.add(cardType.toString());
                    }
                }
            }
            Choice cardTypeChoice = new ChoiceCardType();
            cardTypeChoice.getChoices().clear();
            cardTypeChoice.getChoices().addAll(sharedCardTypes);
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

class ApexObservatoryCastWithoutManaEffect extends ContinuousEffectImpl {

    class ApexObservatoryCondition implements Condition {
        private final int spellCastCount;

        private ApexObservatoryCondition(int spellCastCount) {
            this.spellCastCount = spellCastCount;
        }

        @Override
        public boolean apply(Game game, Ability source) {
            SpellsCastWatcher watcher = game.getState().getWatcher(SpellsCastWatcher.class);
            if (watcher != null) {
                return watcher.getSpellsCastThisTurn(playerId).size() == spellCastCount;
            }
            return false;
        }
    }

    private final FilterCard filter;
    private final String chosenCardType;
    private final UUID playerId;
    private int spellCastCount;
    private AlternativeCostSourceAbility alternativeCostSourceAbility;

    ApexObservatoryCastWithoutManaEffect(String chosenCardType, UUID playerId) {
        super(Duration.EndOfTurn, Layer.RulesEffects, SubLayer.NA, Outcome.PlayForFree);
        this.chosenCardType = chosenCardType;
        this.playerId = playerId;
        this.filter = new FilterCard("spell of the chosen type");
        filter.add(CardType.fromString(chosenCardType).getPredicate());
        staticText = "The next spell you cast this turn of the chosen type can be cast without paying its mana cost";
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        SpellsCastWatcher watcher = game.getState().getWatcher(SpellsCastWatcher.class);
        if (watcher != null) {
            spellCastCount = watcher.getSpellsCastThisTurn(playerId).size();
            Condition condition = new ApexObservatoryCondition(spellCastCount);
            alternativeCostSourceAbility = new AlternativeCostSourceAbility(
                    null, condition, null, filter, true
            );
        }
    }

    private ApexObservatoryCastWithoutManaEffect(final ApexObservatoryCastWithoutManaEffect effect) {
        super(effect);
        this.chosenCardType = effect.chosenCardType;
        this.playerId = effect.playerId;
        this.used = effect.used;
        this.spellCastCount = effect.spellCastCount;
        this.filter = effect.filter;
        this.alternativeCostSourceAbility = effect.alternativeCostSourceAbility;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(playerId);
        if (controller == null) {
            return false;
        }
        alternativeCostSourceAbility.setSourceId(source.getSourceId());
        controller.getAlternativeSourceCosts().add(alternativeCostSourceAbility);
        return true;
    }

    @Override
    public ApexObservatoryCastWithoutManaEffect copy() {
        return new ApexObservatoryCastWithoutManaEffect(this);
    }
}
