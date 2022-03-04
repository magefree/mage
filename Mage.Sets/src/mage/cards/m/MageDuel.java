package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.common.FightTargetsEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.watchers.common.SpellsCastWatcher;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MageDuel extends CardImpl {

    private static final Hint hint = new ConditionHint(
            MageDuelCondition.instance, "You've cast an instant or sorcery this turn"
    );

    public MageDuel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}");

        // This spell costs {2} less to cast if you've cast another instant or sorcery spell this turn.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL,
                new SpellCostReductionSourceEffect(2, MageDuelCondition.instance).setCanWorksOnStackOnly(true)
        ).setRuleAtTheTop(true), new SpellsCastWatcher());

        // Target creature you control gets +1/+2 until end of turn. Then it fights target creature you don't control.
        this.getSpellAbility().addEffect(new BoostTargetEffect(1, 2));
        this.getSpellAbility().addEffect(new FightTargetsEffect().setText(
                "Then it fights target creature you don't control. " +
                "<i>(Each deals damage equal to its power to the other.)</i>"));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL));
        this.getSpellAbility().addWatcher(new SpellsCastWatcher());
    }

    private MageDuel(final MageDuel card) {
        super(card);
    }

    @Override
    public MageDuel copy() {
        return new MageDuel(this);
    }
}

enum MageDuelCondition implements Condition {
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
                .map(Spell::getSourceId)
                .anyMatch(source.getSourceId()::equals);
    }

    @Override
    public String toString() {
        return "you've cast another instant or sorcery spell this turn";
    }
}
