
package mage.cards.l;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.PutIntoGraveFromBattlefieldSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.ControllerLifeCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.LoseGameSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.continuous.DontLoseByZeroOrLessLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author emerald000
 */
public final class Lich extends CardImpl {

    public Lich(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{B}{B}{B}{B}");

        // As Lich enters the battlefield, you lose life equal to your life total.
        this.addAbility(new EntersBattlefieldAbility(new LoseLifeSourceControllerEffect(ControllerLifeCount.instance), null, "As Lich enters the battlefield, you lose life equal to your life total.", null));
        
        // You don't lose the game for having 0 or less life.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DontLoseByZeroOrLessLifeEffect(Duration.WhileOnBattlefield)));
        
        // If you would gain life, draw that many cards instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new LichLifeGainReplacementEffect()));
        
        // Whenever you're dealt damage, sacrifice that many nontoken permanents. If you can't, you lose the game.
        this.addAbility(new LichDamageTriggeredAbility());
        
        // When Lich is put into a graveyard from the battlefield, you lose the game.
        this.addAbility(new PutIntoGraveFromBattlefieldSourceTriggeredAbility(new LoseGameSourceControllerEffect()));
    }

    private Lich(final Lich card) {
        super(card);
    }

    @Override
    public Lich copy() {
        return new Lich(this);
    }
}

class LichLifeGainReplacementEffect extends ReplacementEffectImpl {

    LichLifeGainReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.DrawCard);
        staticText = "If you would gain life, draw that many cards instead";
    }

    LichLifeGainReplacementEffect(final LichLifeGainReplacementEffect effect) {
        super(effect);
    }

    @Override
    public LichLifeGainReplacementEffect copy() {
        return new LichLifeGainReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(event.getPlayerId());
        if (controller != null) {
            controller.drawCards(event.getAmount(), source, game); // original event is not a draw event, so skip it in params
        }
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.GAIN_LIFE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getPlayerId().equals(source.getControllerId());
    }
}

class LichDamageTriggeredAbility extends TriggeredAbilityImpl {

    LichDamageTriggeredAbility() {
        super(Zone.BATTLEFIELD, new LichDamageEffect(), false);
    }

    LichDamageTriggeredAbility(final LichDamageTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public LichDamageTriggeredAbility copy() {
        return new LichDamageTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId().equals(this.getControllerId())) {
            for (Effect effect : this.getEffects()) {
                if (effect instanceof LichDamageEffect) {
                    ((LichDamageEffect) effect).setAmount(event.getAmount());
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you're dealt damage, sacrifice that many nontoken permanents. If you can't, you lose the game.";
    }
}

class LichDamageEffect extends OneShotEffect {
    
    private static final FilterControlledPermanent filter = new FilterControlledPermanent("nontoken permanent");
    static {
        filter.add(TokenPredicate.FALSE);
    }
    
    private int amount = 0;
    
    LichDamageEffect() {
        super(Outcome.Sacrifice);
        this.staticText = "sacrifice that many nontoken permanents. If you can't, you lose the game";
    }
    
    LichDamageEffect(final LichDamageEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }
    
    @Override
    public LichDamageEffect copy() {
        return new LichDamageEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Target target = new TargetControlledPermanent(amount, amount, filter, true);
            if (target.canChoose(controller.getId(), source, game)) {
                if (controller.choose(Outcome.Sacrifice, target, source, game)) {
                    for (UUID targetId : target.getTargets()) {
                        Permanent permanent = game.getPermanent(targetId);
                        if (permanent != null) {
                            permanent.sacrifice(source, game);
                        }
                    }
                    return true;
                }
            }
            controller.lost(game);
            return true;
        }
        return false;
    }
    
    public void setAmount(int amount) {
        this.amount = amount;
    }
}
