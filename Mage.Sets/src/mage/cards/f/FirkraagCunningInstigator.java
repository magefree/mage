package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.combat.GoadTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.events.DefenderAttackedEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.Collection;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FirkraagCunningInstigator extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(FirkraagCunningInstigatorPredicate.instance);
    }

    public FirkraagCunningInstigator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever one or more Dragons you control attack an opponent, goad target creature that player controls.
        this.addAbility(new FirkraagCunningInstigatorTriggeredAbility());

        // Whenever a creature deals combat damage to one of your opponents, if that creature had to attack this combat, you put a +1/+1 counter on Firkraag, Cunning Instigator and you draw a card.
        Ability ability = new DealsDamageToAPlayerAllTriggeredAbility(
                Zone.BATTLEFIELD,
                new AddCountersSourceEffect(CounterType.P1P1.createInstance())
                        .setText("you put a +1/+1 counter on {this}"),
                filter, false, SetTargetPointer.NONE,
                true, false, TargetController.OPPONENT
        ).setTriggerPhrase("Whenever a creature deals combat damage to one of your opponents, " +
                "if that creature had to attack this combat, ");
        ability.addEffect(new DrawCardSourceControllerEffect(1).concatBy("and you"));
        this.addAbility(ability);
    }

    private FirkraagCunningInstigator(final FirkraagCunningInstigator card) {
        super(card);
    }

    @Override
    public FirkraagCunningInstigator copy() {
        return new FirkraagCunningInstigator(this);
    }
}

enum FirkraagCunningInstigatorPredicate implements Predicate<Permanent> {
    instance;

    @Override
    public boolean apply(Permanent input, Game game) {
        return game
                .getCombat()
                .getCreaturesForcedToAttack()
                .values()
                .stream()
                .flatMap(Collection::stream)
                .anyMatch(input.getId()::equals);
    }
}

class FirkraagCunningInstigatorTriggeredAbility extends TriggeredAbilityImpl {

    FirkraagCunningInstigatorTriggeredAbility() {
        super(Zone.BATTLEFIELD, new GoadTargetEffect());
    }

    private FirkraagCunningInstigatorTriggeredAbility(final FirkraagCunningInstigatorTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public FirkraagCunningInstigatorTriggeredAbility copy() {
        return new FirkraagCunningInstigatorTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DEFENDER_ATTACKED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (this.isControlledBy(event.getPlayerId())
                && game.getOpponents(this.getControllerId()).contains(event.getTargetId())
                && ((DefenderAttackedEvent) event)
                .getAttackers(game)
                .stream()
                .anyMatch(permanent -> permanent.hasSubtype(SubType.DRAGON, game))) {
            this.getTargets().clear();
            FilterPermanent filter = new FilterCreaturePermanent(
                    "creature controlled by " + game.getPlayer(event.getTargetId()).getName()
            );
            filter.add(new ControllerIdPredicate(event.getTargetId()));
            this.addTarget(new TargetPermanent(filter));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever one or more Dragons you control attack an opponent, goad target creature that player controls.";
    }
}
