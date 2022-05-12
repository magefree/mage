
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.LoseLifeAllPlayersEffect;
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
 * @author LoneFox

 */
public final class PutridWarrior extends CardImpl {

    public PutridWarrior(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.SOLDIER);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Putrid Warrior deals damage, choose one - Each player loses 1 life; or each player gains 1 life.
        Ability ability = new PutridWarriorDealsDamageTriggeredAbility(new LoseLifeAllPlayersEffect(1));
        Mode mode = new Mode(new PutridWarriorGainLifeEffect());
        ability.addMode(mode);
        this.addAbility(ability);
    }

    private PutridWarrior(final PutridWarrior card) {
        super(card);
    }

    @Override
    public PutridWarrior copy() {
        return new PutridWarrior(this);
    }
}


class PutridWarriorDealsDamageTriggeredAbility extends TriggeredAbilityImpl {

    public PutridWarriorDealsDamageTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect, false);
    }

    public PutridWarriorDealsDamageTriggeredAbility(final PutridWarriorDealsDamageTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public PutridWarriorDealsDamageTriggeredAbility copy() {
        return new PutridWarriorDealsDamageTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER
            || event.getType() == GameEvent.EventType.DAMAGED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getSourceId().equals(this.sourceId);
    }

    @Override
    public String getTriggerPhrase() {
        return "Whenever {this} deals damage, " ;
    }
}


class PutridWarriorGainLifeEffect extends OneShotEffect {

    public PutridWarriorGainLifeEffect() {
        super(Outcome.GainLife);
        staticText = "Each player gains 1 life.";
    }

    public PutridWarriorGainLifeEffect(final PutridWarriorGainLifeEffect effect) {
        super(effect);
    }

    @Override
    public PutridWarriorGainLifeEffect copy() {
        return new PutridWarriorGainLifeEffect(this);
    }

    @Override
        public boolean apply(Game game, Ability source) {
        for(UUID playerId: game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if(player != null) {
                player.gainLife(1, game, source);
            }
        }
        return true;
    }

}
