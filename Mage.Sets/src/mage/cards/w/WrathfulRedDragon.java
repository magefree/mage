package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
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

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WrathfulRedDragon extends CardImpl {

    public WrathfulRedDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");

        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever a Dragon you control is dealt damage, it deals that much damage to any target that isn't a Dragon.
        this.addAbility(new WrathfulRedDragonTriggeredAbility());
    }

    private WrathfulRedDragon(final WrathfulRedDragon card) {
        super(card);
    }

    @Override
    public WrathfulRedDragon copy() {
        return new WrathfulRedDragon(this);
    }
}

class WrathfulRedDragonTriggeredAbility extends TriggeredAbilityImpl {

    private static final FilterPermanentOrPlayer filter
            = new FilterAnyTarget("any target that isn't a Dragon");

    static {
        filter.getPermanentFilter().add(Predicates.not(SubType.DRAGON.getPredicate()));
    }

    WrathfulRedDragonTriggeredAbility() {
        super(Zone.BATTLEFIELD, new WrathfulRedDragonEffect());
        this.addTarget(new TargetPermanentOrPlayer(filter));
    }

    private WrathfulRedDragonTriggeredAbility(final WrathfulRedDragonTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public WrathfulRedDragonTriggeredAbility copy() {
        return new WrathfulRedDragonTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent dragon = game.getPermanent(event.getTargetId());
        int damage = event.getAmount();
        if (dragon == null || damage < 1
                || !dragon.isControlledBy(getControllerId())
                || !dragon.hasSubtype(SubType.DRAGON, game)) {
            return false;
        }
        this.getEffects().setValue("damagedPermanent", dragon);
        this.getEffects().setValue("damage", damage);
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever a Dragon you control is dealt damage, " +
                "it deals that much damage to any target that isn't a Dragon.";
    }
}

class WrathfulRedDragonEffect extends OneShotEffect {

    WrathfulRedDragonEffect() {
        super(Outcome.Benefit);
    }

    private WrathfulRedDragonEffect(final WrathfulRedDragonEffect effect) {
        super(effect);
    }

    @Override
    public WrathfulRedDragonEffect copy() {
        return new WrathfulRedDragonEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent dragon = (Permanent) getValue("damagedPermanent");
        Integer damage = (Integer) getValue("damage");
        if (dragon == null || damage == null) {
            return false;
        }
        UUID targetId = getTargetPointer().getFirst(game, source);
        Permanent permanent = game.getPermanent(targetId);
        if (permanent != null) {
            return permanent.damage(damage, dragon.getId(), source, game) > 0;
        }
        Player player = game.getPlayer(targetId);
        if (player != null) {
            return player.damage(damage, dragon.getId(), source, game) > 0;
        }
        return false;
    }
}
