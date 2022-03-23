
package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.RedirectionEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author L_J
 */
public final class Martyrdom extends CardImpl {

    public Martyrdom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}{W}");

        // Until end of turn, target creature you control gains "{0}: The next 1 damage that would be dealt to any target this turn is dealt to this creature instead." Only you may activate this ability.
        this.getSpellAbility().addEffect(new MartyrdomGainAbilityTargetEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
    }

    private Martyrdom(final Martyrdom card) {
        super(card);
    }

    @Override
    public Martyrdom copy() {
        return new Martyrdom(this);
    }
}

class MartyrdomGainAbilityTargetEffect extends ContinuousEffectImpl {

    public MartyrdomGainAbilityTargetEffect() {
        super(Duration.EndOfTurn, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        this.staticText = "Until end of turn, target creature you control gains \"{0}: The next 1 damage that would be dealt to target creature, planeswalker, or player this turn is dealt to this creature instead.\" Only you may activate this ability";
    }

    public MartyrdomGainAbilityTargetEffect(final MartyrdomGainAbilityTargetEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent != null) {
            ActivatedAbilityImpl ability = new MartyrdomActivatedAbility(source.getControllerId());
            ability.setMayActivate(TargetController.ANY);
            permanent.addAbility(ability, source.getSourceId(), game);
            return true;
        }
        return false;
    }

    @Override
    public MartyrdomGainAbilityTargetEffect copy() {
        return new MartyrdomGainAbilityTargetEffect(this);
    }
}

class MartyrdomActivatedAbility extends ActivatedAbilityImpl {

    private UUID caster;

    public MartyrdomActivatedAbility(UUID caster) {
        super(Zone.BATTLEFIELD, new MartyrdomRedirectDamageTargetEffect(Duration.EndOfTurn, 1), new GenericManaCost(0));
        this.addTarget(new TargetAnyTarget());
        this.caster = caster;
    }

    private MartyrdomActivatedAbility(final MartyrdomActivatedAbility ability) {
        super(ability);
        this.caster = ability.caster;
    }

    @Override
    public ActivationStatus canActivate(UUID playerId, Game game) {
        if (!playerId.equals(caster)) {
            return ActivationStatus.getFalse();
        }
        Permanent permanent = game.getBattlefield().getPermanent(this.getSourceId());
        if (permanent == null || !permanent.isCreature(game)) {
            return ActivationStatus.getFalse();
        }
        return super.canActivate(playerId, game);
    }

    @Override
    public MartyrdomActivatedAbility copy() {
        return new MartyrdomActivatedAbility(this);
    }

    @Override
    public String getRule() {
        return "{0}: The next 1 damage that would be dealt to target creature, planeswalker, or player this turn is dealt to {this} instead.";
    }
}

class MartyrdomRedirectDamageTargetEffect extends RedirectionEffect {

    private static FilterCreaturePermanent filter = new FilterCreaturePermanent();

    public MartyrdomRedirectDamageTargetEffect(Duration duration, int amount) {
        super(duration, amount, UsageType.ONE_USAGE_ABSOLUTE);
        staticText = "The next " + amount + " damage that would be dealt to target creature, planeswalker, or player this turn is dealt to {this} instead";
    }

    public MartyrdomRedirectDamageTargetEffect(final MartyrdomRedirectDamageTargetEffect effect) {
        super(effect);
    }

    @Override
    public MartyrdomRedirectDamageTargetEffect copy() {
        return new MartyrdomRedirectDamageTargetEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent permanent = game.getBattlefield().getPermanent(source.getSourceId());
        if (permanent != null) {
            if (filter.match(permanent, permanent.getControllerId(), source, game)) {
                if (event.getTargetId().equals(getTargetPointer().getFirst(game, source))) {
                    if (event.getTargetId() != null) {
                        TargetAnyTarget target = new TargetAnyTarget();
                        target.add(source.getSourceId(), game);
                        redirectTarget = target;
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
