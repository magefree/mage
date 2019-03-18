
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.RevealTargetFromHandCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.TargetSpell;
import mage.target.common.TargetCardInHand;
import mage.watchers.common.DragonOnTheBattlefieldWhileSpellWasCastWatcher;

/**
 *
 * @author LevelX2
 */
public final class SilumgarsScorn extends CardImpl {

    private static final FilterCard filter = new FilterCard("a Dragon card from your hand (you don't have to)");

    static {
        filter.add(new SubtypePredicate(SubType.DRAGON));
    }    
    
    public SilumgarsScorn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{U}{U}");

        // As an additional cost to cast Silumgar's Scorn, you may reveal a Dragon card from your hand.
        this.getSpellAbility().addEffect(new InfoEffect("as an additional cost to cast this spell, you may reveal a Dragon card from your hand"));
        
        // Counter target spell unless its controller pays {1}. If you revealed a Dragon card or controlled a Dragon as you cast Silumgar's Scorn, counter that spell instead.
        this.getSpellAbility().addEffect(new SilumgarsScornCounterEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
        this.getSpellAbility().addWatcher(new DragonOnTheBattlefieldWhileSpellWasCastWatcher());
        
    }

    
    @Override
    public void adjustCosts(Ability ability, Game game) {
        if (ability.getAbilityType() == AbilityType.SPELL) {
            Player controller = game.getPlayer(ability.getControllerId());
            if (controller != null) {
                if (controller.getHand().count(filter, game) > 0) {
                    ability.addCost(new RevealTargetFromHandCost(new TargetCardInHand(0,1, filter)));
                }
            }
        }
    }
    
    public SilumgarsScorn(final SilumgarsScorn card) {
        super(card);
    }

    @Override
    public SilumgarsScorn copy() {
        return new SilumgarsScorn(this);
    }
}

class SilumgarsScornCounterEffect extends OneShotEffect {

    public SilumgarsScornCounterEffect() {
        super(Outcome.Detriment);
        staticText = "<br/>Counter target spell unless its controller pays {1}. If you revealed a Dragon card or controlled a Dragon as you cast {this}, counter that spell instead";
    }

    public SilumgarsScornCounterEffect(final SilumgarsScornCounterEffect effect) {
        super(effect);
    }

    @Override
    public SilumgarsScornCounterEffect copy() {
        return new SilumgarsScornCounterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        StackObject spell = game.getStack().getStackObject(targetPointer.getFirst(game, source));
        if (spell != null) {
            Player player = game.getPlayer(spell.getControllerId());
            if (player != null) {
                DragonOnTheBattlefieldWhileSpellWasCastWatcher watcher = game.getState().getWatcher(DragonOnTheBattlefieldWhileSpellWasCastWatcher.class);
                boolean condition = watcher != null && watcher.castWithConditionTrue(source.getId());
                if (!condition) {
                    for (Cost cost: source.getCosts()) {
                        if (cost instanceof RevealTargetFromHandCost) {
                            condition = ((RevealTargetFromHandCost)cost).getNumberRevealedCards() > 0;
                        }
                    }
                }
                if (condition) {
                    return game.getStack().counter(spell.getId(), source.getSourceId(), game);
                }
                if (!(player.chooseUse(Outcome.Benefit, "Would you like to pay {1} to prevent counter effect?", source, game) && 
                        new GenericManaCost(1).pay(source, game, spell.getSourceId(), spell.getControllerId(), false))) {
                    return game.getStack().counter(spell.getId(), source.getSourceId(), game);
                }
            }
        }
        return true;
    }

}
