package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.ExchangeControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.EachTargetPointer;

import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ModifyMemory extends CardImpl {

    public ModifyMemory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{U}");

        // Exchange control of two target creatures controlled by different players. If you control neither creature, draw three cards.
        this.getSpellAbility().addEffect(new ExchangeControlTargetEffect(
                Duration.Custom, "exchange control of two target creatures controlled by different players"
        ).setTargetPointer(new EachTargetPointer()));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DrawCardSourceControllerEffect(3), ModifyMemoryCondition.instance,
                "If you control neither creature, draw three cards"
        ));
        this.getSpellAbility().addTarget(new ModifyMemoryTarget());
    }

    private ModifyMemory(final ModifyMemory card) {
        super(card);
    }

    @Override
    public ModifyMemory copy() {
        return new ModifyMemory(this);
    }
}

enum ModifyMemoryCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return source
                .getTargets()
                .stream()
                .map(Target::getTargets)
                .flatMap(Collection::stream)
                .map(game::getPermanentOrLKIBattlefield)
                .filter(Objects::nonNull)
                .map(Controllable::getControllerId)
                .noneMatch(source::isControlledBy);
    }
}

class ModifyMemoryTarget extends TargetCreaturePermanent {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("creatures controlled by different players");

    ModifyMemoryTarget() {
        super(2, 2, filter, false);
    }

    private ModifyMemoryTarget(final ModifyMemoryTarget target) {
        super(target);
    }

    @Override
    public ModifyMemoryTarget copy() {
        return new ModifyMemoryTarget(this);
    }

    @Override
    public boolean canTarget(UUID controllerId, UUID id, Ability source, Game game) {
        if (!super.canTarget(controllerId, id, source, game)) {
            return false;
        }
        Permanent creature = game.getPermanent(id);
        if (creature == null) {
            return false;
        }
        return this.getTargets()
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .noneMatch(permanent -> !creature.getId().equals(permanent.getId())
                        && creature.isControlledBy(permanent.getControllerId())
                );
    }
}
