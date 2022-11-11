package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.SharesCardTypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author spjspj
 */
public final class MartyrsBond extends CardImpl {

    public MartyrsBond(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{W}{W}");

        // Whenever Martyr's Bond or another nonland permanent you control is put into a graveyard from the battlefield, each opponent sacrifices a permanent that shares a card type with it.
        this.addAbility(new MartyrsBondTriggeredAbility());
    }

    private MartyrsBond(final MartyrsBond card) {
        super(card);
    }

    @Override
    public MartyrsBond copy() {
        return new MartyrsBond(this);
    }
}

class MartyrsBondTriggeredAbility extends TriggeredAbilityImpl {

    public MartyrsBondTriggeredAbility() {
        super(Zone.BATTLEFIELD, new MartyrsBondEffect());
    }

    public MartyrsBondTriggeredAbility(final MartyrsBondTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public MartyrsBondTriggeredAbility copy() {
        return new MartyrsBondTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (((ZoneChangeEvent) event).getToZone() == Zone.GRAVEYARD
                && ((ZoneChangeEvent) event).getFromZone() == Zone.BATTLEFIELD) {
            Permanent permanent = (Permanent) game.getLastKnownInformation(event.getTargetId(), Zone.BATTLEFIELD);
            if (permanent != null
                    && permanent.isControlledBy(this.getControllerId())
                    && !permanent.isLand(game)) {
                for (Effect effect : this.getEffects()) {
                    effect.setTargetPointer(new FixedTarget(permanent, game));
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} or another nonland permanent you control is put into a graveyard from the battlefield, each opponent sacrifices a permanent that shares a card type with it.";
    }

}

class MartyrsBondEffect extends OneShotEffect {

    public MartyrsBondEffect() {
        super(Outcome.Sacrifice);
        this.staticText = "each opponent sacrifices a permanent that shares a card type with it";
    }

    public MartyrsBondEffect(final MartyrsBondEffect effect) {
        super(effect);
    }

    @Override
    public MartyrsBondEffect copy() {
        return new MartyrsBondEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<UUID> perms = new ArrayList<>();
        if (source != null) {
            Permanent saccedPermanent = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
            Player controller = game.getPlayer(source.getControllerId());
            if (controller != null && saccedPermanent != null) {
                SharesCardTypePredicate predicate = new SharesCardTypePredicate(saccedPermanent.getCardType(game));
                FilterControlledPermanent filter = new FilterControlledPermanent(predicate.toString());
                filter.add(predicate);

                for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                    Player player = game.getPlayer(playerId);
                    if (player != null && !playerId.equals(controller.getId())) {
                        TargetControlledPermanent target = new TargetControlledPermanent(1, 1, filter, true);
                        if (target.canChoose(playerId, source, game)) {
                            player.chooseTarget(Outcome.Sacrifice, target, source, game);
                            perms.add(target.getFirstTarget());
                        }
                    }
                }

                boolean saccedPermaents = false;
                for (UUID permID : perms) {
                    Permanent permanent = game.getPermanent(permID);
                    if (permanent != null) {
                        permanent.sacrifice(source, game);
                        saccedPermaents = true;
                    }
                }

                return saccedPermaents;
            }
        }
        return false;
    }
}
