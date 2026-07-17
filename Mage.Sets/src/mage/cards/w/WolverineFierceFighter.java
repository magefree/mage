package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.FightTargetSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

/**
 *
 * @author muz
 */
public final class WolverineFierceFighter extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("other target creature");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public WolverineFierceFighter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.BERSERKER);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // When Wolverine enters, he fights up to one other target creature.
        Ability ability = new EntersBattlefieldTriggeredAbility(new FightTargetSourceEffect());
        ability.addTarget(new TargetPermanent(0, 1, filter));
        this.addAbility(ability);

        // If damage would be dealt to Wolverine, instead that damage is dealt, but all other damage already dealt to him is healed.
        this.addAbility(new SimpleStaticAbility(new WolverineFierceFighterEffect()));
    }

    private WolverineFierceFighter(final WolverineFierceFighter card) {
        super(card);
    }

    @Override
    public WolverineFierceFighter copy() {
        return new WolverineFierceFighter(this);
    }
}

class WolverineFierceFighterEffect extends ReplacementEffectImpl {

    WolverineFierceFighterEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If damage would be dealt to {this}, instead that damage is dealt, but all other damage already dealt to him is healed";
    }

    private WolverineFierceFighterEffect(final WolverineFierceFighterEffect effect) {
        super(effect);
    }

    @Override
    public WolverineFierceFighterEffect copy() {
        return new WolverineFierceFighterEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_PERMANENT;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getTargetId().equals(source.getSourceId());
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        DamageEvent damageEvent = (DamageEvent) event;
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            permanent.removeAllDamage(game);
            permanent.damage(damageEvent.getAmount(), damageEvent.getSourceId(), source, game,
                    damageEvent.isCombatDamage(), damageEvent.isPreventable(), event.getAppliedEffects());
        }
        return true;
    }
}
