package mage.cards.r;

import mage.MageIdentifier;
import mage.MageObject;
import mage.MageObjectReference;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageMultiEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionAllEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetAnyTargetAmount;
import mage.util.CardUtil;
import mage.watchers.common.SpellsCastWatcher;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * @author Susucr
 */
public final class RalLeylineProdigy extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("blue permanent other than {this}");
    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter, true);
    private static final Hint hint = new ConditionHint(condition, "you control another blue permanent");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLUE));
        filter.add(AnotherPredicate.instance);
    }

    public RalLeylineProdigy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.RAL);
        this.setStartingLoyalty(2);

        this.color.setBlue(true);
        this.color.setRed(true);
        this.nightCard = true;

        // Ral, Leyline Prodigy enters the battlefield with an additional loyalty counter on him for each instant and sorcery spell you've cast this turn.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.LOYALTY.createInstance(), RalLeylineProdigyValue.instance, false)
                        .setText("with an additional loyalty counter on him for each instant and sorcery spell you've cast this turn")
        ));

        // +1: Until your next turn, instant and sorcery spells you cast cost {1} less to cast.
        this.addAbility(new LoyaltyAbility(new RalLeylineProdigyCostReductionEffect(), 1));

        // -2: Ral deals 2 damage divided as you choose among one or two targets. Draw a card if you control a blue permanent other than Ral.
        Ability ability = new LoyaltyAbility(new DamageMultiEffect(2), -2);
        ability.addTarget(new TargetAnyTargetAmount(2));
        ability.addEffect(new ConditionalOneShotEffect(
                new DrawCardSourceControllerEffect(1),
                condition, "Draw a card if you control a blue permanent other than {this}"
        ));
        ability.addHint(hint);
        this.addAbility(ability);

        // -8: Exile the top eight cards of your library. You may cast instant and sorcery spells from among them this turn without paying their mana costs.
        this.addAbility(new LoyaltyAbility(new RalLeylineProdigyMinusEightEffect(), -8)
                .setIdentifier(MageIdentifier.WithoutPayingManaCostAlternateCast));
    }

    private RalLeylineProdigy(final RalLeylineProdigy card) {
        super(card);
    }

    @Override
    public RalLeylineProdigy copy() {
        return new RalLeylineProdigy(this);
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
        SpellsCostReductionAllEffect effect = new SpellsCostReductionAllEffect(filter, 1);
        effect.setDuration(Duration.UntilYourNextTurn);
        game.addEffect(effect, source);
        return true;
    }
}

enum RalLeylineProdigyValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        SpellsCastWatcher watcher = game.getState().getWatcher(SpellsCastWatcher.class);
        if (watcher == null) {
            return 0;
        }
        return watcher
                .getSpellsCastThisTurn(sourceAbility.getControllerId())
                .stream()
                .filter(Objects::nonNull)
                .filter(spell -> spell.isInstantOrSorcery(game))
                .mapToInt(spell -> 1)
                .sum();
    }

    @Override
    public RalLeylineProdigyValue copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return "instant and sorcery spell you've cast this turn";
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
        UUID exileId = CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter());
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