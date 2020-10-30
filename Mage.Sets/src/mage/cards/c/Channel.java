
package mage.cards.c;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.SpecialAction;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.CreateSpecialActionEffect;
import mage.abilities.effects.common.RemoveSpecialActionEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;

/**
 *
 * @author emerald000
 */
public final class Channel extends CardImpl {

    public Channel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{G}{G}");

        // Until end of turn, any time you could activate a mana ability, you may pay 1 life. If you do, add {C}.
        this.getSpellAbility().addEffect(new ChannelEffect());
    }

    public Channel(final Channel card) {
        super(card);
    }

    @Override
    public Channel copy() {
        return new Channel(this);
    }
}

class ChannelEffect extends OneShotEffect {
    
    ChannelEffect() {
        super(Outcome.PutManaInPool);
        this.staticText = "Until end of turn, any time you could activate a mana ability, you may pay 1 life. If you do, add {C}";
    }
    
    ChannelEffect(final ChannelEffect effect) {
        super(effect);
    }
    
    @Override
    public ChannelEffect copy() {
        return new ChannelEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        SpecialAction specialAction = new ChannelSpecialAction();
        new CreateSpecialActionEffect(specialAction).apply(game, source);
        
        // Create a hidden delayed triggered ability to remove the special action at end of turn.
        new CreateDelayedTriggeredAbilityEffect(new ChannelDelayedTriggeredAbility(specialAction.getId()), false).apply(game, source);
        return true;
    }
}

class ChannelSpecialAction extends SpecialAction {

    ChannelSpecialAction() {
        super();
        this.abilityType = AbilityType.SPECIAL_MANA_PAYMENT;
        this.addCost(new PayLifeCost(1));
        this.addEffect(new BasicManaEffect(Mana.ColorlessMana(1)));
    }

    ChannelSpecialAction(final ChannelSpecialAction ability) {
        super(ability);
    }

    @Override
    public ChannelSpecialAction copy() {
        return new ChannelSpecialAction(this);
    }
}

class ChannelDelayedTriggeredAbility extends DelayedTriggeredAbility {

    ChannelDelayedTriggeredAbility(UUID specialActionId) {
        super(new RemoveSpecialActionEffect(specialActionId), Duration.OneUse);
        this.usesStack = false;
        this.setRuleVisible(false);
    }

    ChannelDelayedTriggeredAbility(ChannelDelayedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ChannelDelayedTriggeredAbility copy() {
        return new ChannelDelayedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.CLEANUP_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return true;
    }
}
