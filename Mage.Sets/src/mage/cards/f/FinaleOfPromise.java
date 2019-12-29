package mage.cards.f;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetadjustment.TargetAdjuster;
import mage.target.targetpointer.FixedTarget;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author JayDi85
 */
public final class FinaleOfPromise extends CardImpl {

    static final FilterCard filterInstant = new FilterCard("instant card from your graveyard");
    static final FilterCard filterSorcery = new FilterCard("sorcery card from your graveyard");

    static {
        filterInstant.add(new CardTypePredicate(CardType.INSTANT));
        filterSorcery.add(new CardTypePredicate(CardType.SORCERY));
    }

    public FinaleOfPromise(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{R}{R}");

        // You may cast up to one target instant card and/or up to one target sorcery card from your graveyard
        // each with converted mana cost X or less without paying their mana costs.
        // If a card cast this way would be put into your graveyard this turn, exile it instead.
        // If X is 10 or more, copy each of those spells twice. You may choose new targets for the copies.
        this.getSpellAbility().addEffect(new FinaleOfPromiseEffect());
        this.getSpellAbility().setTargetAdjuster(FinaleOfPromiseAdjuster.instance);
    }

    public FinaleOfPromise(final FinaleOfPromise card) {
        super(card);
    }

    @Override
    public FinaleOfPromise copy() {
        return new FinaleOfPromise(this);
    }
}

enum FinaleOfPromiseAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();

        int xValue = ManacostVariableValue.instance.calculate(game, ability, null);

        // <= must be replaced to &#60;= for html view
        FilterCard filter1 = FinaleOfPromise.filterInstant.copy();
        filter1.setMessage("up to one INSTANT card from your graveyard with CMC &#60;= " + xValue + " (target 1 of 2)");
        filter1.add(new ConvertedManaCostPredicate(ComparisonType.FEWER_THAN, xValue + 1));
        ability.addTarget(new TargetCardInYourGraveyard(0, 1, filter1));

        FilterCard filter2 = FinaleOfPromise.filterSorcery.copy();
        filter2.setMessage("up to one SORCERY card from your graveyard with CMC &#60;=" + xValue + " (target 2 of 2)");
        filter2.add(new ConvertedManaCostPredicate(ComparisonType.FEWER_THAN, xValue + 1));
        ability.addTarget(new TargetCardInYourGraveyard(0, 1, filter2));
    }
}

class FinaleOfPromiseEffect extends OneShotEffect {

    public FinaleOfPromiseEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "You may cast up to one target instant card and/or up to one target sorcery card from your graveyard "
                + "each with converted mana cost X or less without paying their mana costs. If a card cast this way would "
                + "be put into your graveyard this turn, exile it instead. If X is 10 or more, copy each of those spells "
                + "twice. You may choose new targets for the copies.";
    }

    public FinaleOfPromiseEffect(final FinaleOfPromiseEffect effect) {
        super(effect);
    }

    @Override
    public FinaleOfPromiseEffect copy() {
        return new FinaleOfPromiseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        // split card can be targeted two time -- but can cast only one
        List<UUID> cardsToCast = new ArrayList<>();
        for (Target target : source.getTargets()) {
            for (UUID id : target.getTargets()) {
                if (id != null && !cardsToCast.contains(id)) {
                    cardsToCast.add(id);
                }
            }
        }

        // ask to cast order
        if (!cardsToCast.isEmpty()) {
            String cardsOrder = cardsToCast.stream()
                    .map(game::getCard)
                    .filter(Objects::nonNull)
                    .map(Card::getName)
                    .collect(Collectors.joining(" -> "));
            if (!controller.chooseUse(Outcome.Detriment, "Cast cards by choose order: " + cardsOrder + "?", "Finale of Promise",
                    "Use that order", "Reverse", source, game)) {
                Collections.reverse(cardsToCast);
            }
        }

        // free cast + replace effect
        for (UUID id : cardsToCast) {
            Card card = game.getCard(id);
            if (card != null) {
                controller.cast(card.getSpellAbility(), game, true, new MageObjectReference(source.getSourceObject(game), game));
                ContinuousEffect effect = new FinaleOfPromiseReplacementEffect();
                effect.setTargetPointer(new FixedTarget(card.getId(), game.getState().getZoneChangeCounter(card.getId())));
                game.addEffect(effect, source);
            }
        }

        // If X is 10 or more, copy each of those spells twice. You may choose new targets for the copies
        int xValue = ManacostVariableValue.instance.calculate(game, source, null);
        if (xValue >= 10) {
            for (UUID id : cardsToCast) {
                Card card = game.getCard(id);
                if (card != null) {
                    Spell spell = game.getStack().getSpell(card.getId());
                    if (spell != null) {
                        spell.createCopyOnStack(game, source, controller.getId(), true);
                        spell.createCopyOnStack(game, source, controller.getId(), true);
                        game.informPlayers(controller.getLogName() + " copies " + spell.getName() + " twice.");
                    }
                }
            }
        }

        return true;
    }
}

class FinaleOfPromiseReplacementEffect extends ReplacementEffectImpl {

    public FinaleOfPromiseReplacementEffect() {
        super(Duration.EndOfTurn, Outcome.Exile);
        staticText = "If a card cast this way would be put into your graveyard this turn, exile it instead";
    }

    public FinaleOfPromiseReplacementEffect(final FinaleOfPromiseReplacementEffect effect) {
        super(effect);
    }

    @Override
    public FinaleOfPromiseReplacementEffect copy() {
        return new FinaleOfPromiseReplacementEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card card = game.getCard(getTargetPointer().getFirst(game, source));
            if (card != null) {
                card.moveToExile(null, "", source.getSourceId(), game);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        return zEvent.getToZone() == Zone.GRAVEYARD && event.getTargetId().equals(getTargetPointer().getFirst(game, source));
    }
}
