package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.AttachedToAttachedPredicate;
import mage.game.Game;
import mage.game.events.DamagePlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WithGreatPower extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Aura and Equipment attached to it");

    static {
        filter.add(Predicates.or(
                SubType.AURA.getPredicate(),
                SubType.EQUIPMENT.getPredicate()
        ));
        filter.add(AttachedToAttachedPredicate.instance);
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter, 2);

    public WithGreatPower(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}");

        this.subtype.add(SubType.AURA);

        // Enchant creature you control
        TargetPermanent auraTarget = new TargetPermanent(StaticFilters.FILTER_CONTROLLED_CREATURE);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted creature gets +2/+2 for each Aura and Equipment attached to it.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(xValue, xValue)));

        // All damage that would be dealt to you is dealt to enchanted creature instead.
        this.addAbility(new SimpleStaticAbility(new WithGreatPowerEffect()));
    }

    private WithGreatPower(final WithGreatPower card) {
        super(card);
    }

    @Override
    public WithGreatPower copy() {
        return new WithGreatPower(this);
    }
}

class WithGreatPowerEffect extends ReplacementEffectImpl {
    WithGreatPowerEffect() {
        super(Duration.WhileOnBattlefield, Outcome.RedirectDamage);
        staticText = "all damage that would be dealt to you is dealt to enchanted creature instead";
    }

    private WithGreatPowerEffect(final WithGreatPowerEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        DamagePlayerEvent damageEvent = (DamagePlayerEvent) event;
        Optional.ofNullable(event)
                .map(GameEvent::getSourceId)
                .map(game::getPermanent)
                .map(Permanent::getAttachedTo)
                .map(game::getPermanent)
                .ifPresent(permanent -> permanent.damage(
                        damageEvent.getAmount(), event.getSourceId(), source,
                        game, damageEvent.isCombatDamage(), damageEvent.isPreventable()
                ));
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_PLAYER;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return source.isControlledBy(event.getPlayerId());
    }

    @Override
    public WithGreatPowerEffect copy() {
        return new WithGreatPowerEffect(this);
    }
}
