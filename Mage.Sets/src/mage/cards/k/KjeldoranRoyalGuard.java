
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.UnblockedPredicate;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.DamagePlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author Quercitron
 */
public final class KjeldoranRoyalGuard extends CardImpl {

    public KjeldoranRoyalGuard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // {tap}: All combat damage that would be dealt to you by unblocked creatures this turn is dealt to Kjeldoran Royal Guard instead.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new KjeldoranRoyalGuardEffect(), new TapSourceCost()));
    }

    private KjeldoranRoyalGuard(final KjeldoranRoyalGuard card) {
        super(card);
    }

    @Override
    public KjeldoranRoyalGuard copy() {
        return new KjeldoranRoyalGuard(this);
    }
}

class KjeldoranRoyalGuardEffect extends ReplacementEffectImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("unblocked creatures");
    
    static {
        filter.add(UnblockedPredicate.instance);
    }
    
    KjeldoranRoyalGuardEffect() {
        super(Duration.EndOfTurn, Outcome.RedirectDamage);
        staticText = "All combat damage that would be dealt to you by unblocked creatures this turn is dealt to {this} instead";
    }

    KjeldoranRoyalGuardEffect(final KjeldoranRoyalGuardEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        DamagePlayerEvent damageEvent = (DamagePlayerEvent) event;
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            permanent.damage(damageEvent.getAmount(), event.getSourceId(), source, game, damageEvent.isCombatDamage(), damageEvent.isPreventable());
        }
        return true;
    }
    
    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_PLAYER;
    }
    
    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getPlayerId().equals(source.getControllerId())
                && ((DamageEvent)event).isCombatDamage()) {
            Permanent p = game.getPermanent(source.getSourceId());
            if (p != null) {
                for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)) {
                    if (event.getSourceId().equals(permanent.getId())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public KjeldoranRoyalGuardEffect copy() {
        return new KjeldoranRoyalGuardEffect(this);
    }
}
