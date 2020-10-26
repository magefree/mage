package mage.cards.b;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class BladegriffPrototype extends CardImpl {

    public BladegriffPrototype(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{5}");

        this.subtype.add(SubType.GRIFFIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Bladegriff Prototype deals combat damage to a player, destroy target nonland permanent of that player's choice that one of your opponents controls.
        this.addAbility(new BladegriffPrototypeAbility());
    }

    private BladegriffPrototype(final BladegriffPrototype card) {
        super(card);
    }

    @Override
    public BladegriffPrototype copy() {
        return new BladegriffPrototype(this);
    }
}

class BladegriffPrototypeAbility extends TriggeredAbilityImpl {

    BladegriffPrototypeAbility() {
        super(Zone.BATTLEFIELD, new DestroyTargetEffect(), false);
    }

    private BladegriffPrototypeAbility(final BladegriffPrototypeAbility ability) {
        super(ability);
    }

    @Override
    public BladegriffPrototypeAbility copy() {
        return new BladegriffPrototypeAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Player player = game.getPlayer(getControllerId());
        if (player == null
                || !event.getSourceId().equals(this.sourceId)
                || !((DamagedEvent) event).isCombatDamage()) {
            return false;
        }
        FilterPermanent filter = new FilterNonlandPermanent(
                "nonland permanent controlled by an opponent of " + player.getName()
        );
        filter.add(Predicates.or(
                game.getOpponents(getControllerId())
                        .stream()
                        .map(ControllerIdPredicate::new)
                        .collect(Collectors.toSet())
        ));
        TargetPermanent target = new TargetPermanent(filter);
        target.setTargetController(event.getPlayerId());
        this.getTargets().clear();
        this.addTarget(target);
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever {this} deals combat damage to a player, " +
                "destroy target nonland permanent of that player's choice " +
                "that one of your opponents controls.";
    }
}
