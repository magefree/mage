package mage.cards.b;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.DamageWithPowerFromOneToAnotherTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetOpponentsCreaturePermanent;
import mage.watchers.common.SpellsCastWatcher;

/**
 *
 * @author muz
 */
public final class BurrogBarrage extends CardImpl {

    private static final Hint hint = new ConditionHint(
            BurrogBarrageCondition.instance, "You've cast an instant or sorcery this turn"
    );

    public BurrogBarrage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // Target creature you control gets +1/+0 until end of turn if you've cast another instant or sorcery spell this turn. Then it deals damage equal to its power to up to one target creature an opponent controls.
        this.getSpellAbility().addEffect(new ConditionalContinuousEffect(
            new BoostTargetEffect(1, 0),
            BurrogBarrageCondition.instance,
            "Target creature you control gets +1/+0 until end of turn if you've cast another instant or sorcery spell this turn"
        ));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());

        this.getSpellAbility().addEffect(
            new DamageWithPowerFromOneToAnotherTargetEffect()
                .setText("Then it deals damage equal to its power to up to one target creature an opponent controls")
        );
        this.getSpellAbility().addTarget(new TargetOpponentsCreaturePermanent());
        this.getSpellAbility().addHint(hint);
    }

    private BurrogBarrage(final BurrogBarrage card) {
        super(card);
    }

    @Override
    public BurrogBarrage copy() {
        return new BurrogBarrage(this);
    }
}

enum BurrogBarrageCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        SpellsCastWatcher watcher = game.getState().getWatcher(SpellsCastWatcher.class);
        if (watcher == null) {
            return false;
        }
        List<Spell> spells = watcher.getSpellsCastThisTurn(source.getControllerId());
        return spells != null && spells
                .stream()
                .filter(Objects::nonNull)
                .filter(spell -> spell.isInstantOrSorcery(game))
                .anyMatch(spell -> !spell.getSourceId().equals(source.getSourceId()));
    }

    @Override
    public String toString() {
        return "you've cast another instant or sorcery spell this turn";
    }
}
