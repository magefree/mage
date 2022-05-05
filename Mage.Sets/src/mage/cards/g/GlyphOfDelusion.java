package mage.cards.g;

import mage.MageObject;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.decorator.ConditionalContinuousRuleModifyingEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DontUntapInControllersUntapStepSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.RemoveCounterSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.common.BlockedAttackerWatcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author L_J
 */
public final class GlyphOfDelusion extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Wall creature");

    static {
        filter.add(SubType.WALL.getPredicate());
    }

    public GlyphOfDelusion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}");

        // Put X glyph counters on target creature that target Wall blocked this turn, where X is the power of that blocked creature. The creature gains “This creature doesn’t untap during your untap step if it has a glyph counter on it” and “At the beginning of your upkeep, remove a glyph counter from this creature.”
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
        this.getSpellAbility().addTarget(new GlyphOfDelusionSecondTarget());
        this.getSpellAbility().addEffect(new GlyphOfDelusionEffect());
        this.getSpellAbility().addWatcher(new BlockedAttackerWatcher());
    }

    private GlyphOfDelusion(final GlyphOfDelusion card) {
        super(card);
    }

    @Override
    public GlyphOfDelusion copy() {
        return new GlyphOfDelusion(this);
    }
}

class GlyphOfDelusionSecondTarget extends TargetPermanent {

    private Permanent firstTarget = null;

    public GlyphOfDelusionSecondTarget() {
        super();
        setTargetName("target creature that target Wall blocked this turn");
    }

    public GlyphOfDelusionSecondTarget(final GlyphOfDelusionSecondTarget target) {
        super(target);
        this.firstTarget = target.firstTarget;
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        Set<UUID> possibleTargets = new HashSet<>();
        if (firstTarget != null) {
            BlockedAttackerWatcher watcher = game.getState().getWatcher(BlockedAttackerWatcher.class);
            if (watcher != null) {
                MageObject targetSource = game.getObject(source);
                if (targetSource != null) {
                    for (Permanent creature : game.getBattlefield().getActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, sourceControllerId, source, game)) {
                        if (!targets.containsKey(creature.getId()) && creature.canBeTargetedBy(targetSource, sourceControllerId, game)) {
                            if (watcher.creatureHasBlockedAttacker(new MageObjectReference(creature, game), new MageObjectReference(firstTarget, game), game)) {
                                possibleTargets.add(creature.getId());
                            }
                        }
                    }
                }
            }
        }
        return possibleTargets;
    }

    @Override
    public boolean chooseTarget(Outcome outcome, UUID playerId, Ability source, Game game) {
        firstTarget = game.getPermanent(source.getFirstTarget());
        return super.chooseTarget(Outcome.Tap, playerId, source, game);
    }

    @Override
    public GlyphOfDelusionSecondTarget copy() {
        return new GlyphOfDelusionSecondTarget(this);
    }
}

class GlyphOfDelusionEffect extends OneShotEffect {

    public GlyphOfDelusionEffect() {
        super(Outcome.Detriment);
        this.staticText = "Put X glyph counters on target creature that target Wall blocked this turn, where X is the power of that blocked creature. The creature gains \"This creature doesn't untap during your untap step if it has a glyph counter on it\" and \"At the beginning of your upkeep, remove a glyph counter from this creature.\"";
    }

    public GlyphOfDelusionEffect(final GlyphOfDelusionEffect effect) {
        super(effect);
    }

    @Override
    public GlyphOfDelusionEffect copy() {
        return new GlyphOfDelusionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (source.getTargets().get(1) != null) {
            Permanent targetPermanent = game.getPermanent(source.getTargets().get(1).getFirstTarget());
            if (targetPermanent != null) {
                targetPermanent.addCounters(CounterType.GLYPH.createInstance(targetPermanent.getPower().getValue()), source.getControllerId(), source, game);

                SimpleStaticAbility ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousRuleModifyingEffect(new DontUntapInControllersUntapStepSourceEffect(),
                        new SourceHasCounterCondition(CounterType.GLYPH)).setText("This creature doesn't untap during your untap step if it has a glyph counter on it"));
                GainAbilityTargetEffect effect = new GainAbilityTargetEffect(ability, Duration.Custom);
                effect.setTargetPointer(new FixedTarget(targetPermanent.getId(), game));
                game.addEffect(effect, source);

                BeginningOfUpkeepTriggeredAbility ability2 = new BeginningOfUpkeepTriggeredAbility(new RemoveCounterSourceEffect(CounterType.GLYPH.createInstance()),
                        TargetController.YOU, false);
                GainAbilityTargetEffect effect2 = new GainAbilityTargetEffect(ability2, Duration.Custom);
                effect2.setTargetPointer(new FixedTarget(targetPermanent.getId(), game));
                game.addEffect(effect2, source);
            }
        }
        return false;
    }
}
