package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
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
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetPermanentOrPlayer;

/**
 * @author
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

class WrathfulRaptorsTriggeredAbility extends TriggeredAbilityImpl {

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
        return event.getType() == GameEvent.EventType.DAMAGED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent dinosaur = game.getPermanent(event.getTargetId());
        int damage = event.getAmount();
        if (dinosaur == null || damage < 1
                || !dinosaur.isControlledBy(getControllerId())
                || !dinosaur.hasSubtype(SubType.DINOSAUR, game)) {
            return false;
        }
        this.getEffects().setValue("damagedPermanent", dinosaur);
        this.getEffects().setValue("damage", damage);
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
        super(Outcome.Benefit);
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
        Integer damage = (Integer) getValue("damage");
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
