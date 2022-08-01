
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.StateTriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledLandPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author fireshoes
 */
public final class ManaVortex extends CardImpl {

    public ManaVortex(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{U}{U}");

        // When you cast Mana Vortex, counter it unless you sacrifice a land.
        this.addAbility(new CastSourceTriggeredAbility(new CounterSourceEffect()));
        
        // At the beginning of each player's upkeep, that player sacrifices a land.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new SacrificeEffect(StaticFilters.FILTER_LAND, 1, "that player"),
            TargetController.ANY, false));
        
        // When there are no lands on the battlefield, sacrifice Mana Vortex.
        this.addAbility(new ManaVortexStateTriggeredAbility());
    }

    private ManaVortex(final ManaVortex card) {
        super(card);
    }

    @Override
    public ManaVortex copy() {
        return new ManaVortex(this);
    }
}

class CounterSourceEffect extends OneShotEffect {

    public CounterSourceEffect() {
        super(Outcome.Detriment);
    }

    public CounterSourceEffect(final CounterSourceEffect effect) {
        super(effect);
    }

    @Override
    public CounterSourceEffect copy() {
        return new CounterSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        
        StackObject spell = null;
        for(StackObject objet : game.getStack()){
            if(objet instanceof Spell && objet.getSourceId().equals(source.getSourceId())){
                spell = objet;
            }
        }
        if(spell != null){
                Player controller = game.getPlayer(source.getControllerId());
                if(controller != null && controller.chooseUse(Outcome.Detriment, "Sacrifice a land to not counter " + spell.getName() + '?', source, game)){
                    SacrificeTargetCost cost = new SacrificeTargetCost(new TargetControlledPermanent(new FilterControlledLandPermanent()));
                    if(cost.pay(source, game, source, source.getControllerId(), false, null)){
                        game.informPlayers(controller.getLogName() + " sacrifices a land to not counter " + spell.getName() + '.');
                        return true;
                    }
                    else {
                        game.getStack().counter(spell.getId(), source, game);
                    }
                }
            return true;
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        return "counter it unless you sacrifice a land";
    }
}

class ManaVortexStateTriggeredAbility extends StateTriggeredAbility {

    public ManaVortexStateTriggeredAbility() {
        super(Zone.BATTLEFIELD, new SacrificeSourceEffect());
    }

    public ManaVortexStateTriggeredAbility(final ManaVortexStateTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ManaVortexStateTriggeredAbility copy() {
        return new ManaVortexStateTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return game.getBattlefield().count(StaticFilters.FILTER_LANDS, this.getControllerId(), this, game) == 0;
    }

    @Override
    public String getTriggerPhrase() {
        return "When there are no lands on the battlefield, " ;
    }

}