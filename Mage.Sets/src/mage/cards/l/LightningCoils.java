
package mage.cards.l;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.ElementalToken;
import mage.players.Player;

/**
 *
 * @author escplan9 - Derek Monturo
 */
public final class LightningCoils extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("a nontoken creature you control");
    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
        filter.add(Predicates.not(new TokenPredicate()));
    }

    public LightningCoils(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");
        
        // Whenever a nontoken creature you control dies, put a charge counter on Lightning Coils.
        this.addAbility(
                new DiesCreatureTriggeredAbility(
                        new AddCountersSourceEffect(CounterType.CHARGE.createInstance(), true), 
                        false, filter));
        
        // At the beginning of your upkeep, if Lightning Coils has five or more charge counters on it, remove all of them from it 
        // and put that many 3/1 red Elemental creature tokens with haste onto the battlefield. 
        // Exile them at the beginning of the next end step.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new LightningCoilsEffect(), TargetController.YOU, false));
    }

    public LightningCoils(final LightningCoils card) {
        super(card);
    }

    @Override
    public LightningCoils copy() {
        return new LightningCoils(this);
    }
}

class LightningCoilsEffect extends OneShotEffect {

    LightningCoilsEffect() {
        super(Outcome.Benefit);
        staticText = "if {this} has five or more charge counters on it, remove all of them from it and put that many 3/1 red Elemental creature tokens with haste onto the battlefield. Exile them at the beginning of the next end step.";
    }

    LightningCoilsEffect(final LightningCoilsEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent p = game.getPermanent(source.getSourceId());
        if (p != null && controller != null) {
            int counters = p.getCounters(game).getCount(CounterType.CHARGE);
            if (counters >= 5) {
                // remove all the counters and create that many tokens
                p.removeCounters(CounterType.CHARGE.getName(), p.getCounters(game).getCount(CounterType.CHARGE), game);
                CreateTokenEffect effect = new CreateTokenEffect(new ElementalToken("CON", 1, true), counters);
                effect.apply(game, source);
                
                // exile those tokens at next end step
                effect.exileTokensCreatedAtNextEndStep(game, source);
                return true;
            }
        }
        return false;
    }

    @Override
    public LightningCoilsEffect copy() {
        return new LightningCoilsEffect(this);
    }
}