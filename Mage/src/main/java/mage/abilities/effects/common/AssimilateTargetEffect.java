package mage.abilities.effects.common;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.AssimilateContinuousEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.Card;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.counters.Counters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.*;

/**
 * 701.72. Assimilate
 * <p>
 * 701.72a To assimilate a permanent, gain control of it and put a +1/+1 counter on it. It becomes
 * a Borg artifact creature.
 * <p>
 * 701.72b To assimilate a permanent card that’s in a zone other than the battlefield, put it onto
 * the battlefield under your control with a +1/+1 counter on it. It’s a Borg artifact creature.
 * <p>
 * 701.72c An object may have an ability printed on it that assimilates another object and another
 * ability that refers to permanents “assimilated with [this object].” These abilities are linked;
 * the second refers only to permanents that have been assimilated due to the first. See rule 607,
 * “Linked Abilities.”
 *
 * @author muz, JayDi85
 */
public class AssimilateTargetEffect extends OneShotEffect {

    private static final String ASSIMILATED_WITH_KEY = "assimilatedWith";

    public AssimilateTargetEffect() {
        super(Outcome.PutCreatureInPlay);
    }

    protected AssimilateTargetEffect(final AssimilateTargetEffect effect) {
        super(effect);
    }

    @Override
    public AssimilateTargetEffect copy() {
        return new AssimilateTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        Set<Permanent> permanentsToAssimilate = new LinkedHashSet<>();
        Set<Card> cardsToMove = new LinkedHashSet<>();
        for (UUID targetId : getTargetPointer().getTargets(game, source)) {
            Permanent permanent = game.getPermanent(targetId);
            if (permanent != null) {
                permanentsToAssimilate.add(permanent);
                continue;
            }
            Card card = game.getCard(targetId);
            if (card != null && game.getState().getZone(card.getId()) != Zone.BATTLEFIELD) {
                cardsToMove.add(card);
            }
        }

        boolean applied = false;
        if (!permanentsToAssimilate.isEmpty()) {
            applied |= assimilatePermanents(controller, permanentsToAssimilate, source, game);
        }
        if (!cardsToMove.isEmpty()) {
            applied |= assimilateCards(controller, cardsToMove, source, game);
        }
        return applied;
    }

    private static String getAssimilatedKey(Game game, Ability source) {
        return CardUtil.getObjectZoneString(
                ASSIMILATED_WITH_KEY,
                source.getSourceId(),
                game,
                CardUtil.getActualSourceObjectZoneChangeCounter(game, source),
                false
        );
    }

    public static boolean assimilatePermanent(Player controller, Permanent permanent, Ability source, Game game) {
        if (controller == null || permanent == null) {
            return false;
        }
        return assimilatePermanents(controller, Collections.singleton(permanent), source, game);
    }

    public static boolean assimilatePermanents(Player controller, Set<Permanent> permanents, Ability source, Game game) {
        if (controller == null || permanents.isEmpty()) {
            return false;
        }

        List<Permanent> toAssimilate = new ArrayList<>();
        for (Permanent permanent : permanents) {
            if (permanent != null && game.getState().getZone(permanent.getId()) == Zone.BATTLEFIELD) {
                toAssimilate.add(permanent);
            }
        }
        if (toAssimilate.isEmpty()) {
            return false;
        }

        for (Permanent permanent : toAssimilate) {
            MageObjectReference mor = new MageObjectReference(permanent, game);
            game.addEffect(
                    new GainControlTargetEffect(Duration.Custom, true)
                            .setTargetPointer(new FixedTarget(permanent, game)),
                    source
            );
            game.addEffect(new AssimilateContinuousEffect(mor), source);
            recordAssimilatedPermanent(game, source, mor);
        }
        game.processAction();

        for (Permanent permanent : toAssimilate) {
            Permanent current = game.getPermanent(permanent.getId());
            if (current != null) {
                current.addCounters(CounterType.P1P1.createInstance(), source, game);
            }
        }
        return true;
    }

    public static boolean assimilateCard(Player controller, Card card, Ability source, Game game) {
        if (controller == null || card == null) {
            return false;
        }
        return assimilateCards(controller, Collections.singleton(card), source, game);
    }

    public static boolean assimilateCards(Player controller, Set<Card> cardsToMove, Ability source, Game game) {
        if (controller == null || cardsToMove.isEmpty()) {
            return false;
        }

        Counters counters = new Counters();
        counters.addCounter(CounterType.P1P1.createInstance());

        for (Card card : cardsToMove) {
            Card enterCard = CardUtil.getDefaultCardSideForBattlefield(game, card);
            game.setEnterWithCounters(enterCard.getId(), counters.copy());

            // Apply the continuous effect before moving the card to have the proper types on entering.
            game.addEffect(new AssimilateContinuousEffect(new MageObjectReference(enterCard, game, 1)), source);
        }

        boolean moved = controller.moveCards(cardsToMove, Zone.BATTLEFIELD, source, game, false, false, false, null);
        for (Card card : cardsToMove) {
            Permanent permanent = CardUtil.getPermanentFromCardPutToBattlefield(card, game);
            if (permanent != null) {
                recordAssimilatedPermanent(game, source, new MageObjectReference(permanent, game));
            }
        }
        return moved;
    }

    public static Set<Permanent> getAssimilatedPermanents(Game game, Ability source) {
        String key = getAssimilatedKey(game, source);
        @SuppressWarnings("unchecked")
        Set<MageObjectReference> refs = (Set<MageObjectReference>) game.getState().getValue(key);
        if (refs == null || refs.isEmpty()) {
            return Collections.emptySet();
        }
        Set<Permanent> result = new LinkedHashSet<>();
        for (MageObjectReference mor : refs) {
            Permanent permanent = mor.getPermanent(game);
            if (permanent != null) {
                result.add(permanent);
            }
        }
        return result;
    }

    private static void recordAssimilatedPermanent(Game game, Ability source, MageObjectReference mor) {
        String key = getAssimilatedKey(game, source);
        @SuppressWarnings("unchecked")
        Set<MageObjectReference> existing = (Set<MageObjectReference>) game.getState().getValue(key);
        Set<MageObjectReference> updated = existing == null ? new LinkedHashSet<>() : new LinkedHashSet<>(existing);
        updated.add(mor);
        game.getState().setValue(key, updated);
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        boolean targetsPermanent = mode.getTargets().stream().anyMatch(TargetPermanent.class::isInstance);
        return "assimilate "
                + getTargetPointer().describeTargets(mode.getTargets(), targetsPermanent ? "that creature" : "that card")
                + (targetsPermanent
                ? ". <i>(Gain control of it and put a +1/+1 counter on it. "
                : ". <i>(Put it onto the battlefield under your control with a +1/+1 counter. ")
                + "It's a Borg artifact creature and loses all other creature types.)</i>";
    }
}
