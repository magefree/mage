package mage.cards.b;

import java.util.Set;
import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.PartnerWithAbility;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author jimga150
 */
public final class BlueLoyalRaptor extends CardImpl {

    public BlueLoyalRaptor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{U}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Partner with Owen Grady, Raptor Trainer
        this.addAbility(new PartnerWithAbility("Owen Grady, Raptor Trainer"));

        // For each kind of counter on Blue, Loyal Raptor, each other Dinosaur you control enters the battlefield with a counter of that kind on it.
        this.addAbility(new SimpleStaticAbility(new BlueLoyalRaptorEffect()));

        // If Blue, Loyal Raptor enters the battlefield with one or more kinds of counters on it at the same time as
        // other Dinosaurs you control, those other Dinosaurs won't get additional counters from Blue, Loyal Raptor's
        // last ability. (2023-11-10)
    }

    private BlueLoyalRaptor(final BlueLoyalRaptor card) {
        super(card);
    }

    @Override
    public BlueLoyalRaptor copy() {
        return new BlueLoyalRaptor(this);
    }
}

// Based on Arwen, Weaver of Hope and Bribe Taker
class BlueLoyalRaptorEffect extends ReplacementEffectImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.DINOSAUR, "Dinosaur");

    BlueLoyalRaptorEffect() {
        super(Duration.WhileOnBattlefield, Outcome.BoostCreature);
        staticText = "For each kind of counter on {this}, each other " + filter.getMessage() + " you control enters the battlefield with a counter of that kind on it.";
    }

    private BlueLoyalRaptorEffect(BlueLoyalRaptorEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent creature = ((EntersTheBattlefieldEvent) event).getTarget();
        return creature != null && creature.isControlledBy(source.getControllerId())
                && filter.match(creature, game)
                && !event.getTargetId().equals(source.getSourceId());
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent sourcePermanent = source.getSourcePermanentIfItStillExists(game);
        Permanent targetPermanent = ((EntersTheBattlefieldEvent) event).getTarget();
        if (sourcePermanent == null || targetPermanent == null) {
            return false;
        }
        Set<String> counterTypes = sourcePermanent.getCounters(game).keySet();
        for (String counterType : counterTypes) {
            targetPermanent.addCounters(
                    CounterType.findByName(counterType).createInstance(), source.getControllerId(), source, game);
        }
        return false;
    }

    @Override
    public BlueLoyalRaptorEffect copy() {
        return new BlueLoyalRaptorEffect(this);
    }
}