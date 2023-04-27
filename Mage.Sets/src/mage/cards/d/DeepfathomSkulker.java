
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class DeepfathomSkulker extends CardImpl {

    public DeepfathomSkulker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{U}");
        this.subtype.add(SubType.ELDRAZI);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Devoid </i>(This card has no color.)</i>
        this.addAbility(new DevoidAbility(this.color));
        
        // Whenever a creature you control deals combat damage to a player, you may draw a card.
        this.addAbility(new DeepfathomSkulkerTriggeredAbility());
        
        // {3}{C}: Target creature can't be blocked this turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CantBeBlockedTargetEffect(), new ManaCostsImpl<>("{3}{C}"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private DeepfathomSkulker(final DeepfathomSkulker card) {
        super(card);
    }

    @Override
    public DeepfathomSkulker copy() {
        return new DeepfathomSkulker(this);
    }
}

class DeepfathomSkulkerTriggeredAbility extends TriggeredAbilityImpl {

    public DeepfathomSkulkerTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), true);
    }

    public DeepfathomSkulkerTriggeredAbility(final DeepfathomSkulkerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public DeepfathomSkulkerTriggeredAbility copy() {
        return new DeepfathomSkulkerTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (((DamagedPlayerEvent) event).isCombatDamage()) {
            Permanent creature = game.getPermanent(event.getSourceId());
            if (creature != null && creature.isControlledBy(controllerId)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return " Whenever a creature you control deals combat damage to a player, you may draw a card.";
    }
}
