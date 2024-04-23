package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.BatchTriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterAnyTarget;
import mage.filter.common.FilterPermanentOrPlayer;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.events.DamagedBatchForPermanentsEvent;
import mage.game.events.DamagedPermanentEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetPermanentOrPlayer;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * @author arcox
 */
public final class WrathfulRaptors extends CardImpl {

    public WrathfulRaptors(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever a Dinosaur you control is dealt damage, it deals that much damage to any target that isn't a Dinosaur.
        this.addAbility(new WrathfulRaptorsTriggeredAbility());
    }

    private WrathfulRaptors(final WrathfulRaptors card) {
        super(card);
    }

    @Override
    public WrathfulRaptors copy() {
        return new WrathfulRaptors(this);
    }
}

class WrathfulRaptorsTriggeredAbility extends TriggeredAbilityImpl implements BatchTriggeredAbility<DamagedPermanentEvent> {

    private static final FilterPermanentOrPlayer filter
            = new FilterAnyTarget("any target that isn't a Dinosaur");

    static {
        filter.getPermanentFilter().add(Predicates.not(SubType.DINOSAUR.getPredicate()));
    }

    WrathfulRaptorsTriggeredAbility() {
        super(Zone.BATTLEFIELD, new WrathfulRaptorsEffect());
        this.addTarget(new TargetPermanentOrPlayer(filter));
    }

    private WrathfulRaptorsTriggeredAbility(final WrathfulRaptorsTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public WrathfulRaptorsTriggeredAbility copy() {
        return new WrathfulRaptorsTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_BATCH_FOR_ONE_PERMANENT;
    }

    @Override
    public Stream<DamagedPermanentEvent> filterBatchEvent(GameEvent event, Game game) {
        return ((DamagedBatchForPermanentsEvent) event)
                .getEvents()
                .stream()
                .filter(e -> isControlledBy(game.getControllerId(e.getTargetId())))
                .filter(e -> Optional
                        .of(e)
                        .map(DamagedPermanentEvent::getTargetId)
                        .map(game::getPermanentOrLKIBattlefield)
                        .filter(p -> p.hasSubtype(SubType.DINOSAUR, game))
                        .isPresent())
                .filter(e -> e.getAmount() > 0);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent dinosaur = game.getPermanentOrLKIBattlefield(event.getTargetId());
        int amount = filterBatchEvent(event, game)
                .mapToInt(GameEvent::getAmount)
                .sum();
        if (dinosaur == null || amount <= 0) {
            return false;
        }
        this.getEffects().setValue("damagedPermanent", dinosaur);
        this.getEffects().setValue("damage", amount);
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever a Dinosaur you control is dealt damage, " +
                "it deals that much damage to any target that isn't a Dinosaur.";
    }
}

class WrathfulRaptorsEffect extends OneShotEffect {

    WrathfulRaptorsEffect() {
        super(Outcome.Damage);
    }

    private WrathfulRaptorsEffect(final WrathfulRaptorsEffect effect) {
        super(effect);
    }

    @Override
    public WrathfulRaptorsEffect copy() {
        return new WrathfulRaptorsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent dinosaur = (Permanent) getValue("damagedPermanent");
        Integer damage = SavedDamageValue.MUCH.calculate(game, source, this);
        if (dinosaur == null || damage == null) {
            return false;
        }
        UUID targetId = getTargetPointer().getFirst(game, source);
        Permanent permanent = game.getPermanent(targetId);
        if (permanent != null) {
            return permanent.damage(damage, dinosaur.getId(), source, game) > 0;
        }
        Player player = game.getPlayer(targetId);
        if (player != null) {
            return player.damage(damage, dinosaur.getId(), source, game) > 0;
        }
        return false;
    }
}
