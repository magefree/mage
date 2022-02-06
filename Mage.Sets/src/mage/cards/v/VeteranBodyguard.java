package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.SourceTappedCondition;
import mage.abilities.decorator.ConditionalPreventionEffect;
import mage.abilities.effects.PreventionEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.UnblockedPredicate;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.DamagePlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author MTGfan
 */
public final class VeteranBodyguard extends CardImpl {

    public VeteranBodyguard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");

        this.subtype.add(SubType.HUMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // As long as Veteran Bodyguard is untapped, all damage that would be dealt to you by unblocked creatures is dealt to Veteran Bodyguard instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalPreventionEffect(
                new VeteranBodyguardEffect(),
                SourceTappedCondition.UNTAPPED,
                "As long as {this} is untapped, all damage that would be dealt to you by unblocked creatures is dealt to {this} instead."
        )));
    }

    private VeteranBodyguard(final VeteranBodyguard card) {
        super(card);
    }

    @Override
    public VeteranBodyguard copy() {
        return new VeteranBodyguard(this);
    }
}

class VeteranBodyguardEffect extends PreventionEffectImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("unblocked creatures");

    static {
        filter.add(UnblockedPredicate.instance);
    }

    VeteranBodyguardEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "all combat damage that would be dealt to you by unblocked creatures is dealt to {this} instead";
    }

    VeteranBodyguardEffect(final VeteranBodyguardEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        DamagePlayerEvent damageEvent = (DamagePlayerEvent) event;
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            permanent.damage(damageEvent.getAmount(), event.getSourceId(), source, game, damageEvent.isCombatDamage(), damageEvent.isPreventable());
            return true;
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_PLAYER;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getPlayerId().equals(source.getControllerId())
                && ((DamageEvent) event).isCombatDamage()) {
            Permanent p = game.getPermanent(source.getSourceId());
            if (p != null) {
                for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game)) {
                    if (event.getSourceId().equals(permanent.getId())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public VeteranBodyguardEffect copy() {
        return new VeteranBodyguardEffect(this);
    }
}
