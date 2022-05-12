
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.PreventionEffectData;
import mage.abilities.effects.PreventionEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.TargetSource;

/**
 *
 * @author Quercitron
 */
public final class ReverseDamage extends CardImpl {

    public ReverseDamage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{W}{W}");


        // The next time a source of your choice would deal damage to you this turn, prevent that damage. You gain life equal to the damage prevented this way.
        this.getSpellAbility().addEffect(new ReverseDamageEffect());
    }

    private ReverseDamage(final ReverseDamage card) {
        super(card);
    }

    @Override
    public ReverseDamage copy() {
        return new ReverseDamage(this);
    }
}

class ReverseDamageEffect extends PreventionEffectImpl {

    private final TargetSource target;
    
    public ReverseDamageEffect() {
        super(Duration.EndOfTurn, Integer.MAX_VALUE, false, false);
        this.staticText = "The next time a source of your choice would deal damage to you this turn, prevent that damage. You gain life equal to the damage prevented this way.";
        this.target = new TargetSource();
    }

    public ReverseDamageEffect(final ReverseDamageEffect effect) {
        super(effect);
        this.target = effect.target.copy();
    }

    @Override
    public ReverseDamageEffect copy() {
        return new ReverseDamageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public void init(Ability source, Game game) {
        this.target.choose(Outcome.PreventDamage, source.getControllerId(), source.getSourceId(), source, game);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        PreventionEffectData preventionData = preventDamageAction(event, source, game);
        this.used = true;
        this.discard(); // only one use
        if (preventionData.getPreventedDamage() > 0) {
            Player player = game.getPlayer(source.getControllerId());
            if (player != null) {
                player.gainLife(preventionData.getPreventedDamage(), game, source);
            }            
        }        
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!this.used && super.applies(event, source, game)) {
            if (event.getTargetId().equals(source.getControllerId()) && event.getSourceId().equals(target.getFirstTarget())) {
                return true;
            }
        }
        return false;
    }
}
