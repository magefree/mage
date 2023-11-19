package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.MayCastTargetThenExileEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
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
        filterInstant.add(CardType.INSTANT.getPredicate());
        filterSorcery.add(CardType.SORCERY.getPredicate());
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

    private FinaleOfPromise(final FinaleOfPromise card) {
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

        int xValue = ManacostVariableValue.REGULAR.calculate(game, ability, null);

        // <= must be replaced to &#60;= for html view
        FilterCard filter1 = FinaleOfPromise.filterInstant.copy();
        filter1.setMessage("up to one INSTANT card from your graveyard with mana value &#60;= " + xValue + " (target 1 of 2)");
        filter1.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, xValue + 1));
        ability.addTarget(new TargetCardInYourGraveyard(0, 1, filter1));

        FilterCard filter2 = FinaleOfPromise.filterSorcery.copy();
        filter2.setMessage("up to one SORCERY card from your graveyard with mana value &#60;=" + xValue + " (target 2 of 2)");
        filter2.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, xValue + 1));
        ability.addTarget(new TargetCardInYourGraveyard(0, 1, filter2));
    }
}

class FinaleOfPromiseEffect extends OneShotEffect {

    public FinaleOfPromiseEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "You may cast up to one target instant card and/or up to one target sorcery card from your graveyard "
                + "each with mana value X or less without paying their mana costs. If a card cast this way would "
                + "be put into your graveyard this turn, exile it instead. If X is 10 or more, copy each of those spells "
                + "twice. You may choose new targets for the copies.";
    }

    private FinaleOfPromiseEffect(final FinaleOfPromiseEffect effect) {
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
        if (cardsToCast.size() > 1) {
            String cardsOrder = cardsToCast.stream()
                    .map(game::getCard)
                    .filter(Objects::nonNull)
                    .map(Card::getName)
                    .collect(Collectors.joining(" -> "));
            if (!controller.chooseUse(Outcome.Detriment, "Cast cards by choose order: "
                            + cardsOrder + "?", "Finale of Promise",
                    "Use that order", "Reverse", source, game)) {
                Collections.reverse(cardsToCast);
            }
        }

        // free cast + replace effect
        for (UUID id : cardsToCast) {
            Card card = game.getCard(id);
            if (card != null) {
                new MayCastTargetThenExileEffect(true).setTargetPointer(new FixedTarget(card, game)).apply(game, source);
            }
        }

        // If X is 10 or more, copy each of those spells twice. You may choose new targets for the copies
        int xValue = ManacostVariableValue.REGULAR.calculate(game, source, null);
        if (xValue >= 10) {
            for (UUID id : cardsToCast) {
                Card card = game.getCard(id);
                if (card != null) {
                    Spell spell = game.getStack().getSpell(card.getId());
                    if (spell != null) {
                        spell.createCopyOnStack(game, source, controller.getId(), true, 2);
                    }
                }
            }
        }

        return true;
    }
}
