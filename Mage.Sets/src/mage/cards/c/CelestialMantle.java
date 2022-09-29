
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class CelestialMantle extends CardImpl {

    public CelestialMantle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{W}{W}{W}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature gets +3/+3.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(3, 3, Duration.WhileOnBattlefield)));

        // Whenever enchanted creature deals combat damage to a player, double its controller's life total.
        this.addAbility(new CelestialMantleAbility());
    }

    private CelestialMantle(final CelestialMantle card) {
        super(card);
    }

    @Override
    public CelestialMantle copy() {
        return new CelestialMantle(this);
    }

}

class CelestialMantleAbility extends TriggeredAbilityImpl {

    public CelestialMantleAbility() {
        super(Zone.BATTLEFIELD, new CelestialMantleEffect());
    }

    public CelestialMantleAbility(final CelestialMantleAbility ability) {
        super(ability);
    }

    @Override
    public CelestialMantleAbility copy() {
        return new CelestialMantleAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        DamagedPlayerEvent damageEvent = (DamagedPlayerEvent) event;
        Permanent p = game.getPermanent(event.getSourceId());
        return damageEvent.isCombatDamage() && p != null && p.getAttachments().contains(this.getSourceId());
    }

    @Override
    public String getRule() {
        return "Whenever enchanted creature deals combat damage to a player, double its controller's life total.";
    }
}

class CelestialMantleEffect extends OneShotEffect {

    CelestialMantleEffect() {
        super(Outcome.GainLife);
    }

    CelestialMantleEffect(final CelestialMantleEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            player.gainLife(player.getLife(), game, source);
            return true;
        }
        return false;
    }

    @Override
    public CelestialMantleEffect copy() {
        return new CelestialMantleEffect(this);
    }
}
