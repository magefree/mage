package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.ChoosePlaneswalkerTypeEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterPlaneswalkerPermanent;
import mage.filter.predicate.mageobject.ChosenPlaneswalkerTypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.RemoveCountersEvent;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author jimga150
 */
public final class Deification extends CardImpl {

    private static final FilterPlaneswalkerPermanent filter = new FilterPlaneswalkerPermanent("planeswalkers you control of the chosen type");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(ChosenPlaneswalkerTypePredicate.TRUE);
    }

    public Deification(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");


        // As Deification enters the battlefield, choose a planeswalker type.
        this.addAbility(new AsEntersBattlefieldAbility(new ChoosePlaneswalkerTypeEffect(Outcome.AddAbility)));

        // Planeswalkers you control of the chosen type have hexproof.
        this.addAbility(new SimpleStaticAbility(new GainAbilityAllEffect(HexproofAbility.getInstance(), Duration.WhileOnBattlefield, filter)));

        // As long as you control a creature, if damage dealt to a planeswalker you control of the chosen type would result in all loyalty counters on it being removed, instead all but one of those counters are removed.
        addAbility(new SimpleStaticAbility(new DeificationReplacementEffect()));
    }

    private Deification(final Deification card) {
        super(card);
    }

    @Override
    public Deification copy() {
        return new Deification(this);
    }
}

// Based on SerraTheBenevolentEmblemEffect
class DeificationReplacementEffect extends ReplacementEffectImpl {

    DeificationReplacementEffect() {
        super(Duration.Custom, Outcome.Benefit);
        staticText = "As long as you control a creature, if damage dealt to a planeswalker you control of the chosen " +
                "type would result in all loyalty counters on it being removed, instead all but one of those " +
                "counters are removed.";
    }

    private DeificationReplacementEffect(final DeificationReplacementEffect effect) {
        super(effect);
    }

    @Override
    public DeificationReplacementEffect copy() {
        return new DeificationReplacementEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.REMOVE_COUNTERS;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {

        RemoveCountersEvent rEvent = (RemoveCountersEvent) event;

        Permanent planeswalker = game.getPermanent(rEvent.getTargetId());
        if (planeswalker == null) {
            return false;
        }
        if (!source.isControlledBy(planeswalker.getControllerId())
                || 0 == game.getBattlefield().count(StaticFilters.FILTER_CONTROLLED_CREATURE, planeswalker.getControllerId(), source, game)) {
            // only protects your planeswalkers, and only if you control a creature.
            return false;
        }
        if (!planeswalker.hasSubtype(ChoosePlaneswalkerTypeEffect.getChosenPlaneswalkerType(source.getSourceId(), game), game)) {
            // only protects the planeswalkers of the chosen type.
            return false;
        }
        if (!rEvent.counterRemovedDueToDamage()) {
            // not due to damage, prevention does not occur
            return false;
        }

        int loyaltyCounters = planeswalker.getCounters(game).getCount(CounterType.LOYALTY);
        return (loyaltyCounters - event.getAmount()) < 1;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent planeswalker = game.getPermanentOrLKIBattlefield(event.getTargetId());
        if (planeswalker == null) {
            return false;
        }
        int loyaltyCounters = planeswalker.getCounters(game).getCount(CounterType.LOYALTY);
        event.setAmount(loyaltyCounters - 1);
        return false;
    }
}
