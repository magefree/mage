
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author spjspj
 */
public final class VerdantSunsAvatar extends CardImpl {

    public VerdantSunsAvatar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}{G}");

        this.subtype.add(SubType.DINOSAUR);
        this.subtype.add(SubType.AVATAR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Whenever Verdant Sun's Avatar or another creature enters the battlefield under your control, you gain life equal to that creature's toughness.
        this.addAbility(new VerdantSunsAvatarTriggeredAbility());
    }

    private VerdantSunsAvatar(final VerdantSunsAvatar card) {
        super(card);
    }

    @Override
    public VerdantSunsAvatar copy() {
        return new VerdantSunsAvatar(this);
    }
}

class VerdantSunsAvatarTriggeredAbility extends TriggeredAbilityImpl {

    public VerdantSunsAvatarTriggeredAbility() {
        super(Zone.BATTLEFIELD, new VerdantSunsAvatarEffect(), false);
        setTriggerPhrase("Whenever {this} or another creature enters the battlefield under your control, ");
    }

    public VerdantSunsAvatarTriggeredAbility(VerdantSunsAvatarTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent == null
                || !permanent.isCreature(game)
                || !permanent.isControlledBy(this.controllerId)) {
            return false;
        }

        Effect effect = this.getEffects().get(0);
        // Life is determined during resolution so it has to be retrieved there (e.g. Giant Growth before resolution)
        effect.setValue("lifeSource", event.getTargetId());
        effect.setValue("zoneChangeCounter", permanent.getZoneChangeCounter(game));
        return true;
    }

    @Override
    public VerdantSunsAvatarTriggeredAbility copy() {
        return new VerdantSunsAvatarTriggeredAbility(this);
    }
}

class VerdantSunsAvatarEffect extends OneShotEffect {

    public VerdantSunsAvatarEffect() {
        super(Outcome.GainLife);
        staticText = "you gain life equal to that creature's toughness";
    }

    public VerdantSunsAvatarEffect(final VerdantSunsAvatarEffect effect) {
        super(effect);
    }

    @Override
    public VerdantSunsAvatarEffect copy() {
        return new VerdantSunsAvatarEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID creatureId = (UUID) getValue("lifeSource");
        Integer zoneChangeCounter = (Integer) getValue("zoneChangeCounter");
        Permanent creature = game.getPermanent(creatureId);
        if (creature == null || creature.getZoneChangeCounter(game) != zoneChangeCounter) {
            creature = (Permanent) game.getLastKnownInformation(creatureId, Zone.BATTLEFIELD, zoneChangeCounter);
        }
        if (creature != null) {
            int amount = creature.getToughness().getValue();
            Player player = game.getPlayer(source.getControllerId());
            if (player != null) {
                player.gainLife(amount, game, source);
            }
            return true;
        }
        return false;
    }
}
