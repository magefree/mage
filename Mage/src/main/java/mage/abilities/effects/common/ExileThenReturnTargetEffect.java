package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.PutCards;
import mage.constants.Zone;
import mage.counters.Counter;
import mage.counters.Counters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTargets;
import mage.util.CardUtil;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author xenohedron
 */
public class ExileThenReturnTargetEffect extends OneShotEffect {

    private final boolean yourControl;
    private final boolean textThatCard;
    private final PutCards putCards;
    private Counters enterWithCounters = null;
    private String enterWithCountersText = null;
    private OneShotEffect afterEffect = null;

    public ExileThenReturnTargetEffect(boolean yourControl, boolean textThatCard) {
        this(yourControl, textThatCard, PutCards.BATTLEFIELD);
    }

    public ExileThenReturnTargetEffect(boolean yourControl, boolean textThatCard, PutCards putCards) {
        super(Outcome.Benefit);
        this.yourControl = yourControl;
        this.textThatCard = textThatCard;
        this.putCards = putCards;
    }

    protected ExileThenReturnTargetEffect(final ExileThenReturnTargetEffect effect) {
        super(effect);
        this.putCards = effect.putCards;
        this.yourControl = effect.yourControl;
        this.textThatCard = effect.textThatCard;
        this.enterWithCounters = effect.enterWithCounters == null ? null : effect.enterWithCounters.copy();
        this.enterWithCountersText = effect.enterWithCountersText;
        this.afterEffect = effect.afterEffect == null ? null : effect.afterEffect.copy();
    }

    @Override
    public ExileThenReturnTargetEffect copy() {
        return new ExileThenReturnTargetEffect(this);
    }

    public ExileThenReturnTargetEffect withAfterEffect(OneShotEffect afterEffect) {
        this.afterEffect = afterEffect;
        return this;
    }

    public ExileThenReturnTargetEffect withEnterWithCounters(Counter... counters) {
        if (counters == null || counters.length == 0) {
            return this;
        }
        this.enterWithCounters = new Counters();
        for (Counter counter : counters) {
            this.enterWithCounters.addCounter(counter.copy());
        }
        this.enterWithCountersText = makeEnterWithCountersText(counters);
        return this;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Set<Permanent> toFlicker = getTargetPointer().getTargets(game, source)
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        if (toFlicker.isEmpty()) {
            return false;
        }
        controller.moveCards(toFlicker, Zone.EXILED, source, game);
        game.processAction();
        for (Card card : CardUtil.getAllCardsFromPermanentsLeftBattlefield(toFlicker, game)) {
            if (enterWithCounters != null) {
                game.setEnterWithCounters(card.getId(), enterWithCounters.copy());
            }
            putCards.moveCard(
                    yourControl ? controller : game.getPlayer(card.getOwnerId()),
                    card.getMainCard(), source, game, "card");
        }
        if (afterEffect != null) {
            afterEffect.setTargetPointer(new FixedTargets(toFlicker, game));
            afterEffect.apply(game, source);
        }
        return true;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder("exile ");
        sb.append(getTargetPointer().describeTargets(mode.getTargets(), "that permanent"));
        sb.append(", then return ");
        if (getTargetPointer().isPlural(mode.getTargets())) {
            sb.append(textThatCard ? "those cards " : "them ");
            sb.append(putCards.getMessage(false, false).replace("onto", "to"));
            sb.append(" under ");
            sb.append(this.yourControl ? "your" : "their owner's");
        } else {
            sb.append(textThatCard ? "that card " : "it ");
            sb.append(putCards.getMessage(false, false).replace("onto", "to"));
            sb.append(" under ");
            sb.append(this.yourControl ? "your" : "its owner's");
        }
        sb.append(" control");
        if (enterWithCountersText != null) {
            sb.append(enterWithCountersText);
            sb.append(getTargetPointer().isPlural(mode.getTargets()) ? " on them" : " on it");
        }
        if (afterEffect != null) {
            sb.append(". ").append(CardUtil.getTextWithFirstCharUpperCase(afterEffect.getText(mode)));
        }
        return sb.toString();
    }

    private static String makeEnterWithCountersText(Counter... counters) {
        List<String> descriptions = new ArrayList<>();
        for (Counter counter : counters) {
            descriptions.add(counter.getDescription());
        }
        return " with " + CardUtil.concatWithAnd(descriptions);
    }

}
