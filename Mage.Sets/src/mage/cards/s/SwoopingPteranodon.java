package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.constants.*;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jimga150
 */
public final class SwoopingPteranodon extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.DINOSAUR, "Dinosaur with flying");

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }

    public SwoopingPteranodon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{W}");
        
        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever Swooping Pteranodon or another Dinosaur with flying enters the battlefield under your control,
        // gain control of target creature an opponent controls until end of turn. Untap that creature.
        // It gains flying and haste until end of turn.
        // At the beginning of the next end step, target land deals 3 damage to that creature.
        Ability ability = new EntersBattlefieldThisOrAnotherTriggeredAbility(
                new GainControlTargetEffect(Duration.EndOfTurn), filter, false, true);
        ability.addEffect(new UntapTargetEffect().setText("Untap that creature"));
        ability.addEffect(new GainAbilityTargetEffect(FlyingAbility.getInstance(), Duration.EndOfTurn).setText("It gains flying"));
        ability.addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn).setText("and haste until end of turn."));
        ability.addEffect(new SwoopingPteranodonCreateDelayedTriggerEffect());

        ability.addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE));

        this.addAbility(ability);

        // Swooping Pteranodon's triggered ability targets only the creature an opponent controls. You choose the
        // target land (where the poor creature is going to drop) as the delayed triggered ability triggers at the
        // beginning of the next end step.
        // (2023-11-10)
        // The little "(where the poor creature is going to drop)" is like, funny, but mainly it also drives home
        // the love and care that this game gets. The official rulings making a joke about how the weird specifics of
        // the mechanics of a card play into its flavor is a nice little touch, to me.
    }

    private SwoopingPteranodon(final SwoopingPteranodon card) {
        super(card);
    }

    @Override
    public SwoopingPteranodon copy() {
        return new SwoopingPteranodon(this);
    }
}

// Based on Angrath the Flame Chained
class SwoopingPteranodonCreateDelayedTriggerEffect extends OneShotEffect {

    SwoopingPteranodonCreateDelayedTriggerEffect() {
        super(Outcome.Sacrifice);
        staticText = "At the beginning of the next end step, target land deals 3 damage to that creature.";
    }

    private SwoopingPteranodonCreateDelayedTriggerEffect(final SwoopingPteranodonCreateDelayedTriggerEffect effect) {
        super(effect);
    }

    @Override
    public SwoopingPteranodonCreateDelayedTriggerEffect copy() {
        return new SwoopingPteranodonCreateDelayedTriggerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        Effect damageEffect = new SwoopingPteranodonDamageEffect(3);
        damageEffect.setTargetPointer(new FixedTarget(permanent, game));
        damageEffect.setText("target land deals 3 damage to that creature.");

        DelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(damageEffect);
        delayedAbility.addTarget(new TargetPermanent(StaticFilters.FILTER_LAND));
        game.addDelayedTriggeredAbility(delayedAbility, source);

        return true;
    }
}

class SwoopingPteranodonDamageEffect extends OneShotEffect {

    private final int damage;

    public SwoopingPteranodonDamageEffect(int damage) {
        super(Outcome.Damage);
        this.damage = damage;
    }

    protected SwoopingPteranodonDamageEffect(final SwoopingPteranodonDamageEffect effect) {
        super(effect);
        this.damage = effect.damage;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent damagedPermanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        Permanent damagingPermanent = game.getPermanent(source.getTargets().get(0).getFirstTarget());
        if (damagedPermanent == null || damagingPermanent == null) {
            return false;
        }
        damagedPermanent.damage(damage, damagingPermanent.getId(), source, game, false, true);
        return true;
    }

    @Override
    public SwoopingPteranodonDamageEffect copy() {
        return new SwoopingPteranodonDamageEffect(this);
    }

}
