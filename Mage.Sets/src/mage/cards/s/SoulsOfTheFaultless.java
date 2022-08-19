
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author North
 */
public final class SoulsOfTheFaultless extends CardImpl {

    public SoulsOfTheFaultless(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}{B}{B}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Defender
        this.addAbility(DefenderAbility.getInstance());
        // Whenever Souls of the Faultless is dealt combat damage, you gain that much life and attacking player loses that much life.
        this.addAbility(new SoulsOfTheFaultlessTriggeredAbility());
    }

    private SoulsOfTheFaultless(final SoulsOfTheFaultless card) {
        super(card);
    }

    @Override
    public SoulsOfTheFaultless copy() {
        return new SoulsOfTheFaultless(this);
    }
}

class SoulsOfTheFaultlessTriggeredAbility extends TriggeredAbilityImpl {

    public SoulsOfTheFaultlessTriggeredAbility() {
        super(Zone.BATTLEFIELD, new SoulsOfTheFaultlessEffect());
        setTriggerPhrase("Whenever {this} is dealt combat damage, ");
    }

    public SoulsOfTheFaultlessTriggeredAbility(final SoulsOfTheFaultlessTriggeredAbility effect) {
        super(effect);
    }

    @Override
    public SoulsOfTheFaultlessTriggeredAbility copy() {
        return new SoulsOfTheFaultlessTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId().equals(this.sourceId)
                && ((DamagedEvent) event).isCombatDamage()) {
            Permanent source = game.getPermanent(event.getSourceId());
            if (source == null) {
                source = (Permanent) game.getLastKnownInformation(event.getSourceId(), Zone.BATTLEFIELD);
            }
            UUID attackerId = source != null ? source.getControllerId() : null;
            for (Effect effect : this.getEffects()) {
                effect.setValue("damageAmount", event.getAmount());
                effect.setValue("attackerId", attackerId);
            }
            return true;
        }
        return false;
    }
}

class SoulsOfTheFaultlessEffect extends OneShotEffect {

    public SoulsOfTheFaultlessEffect() {
        super(Outcome.GainLife);
        staticText = "you gain that much life and attacking player loses that much life";
    }

    public SoulsOfTheFaultlessEffect(final SoulsOfTheFaultlessEffect effect) {
        super(effect);
    }

    @Override
    public SoulsOfTheFaultlessEffect copy() {
        return new SoulsOfTheFaultlessEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Integer amount = (Integer) this.getValue("damageAmount");

        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            player.gainLife(amount, game, source);
        }

        UUID attackerId = (UUID) this.getValue("attackerId");
        Player attacker = game.getPlayer(attackerId);
        if (attacker != null) {
            attacker.loseLife(amount, game, source, false);
        }
        return true;
    }
}
