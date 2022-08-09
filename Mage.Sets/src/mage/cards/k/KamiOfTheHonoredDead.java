
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.SoulshiftAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;

/**
 *
 * @author LevelX
 */
public final class KamiOfTheHonoredDead extends CardImpl {

    public KamiOfTheHonoredDead(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{W}{W}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Flying  
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Kami of the Honored Dead is dealt damage, you gain that much life.
        this.addAbility(new KamiOfTheHonoredDeadTriggeredAbility());
        // Soulshift 6 (When this creature dies, you may return target Spirit card with converted mana cost 6 or less from your graveyard to your hand.)
        this.addAbility(new SoulshiftAbility(6));
    }

    private KamiOfTheHonoredDead(final KamiOfTheHonoredDead card) {
        super(card);
    }

    @Override
    public KamiOfTheHonoredDead copy() {
        return new KamiOfTheHonoredDead(this);
    }
}

class KamiOfTheHonoredDeadTriggeredAbility extends TriggeredAbilityImpl {

    public KamiOfTheHonoredDeadTriggeredAbility() {
        super(Zone.BATTLEFIELD, new KamiOfTheHonoredDeadGainLifeEffect());
        setTriggerPhrase("Whenever {this} is dealt damage, ");
    }

    public KamiOfTheHonoredDeadTriggeredAbility(final KamiOfTheHonoredDeadTriggeredAbility effect) {
        super(effect);
    }

    @Override
    public KamiOfTheHonoredDeadTriggeredAbility copy() {
        return new KamiOfTheHonoredDeadTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId().equals(this.sourceId)) {
            this.getEffects().get(0).setValue("damageAmount", event.getAmount());
            return true;
        }
        return false;
    }
}


class KamiOfTheHonoredDeadGainLifeEffect extends OneShotEffect {

        public KamiOfTheHonoredDeadGainLifeEffect() {
            super(Outcome.GainLife);
            staticText = "you gain that much life";
        }

    public KamiOfTheHonoredDeadGainLifeEffect(final KamiOfTheHonoredDeadGainLifeEffect effect) {
        super(effect);
    }

    @Override
    public KamiOfTheHonoredDeadGainLifeEffect copy() {
        return new KamiOfTheHonoredDeadGainLifeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            player.gainLife((Integer) this.getValue("damageAmount"), game, source);
        }
        return true;
    }


}