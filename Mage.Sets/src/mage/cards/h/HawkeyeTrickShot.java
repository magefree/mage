package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;

/**
 *
 * @author muz
 */
public final class HawkeyeTrickShot extends CardImpl {

    public HawkeyeTrickShot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARCHER);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Whenever Hawkeye or another Hero you control enters, it deals damage equal to the number of Heroes you control to any target.
        Ability ability = new HawkeyeTrickShotTriggeredAbility();
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private HawkeyeTrickShot(final HawkeyeTrickShot card) {
        super(card);
    }

    @Override
    public HawkeyeTrickShot copy() {
        return new HawkeyeTrickShot(this);
    }
}

class HawkeyeTrickShotTriggeredAbility extends TriggeredAbilityImpl {

    public HawkeyeTrickShotTriggeredAbility() {
        super(Zone.BATTLEFIELD, new HawkeyeTrickShotEffect(), false);
    }

    private HawkeyeTrickShotTriggeredAbility(final HawkeyeTrickShotTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent != null
                && permanent.isCreature(game)
                && permanent.isControlledBy(this.controllerId)
                && permanent.hasSubtype(SubType.HERO, game)) {
            Effect effect = this.getEffects().get(0);
            effect.setValue("damageSource", event.getTargetId());
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever Hawkeye or another Hero you control enters, it deals damage equal to the number of Heroes you control to any target.";
    }

    @Override
    public HawkeyeTrickShotTriggeredAbility copy() {
        return new HawkeyeTrickShotTriggeredAbility(this);
    }
}

class HawkeyeTrickShotEffect extends OneShotEffect {

    HawkeyeTrickShotEffect() {
        super(Outcome.Damage);
        staticText = "it deals damage equal to the number of Heroes you control to any target";
    }

    private HawkeyeTrickShotEffect(final HawkeyeTrickShotEffect effect) {
        super(effect);
    }

    @Override
    public HawkeyeTrickShotEffect copy() {
        return new HawkeyeTrickShotEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID creatureId = (UUID) getValue("damageSource");
        Permanent creature = game.getPermanentOrLKIBattlefield(creatureId);
        if (creature != null) {
            int amount = new PermanentsOnBattlefieldCount(new FilterControlledPermanent(SubType.HERO)).calculate(game, source, this);
            UUID target = source.getTargets().getFirstTarget();
            Permanent targetCreature = game.getPermanent(target);
            if (targetCreature != null) {
                targetCreature.damage(amount, creature.getId(), source, game, false, true);
                return true;
            }
            Player player = game.getPlayer(target);
            if (player != null) {
                player.damage(amount, creature.getId(), source, game);
                return true;
            }
        }
        return false;
    }
}
