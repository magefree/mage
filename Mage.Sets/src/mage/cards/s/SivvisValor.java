
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author L_J
 */
public final class SivvisValor extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("If you control a Plains");
    private static final FilterControlledCreaturePermanent filterCreature = new FilterControlledCreaturePermanent("untapped creature you control");

    static {
        filter.add(SubType.PLAINS.getPredicate());
        filterCreature.add(TappedPredicate.UNTAPPED);
    }

    public SivvisValor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{W}");

        // If you control a Plains, you may tap an untapped creature you control rather than pay Sivvi's Valor's mana cost.
        Cost cost = new TapTargetCost(new TargetControlledPermanent(1,1,filterCreature,false));
        cost.setText(" tap an untapped creature you control");
        this.addAbility(new AlternativeCostSourceAbility(cost, new PermanentsOnTheBattlefieldCondition(filter)));

        // All damage that would be dealt to target creature this turn is dealt to you instead.
        this.getSpellAbility().addEffect(new SivvisValorEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private SivvisValor(final SivvisValor card) {
        super(card);
    }

    @Override
    public SivvisValor copy() {
        return new SivvisValor(this);
    }
}

class SivvisValorEffect extends ReplacementEffectImpl {

    public SivvisValorEffect() {
        super(Duration.EndOfTurn, Outcome.RedirectDamage);
        staticText = "All damage that would be dealt to target creature this turn is dealt to you instead";
    }

    private SivvisValorEffect(final SivvisValorEffect effect) {
        super(effect);
    }

    @Override
    public SivvisValorEffect copy() {
        return new SivvisValorEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_PERMANENT;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        DamageEvent damageEvent = (DamageEvent) event;
        if (controller != null) {
            controller.damage(damageEvent.getAmount(), damageEvent.getSourceId(), source, game, damageEvent.isCombatDamage(), damageEvent.isPreventable(), damageEvent.getAppliedEffects());
            return true;
        }
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        DamageEvent damageEvent = (DamageEvent) event;
        Permanent targetPermanent = game.getPermanent(source.getFirstTarget());
        if (controller != null && targetPermanent != null) {
            return targetPermanent.getId().equals(damageEvent.getTargetId());
        }
        return false;
    }
}
