
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public final class TalusPaladin extends CardImpl {
    
    public TalusPaladin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.subtype.add(SubType.ALLY);
        

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever Talus Paladin or another Ally enters the battlefield under your control, you may have Allies you control gain lifelink until end of turn, and you may put a +1/+1 counter on Talus Paladin.
        this.addAbility(new TalusPaladinTriggeredAbility());
    }
    
    private TalusPaladin(final TalusPaladin card) {
        super(card);
    }
    
    @Override
    public TalusPaladin copy() {
        return new TalusPaladin(this);
    }
}

class TalusPaladinTriggeredAbility extends TriggeredAbilityImpl {
    
    private static final FilterControlledPermanent filter = new FilterControlledPermanent("all allies you control");
    
    static {
        filter.add(SubType.ALLY.getPredicate());
    }
    
    TalusPaladinTriggeredAbility() {
        super(Zone.BATTLEFIELD, new GainAbilityAllEffect(LifelinkAbility.getInstance(), Duration.EndOfTurn, filter), true);
        this.addEffect(new TalusPaladinEffect());
    }
    
    private TalusPaladinTriggeredAbility(final TalusPaladinTriggeredAbility ability) {
        super(ability);
    }
    
    @Override
    public TalusPaladinTriggeredAbility copy() {
        return new TalusPaladinTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }
    
    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent ally = game.getPermanent(event.getTargetId());
        if (ally != null) {
            if (ally.hasSubtype(SubType.ALLY, game)
                    && ally.isControlledBy(this.getControllerId())) {
                if (event.getTargetId().equals(this.getSourceId())
                        || event.getTargetId().equals(ally.getId())) {
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public String getRule() {
        return "Whenever {this} or another Ally enters the battlefield under your control, you may have Allies you control gain lifelink until end of turn, and you may put a +1/+1 counter on {this}.";
    }
}

class TalusPaladinEffect extends OneShotEffect {
    
    public TalusPaladinEffect() {
        super(Outcome.Benefit);
    }
    
    private TalusPaladinEffect(final TalusPaladinEffect effect) {
        super(effect);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent taluspPaladin = game.getPermanent(source.getSourceId());
        if (taluspPaladin != null && player != null) {
            String question = "Put a +1/+1 counter on Talus Paladin?";
            if (!player.chooseUse(Outcome.Benefit, question, source, game)) {
                return false;
            }
            taluspPaladin.addCounters(CounterType.P1P1.createInstance(), source.getControllerId(), source, game);
        }
        return false;
    }
    
    @Override
    public TalusPaladinEffect copy() {
        return new TalusPaladinEffect(this);
    }
}
