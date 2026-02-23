package mage.cards.r;

import mage.MageIdentifier;
import mage.MageObject;
import mage.MageObjectReference;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.common.InstantAndSorceryCastThisTurn;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.abilities.effects.common.DamageMultiEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ExileAndReturnSourceEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.cards.Card;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.common.TargetAnyTargetAmount;
import mage.util.CardUtil;

import java.util.Set;
import java.util.UUID;

/**
 * @author Susucr
 */
public final class RalMonsoonMage extends TransformingDoubleFacedCard {

    private static final FilterCard filter = new FilterInstantOrSorceryCard("Instant and sorcery spells");
    private static final FilterPermanent filterBlue = new FilterPermanent("blue permanent other than {this}");
    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filterBlue, true);
    private static final Hint hint = new ConditionHint(condition, "you control another blue permanent");

    static {
        filterBlue.add(new ColorPredicate(ObjectColor.BLUE));
        filterBlue.add(AnotherPredicate.instance);
    }

    public RalMonsoonMage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.WIZARD}, "{1}{R}",
                "Ral, Leyline Prodigy",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.PLANESWALKER}, new SubType[]{SubType.RAL}, "UR"
        );

        // Ral, Monsoon Mage
        this.getLeftHalfCard().setPT(1, 3);

        // Instant and sorcery spells you cast cost {1} less to cast.
        this.getLeftHalfCard().addAbility(new SimpleStaticAbility(new SpellsCostReductionControllerEffect(filter, 1)));

        // Whenever you cast an instant or sorcery spell during your turn, flip a coin. If you lose the flip, Ral, Monsoon Mage deals 1 damage to you. If you win the flip, you may exile Ral. If you do, return him to the battlefield transformed under his owner control.
        this.getLeftHalfCard().addAbility(new RalMonsoonMageTriggeredAbility()
                            .addHint(InstantAndSorceryCastThisTurn.YOU.getHint()));

        // Ral, Leyline Prodigy
        this.getRightHalfCard().setStartingLoyalty(2);

        // Ral, Leyline Prodigy enters the battlefield with an additional loyalty counter on him for each instant and sorcery spell you've cast this turn.
        this.getRightHalfCard().addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.LOYALTY.createInstance(), InstantAndSorceryCastThisTurn.YOU,
                        false)
                        .setText("with an additional loyalty counter on him for each instant and sorcery spell you've cast this turn"))
                .addHint(InstantAndSorceryCastThisTurn.YOU.getHint())
        );

        // +1: Until your next turn, instant and sorcery spells you cast cost {1} less to cast.
        this.getRightHalfCard().addAbility(new LoyaltyAbility(new RalLeylineProdigyCostReductionEffect(), 1));

        // -2: Ral deals 2 damage divided as you choose among one or two targets. Draw a card if you control a blue permanent other than Ral.
        Ability ability = new LoyaltyAbility(new DamageMultiEffect(), -2);
        ability.addTarget(new TargetAnyTargetAmount(2));
        ability.addEffect(new ConditionalOneShotEffect(
                new DrawCardSourceControllerEffect(1),
                condition, "Draw a card if you control a blue permanent other than {this}"
        ));
        ability.addHint(hint);
        this.getRightHalfCard().addAbility(ability);

        // -8: Exile the top eight cards of your library. You may cast instant and sorcery spells from among them this turn without paying their mana costs.
        this.getRightHalfCard().addAbility(new LoyaltyAbility(new RalLeylineProdigyMinusEightEffect(), -8)
                .setIdentifier(MageIdentifier.WithoutPayingManaCostAlternateCast));
    }

    private RalMonsoonMage(final RalMonsoonMage card) {
        super(card);
    }

    @Override
    public RalMonsoonMage copy() {
        return new RalMonsoonMage(this);
    }
}

class RalMonsoonMageTriggeredAbility extends SpellCastControllerTriggeredAbility {

    RalMonsoonMageTriggeredAbility() {
        super(new RalMonsoonMageEffect(), StaticFilters.FILTER_SPELL_AN_INSTANT_OR_SORCERY, false);
        setTriggerPhrase("Whenever you cast an instant or sorcery spell during your turn, ");
    }

    private RalMonsoonMageTriggeredAbility(final RalMonsoonMageTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public RalMonsoonMageTriggeredAbility copy() {
        return new RalMonsoonMageTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return game.getActivePlayerId().equals(getControllerId()) && super.checkTrigger(event, game);
    }
}

class RalMonsoonMageEffect extends OneShotEffect {

    RalMonsoonMageEffect() {
        super(Outcome.Benefit);
        staticText = "flip a coin. If you lose the flip, {this} deals 1 damage to you. " +
                "If you win the flip, you may exile {this}. If you do, return him to the battlefield transformed under his owner's control";
    }

    private RalMonsoonMageEffect(final RalMonsoonMageEffect effect) {
        super(effect);
    }

    @Override
    public RalMonsoonMageEffect copy() {
        return new RalMonsoonMageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        boolean wonFlip = player.flipCoin(source, game, true);
        if (wonFlip) {
            if (player.chooseUse(outcome, "Exile {this} and return transformed?", source, game)) {
                new ExileAndReturnSourceEffect(PutCards.BATTLEFIELD_TRANSFORMED, Pronoun.HE)
                        .apply(game, source);
            }
        } else {
            new DamageControllerEffect(1)
                    .apply(game, source);
        }
        return true;
    }
}

class RalLeylineProdigyCostReductionEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterInstantOrSorceryCard("instant and sorcery spells");

    RalLeylineProdigyCostReductionEffect() {
        super(Outcome.Benefit);
        this.staticText = "Until your next turn, instant and sorcery spells you cast cost {1} less to cast";
    }

    private RalLeylineProdigyCostReductionEffect(final RalLeylineProdigyCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public RalLeylineProdigyCostReductionEffect copy() {
        return new RalLeylineProdigyCostReductionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        SpellsCostReductionControllerEffect effect = new SpellsCostReductionControllerEffect(filter, 1);
        effect.setDuration(Duration.UntilYourNextTurn);
        game.addEffect(effect, source);
        return true;
    }
}

class RalLeylineProdigyMinusEightEffect extends OneShotEffect {

    RalLeylineProdigyMinusEightEffect() {
        super(Outcome.Benefit);
        staticText = "Exile the top eight cards of your library. "
                + "You may cast instant and sorcery spells from among them this turn without paying their mana costs";
    }

    private RalLeylineProdigyMinusEightEffect(final RalLeylineProdigyMinusEightEffect effect) {
        super(effect);
    }

    @Override
    public RalLeylineProdigyMinusEightEffect copy() {
        return new RalLeylineProdigyMinusEightEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        if (player == null || sourceObject == null) {
            return false;
        }
        Set<Card> cards = player.getLibrary().getTopCards(game, 8);
        UUID exileId = CardUtil.getExileZoneId(game, source.getSourceId(), source.getStackMomentSourceZCC());
        player.moveCardsToExile(cards, source, game, true, exileId, sourceObject.getIdName());
        for (Card card : cards) {
            if (game.getState().getZone(card.getId()) == Zone.EXILED) {
                game.addEffect(new RalLeylineProdigyCastEffect(new MageObjectReference(card, game)), source);
            }
        }
        return true;
    }
}

class RalLeylineProdigyCastEffect extends AsThoughEffectImpl {

    private final MageObjectReference mor;

    public RalLeylineProdigyCastEffect(MageObjectReference mor) {
        super(AsThoughEffectType.CAST_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfTurn, Outcome.Benefit);
        this.mor = mor;
    }

    private RalLeylineProdigyCastEffect(final RalLeylineProdigyCastEffect effect) {
        super(effect);
        this.mor = effect.mor;
    }

    @Override
    public RalLeylineProdigyCastEffect copy() {
        return new RalLeylineProdigyCastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (mor.getCard(game) == null) {
            discard();
            return false;
        }
        Card theCard = game.getCard(objectId);
        if (theCard == null || !theCard.isInstantOrSorcery(game)) {
            return false;
        }
        UUID mainId = theCard.getMainCard().getId(); // for split cards/MDFC/Adventure cards
        if (!source.isControlledBy(affectedControllerId) || !mor.refersTo(mainId, game)) {
            return false;
        }
        allowCardToPlayWithoutMana(mainId, source, affectedControllerId, MageIdentifier.WithoutPayingManaCostAlternateCast, game);
        return true;
    }
}
