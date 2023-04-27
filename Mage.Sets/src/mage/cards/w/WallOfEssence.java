
package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class WallOfEssence extends CardImpl {

    public WallOfEssence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");
        this.subtype.add(SubType.WALL);

        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Defender
        this.addAbility(DefenderAbility.getInstance());
        // Whenever Wall of Essence is dealt combat damage, you gain that much life.
        this.addAbility(new WallOfEssenceTriggeredAbility());
    }

    private WallOfEssence(final WallOfEssence card) {
        super(card);
    }

    @Override
    public WallOfEssence copy() {
        return new WallOfEssence(this);
    }
}

class WallOfEssenceTriggeredAbility extends TriggeredAbilityImpl {

    public WallOfEssenceTriggeredAbility() {
        super(Zone.BATTLEFIELD, new PiousWarriorGainLifeEffect());
        setTriggerPhrase("Whenever {this} is dealt combat damage, ");
    }

    public WallOfEssenceTriggeredAbility(final WallOfEssenceTriggeredAbility effect) {
        super(effect);
    }

    @Override
    public WallOfEssenceTriggeredAbility copy() {
        return new WallOfEssenceTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent == null
                || !permanent.isCreature(game)
                || !event.getTargetId().equals(this.sourceId)
                || !((DamagedEvent) event).isCombatDamage()) {
            return false;
        }
        this.getEffects().setValue("damageAmount", event.getAmount());
        return true;
    }
}


class PiousWarriorGainLifeEffect extends OneShotEffect {

    public PiousWarriorGainLifeEffect() {
        super(Outcome.GainLife);
        staticText = "you gain that much life";
    }

    public PiousWarriorGainLifeEffect(final PiousWarriorGainLifeEffect effect) {
        super(effect);
    }

    @Override
    public PiousWarriorGainLifeEffect copy() {
        return new PiousWarriorGainLifeEffect(this);
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
