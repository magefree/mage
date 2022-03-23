package mage.cards.c;

import java.util.Map;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldOrDiesSourceTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.Card;
import mage.constants.*;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.counters.Counter;
import mage.counters.Counters;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetOpponentsCreaturePermanent;

/**
 *
 * @author weirddan455
 */
public final class CemeteryDesecrator extends CardImpl {

    public CemeteryDesecrator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // When Cemetery Desecrator enters the battlefield or dies, exile another card from a graveyard. When you do, choose one —
        // • Remove X counters from target permanent, where X is the mana value of the exiled card.
        // • Target creature an opponent controls gets -X/-X until end of turn, where X is the mana value of the exiled card.
        this.addAbility(new EntersBattlefieldOrDiesSourceTriggeredAbility(new CemeteryDesecratorEffect(), false));
    }

    private CemeteryDesecrator(final CemeteryDesecrator card) {
        super(card);
    }

    @Override
    public CemeteryDesecrator copy() {
        return new CemeteryDesecrator(this);
    }
}

class CemeteryDesecratorEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("another card from a graveyard");
    private static final String triggerText = "choose one &mdash;<br>"
            + "&bull  Remove X counters from target permanent, where X is the mana value of the exiled card.<br>"
            + "&bull  Target creature an opponent controls gets -X/-X until end of turn, where X is the mana value of the exiled card.";

    static {
        filter.add(AnotherPredicate.instance);
    }

    public CemeteryDesecratorEffect() {
        super(Outcome.Exile);
        staticText = "exile another card from a graveyard. When you do, " + triggerText;
    }

    private CemeteryDesecratorEffect(final CemeteryDesecratorEffect effect) {
        super(effect);
    }

    @Override
    public CemeteryDesecratorEffect copy() {
        return new CemeteryDesecratorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            TargetCardInGraveyard target = new TargetCardInGraveyard(filter);
            target.setNotTarget(true);
            controller.choose(outcome, target, source, game);
            Card card = game.getCard(target.getFirstTarget());
            if (card != null) {
                int manaValue = card.getManaValue();
                if (controller.moveCards(card, Zone.EXILED, source, game)) {
                    ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(new CemeteryDesecratorRemoveCountersEffect(manaValue), false, triggerText);
                    ability.addTarget(new TargetPermanent());
                    Mode mode = new Mode(new BoostTargetEffect(-manaValue, -manaValue, Duration.EndOfTurn)
                            .setText("Target creature an opponent controls gets -X/-X until end of turn, where X is the mana value of the exiled card"));
                    mode.addTarget(new TargetOpponentsCreaturePermanent());
                    ability.addMode(mode);
                    game.fireReflexiveTriggeredAbility(ability, source);
                    return true;
                }
            }
        }
        return false;
    }
}

class CemeteryDesecratorRemoveCountersEffect extends OneShotEffect {

    private final int xValue;

    public CemeteryDesecratorRemoveCountersEffect(int xValue) {
        super(Outcome.UnboostCreature);
        this.xValue = xValue;
        staticText = "Remove X counters from target permanent, where X is the mana value of the exiled card";
    }

    private CemeteryDesecratorRemoveCountersEffect(final CemeteryDesecratorRemoveCountersEffect effect) {
        super(effect);
        this.xValue = effect.xValue;
    }

    @Override
    public CemeteryDesecratorRemoveCountersEffect copy() {
        return new CemeteryDesecratorRemoveCountersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (controller == null || permanent == null) {
            return false;
        }
        if (xValue < 1) {
            return false;
        }
        // Make copy of counters to avoid concurrent modification exception
        Counters counters = permanent.getCounters(game).copy();
        int totalCounters = 0;
        for (Counter counter : counters.values()) {
            totalCounters += counter.getCount();
        }
        if (totalCounters == 0) {
            return false;
        }
        if (totalCounters <= xValue) {
            for (Map.Entry<String, Counter> entry : counters.entrySet()) {
                permanent.removeCounters(entry.getKey(), entry.getValue().getCount(), source, game);
            }
            return true;
        }
        if (counters.size() == 1) {
            String counterName = counters.keySet().iterator().next();
            permanent.removeCounters(counterName, xValue, source, game);
            return true;
        }
        int remainingCounters = totalCounters;
        int countersLeftToRemove = xValue;
        for (Map.Entry<String, Counter> entry : counters.entrySet()) {
            String counterName = entry.getKey();
            int numCounters = entry.getValue().getCount();
            remainingCounters -= numCounters;
            int min = Math.max(0, countersLeftToRemove - remainingCounters);
            int max = Math.min(countersLeftToRemove, numCounters);
            int toRemove = controller.getAmount(min, max, counterName + " counters to remove", game);
            // Sanity check in case of GUI bugs/disconnects
            toRemove = Math.max(toRemove, min);
            toRemove = Math.min(toRemove, max);
            permanent.removeCounters(counterName, toRemove, source, game);
            countersLeftToRemove -= toRemove;
            if (countersLeftToRemove <= 0) {
                break;
            }
        }
        return true;
    }
}
