
package mage.cards.x;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public final class XantidSwarm extends CardImpl {

    public XantidSwarm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}");
        this.subtype.add(SubType.INSECT);

        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever Xantid Swarm attacks, defending player can't cast spells this turn.
        Ability ability = new XantidSwarmTriggeredAbility(new XantidSwarmReplacementEffect());        
        this.addAbility(ability);
    }

    private XantidSwarm(final XantidSwarm card) {
        super(card);
    }

    @Override
    public XantidSwarm copy() {
        return new XantidSwarm(this);
    }
}

class XantidSwarmTriggeredAbility extends TriggeredAbilityImpl {

    public XantidSwarmTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect);
        setTriggerPhrase("Whenever {this} attacks, ");
    }

    public XantidSwarmTriggeredAbility(final XantidSwarmTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public XantidSwarmTriggeredAbility copy() {
        return new XantidSwarmTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getSourceId().equals(this.getSourceId())) {
            return false;
        }
        UUID defender = game.getCombat().getDefendingPlayerId(this.getSourceId(), game);
        this.getEffects().get(0).setTargetPointer(new FixedTarget(defender));
        return true;
    }
}

class XantidSwarmReplacementEffect extends ContinuousRuleModifyingEffectImpl {

    public XantidSwarmReplacementEffect() {
        super(Duration.EndOfTurn, Outcome.Benefit);
        staticText = "defending player can't cast spells this turn";
    }

    public XantidSwarmReplacementEffect(final XantidSwarmReplacementEffect effect) {
        super(effect);
    }

    @Override
    public XantidSwarmReplacementEffect copy() {
        return new XantidSwarmReplacementEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        return player != null && player.getId().equals(event.getPlayerId());
    }
}
