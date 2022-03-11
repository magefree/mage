package mage.cards.m;

import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public final class MobRule extends CardImpl {

    public MobRule(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{R}{R}");

        // Choose one
        // Gain control of all creatures with power 4 or greater until end of turn. Untap those creatures. They gain haste until end of turn.
        this.getSpellAbility().addEffect(new MobRuleEffect(ComparisonType.MORE_THAN, 3));

        // Gain control of all creatures with power 3 or less until end of turn. Untap those creatures. They gain haste until end of turn.
        Mode mode = new Mode(new MobRuleEffect(ComparisonType.FEWER_THAN, 4));
        this.getSpellAbility().addMode(mode);
    }

    private MobRule(final MobRule card) {
        super(card);
    }

    @Override
    public MobRule copy() {
        return new MobRule(this);
    }
}

class MobRuleEffect extends OneShotEffect {

    ComparisonType type = null;
    int power = 0;

    public MobRuleEffect(ComparisonType type, int power) {
        super(Outcome.GainControl);
        this.type = type;
        this.power = power;
        if (type == ComparisonType.MORE_THAN) {
            this.staticText = "Gain control of all creatures with power 4 or greater until end of turn. Untap those creatures. They gain haste until end of turn";
        } else {
            this.staticText = "Gain control of all creatures with power 3 or less until end of turn. Untap those creatures. They gain haste until end of turn";
        }
    }

    public MobRuleEffect(final MobRuleEffect effect) {
        super(effect);
        this.type = effect.type;
        this.power = effect.power;
    }

    @Override
    public MobRuleEffect copy() {
        return new MobRuleEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean applied = false;
        FilterCreaturePermanent filter = new FilterCreaturePermanent();
        filter.add(new PowerPredicate(type, power));
        List<Permanent> creatures = game.getBattlefield().getAllActivePermanents(filter, game);
        for (Permanent creature : creatures) {
            ContinuousEffect effect = new MobRuleControlAllEffect(source.getControllerId());
            effect.setTargetPointer(new FixedTarget(creature.getId(), game));
            game.addEffect(effect, source);
            applied = true;
        }
        for (Permanent creature : creatures) {
            creature.untap(game);
            applied = true;
        }
        for (Permanent creature : creatures) {
            ContinuousEffect effect = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn);
            effect.setTargetPointer(new FixedTarget(creature.getId(), game));
            game.addEffect(effect, source);
            applied = true;
        }
        return applied;
    }
}

class MobRuleControlAllEffect extends ContinuousEffectImpl {

    private final UUID controllerId;

    public MobRuleControlAllEffect(UUID controllerId) {
        super(Duration.EndOfTurn, Layer.ControlChangingEffects_2, SubLayer.NA, Outcome.GainControl);
        this.controllerId = controllerId;
    }

    public MobRuleControlAllEffect(final MobRuleControlAllEffect effect) {
        super(effect);
        this.controllerId = effect.controllerId;
    }

    @Override
    public MobRuleControlAllEffect copy() {
        return new MobRuleControlAllEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = game.getPermanent(targetPointer.getFirst(game, source));
        if (creature != null && controllerId != null) {
            return creature.changeControllerId(controllerId, game, source);
        }
        return false;
    }
}
