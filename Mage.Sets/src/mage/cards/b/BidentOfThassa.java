
package mage.cards.b;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.RequirementEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public final class BidentOfThassa extends CardImpl {

    public BidentOfThassa(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT,CardType.ARTIFACT},"{2}{U}{U}");
        addSuperType(SuperType.LEGENDARY);


        // Whenever a creature you control deals combat damage to a player, you may draw a card.
        this.addAbility(new BidentOfThassaTriggeredAbility());
        // {1}{U}, {T}: Creatures your opponents control attack this turn if able.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BidentOfThassaMustAttackEffect(), new ManaCostsImpl<>("{1}{U}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private BidentOfThassa(final BidentOfThassa card) {
        super(card);
    }

    @Override
    public BidentOfThassa copy() {
        return new BidentOfThassa(this);
    }
}

class BidentOfThassaTriggeredAbility extends TriggeredAbilityImpl {

    public BidentOfThassaTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), true);
    }

    public BidentOfThassaTriggeredAbility(final BidentOfThassaTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public BidentOfThassaTriggeredAbility copy() {
        return new BidentOfThassaTriggeredAbility(this);
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

class BidentOfThassaMustAttackEffect extends RequirementEffect {

    public BidentOfThassaMustAttackEffect() {
        super(Duration.EndOfTurn);
        staticText = "Creatures your opponents control attack this turn if able";
    }

    public BidentOfThassaMustAttackEffect(final BidentOfThassaMustAttackEffect effect) {
        super(effect);
    }

    @Override
    public BidentOfThassaMustAttackEffect copy() {
        return new BidentOfThassaMustAttackEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (game.getOpponents(source.getControllerId()).contains(permanent.getControllerId())) {
            return true;
        }
        return false;
    }

    @Override
    public boolean mustAttack(Game game) {
        return true;
    }

    @Override
    public boolean mustBlock(Game game) {
        return false;
    }

}
