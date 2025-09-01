package mage.cards.g;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.decorator.ConditionalContinuousRuleModifyingEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DontUntapInControllersUntapStepSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.RemoveCounterSourceEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
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
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
        this.getSpellAbility().addTarget(new GlyphOfDelusionSecondTarget());
        this.getSpellAbility().addEffect(new GlyphOfDelusionEffect());
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

    public GlyphOfDelusionSecondTarget() {
        super();
        withTargetName("target creature that target Wall blocked this turn");
    }

    private GlyphOfDelusionSecondTarget(final GlyphOfDelusionSecondTarget target) {
        super(target);
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        Set<UUID> possibleTargets = new HashSet<>();

        BlockedAttackerWatcher watcher = game.getState().getWatcher(BlockedAttackerWatcher.class);
        if (watcher == null) {
            return possibleTargets;
        }

        Permanent targetWall = game.getPermanent(source.getFirstTarget());
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, sourceControllerId, source, game)) {
            if (targetWall == null) {
                // playable or first target not yet selected
                // use all
                possibleTargets.add(permanent.getId());
            } else {
                // real
                // filter by blocked
                if (watcher.creatureHasBlockedAttacker(new MageObjectReference(permanent, game), new MageObjectReference(targetWall, game))) {
                    possibleTargets.add(permanent.getId());
                }
            }
        }
        possibleTargets.removeIf(id -> targetWall != null && targetWall.getId().equals(id));

        return keepValidPossibleTargets(possibleTargets, sourceControllerId, source, game);
    }

    @Override
    public boolean chooseTarget(Outcome outcome, UUID playerId, Ability source, Game game) {
        // AI hint with better outcome
        return super.chooseTarget(Outcome.Tap, playerId, source, game);
    }

    @Override
    public GlyphOfDelusionSecondTarget copy() {
        return new GlyphOfDelusionSecondTarget(this);
    }
}

class GlyphOfDelusionEffect extends OneShotEffect {

    GlyphOfDelusionEffect() {
        super(Outcome.Detriment);
        this.staticText = "Put X glyph counters on target creature that target Wall blocked this turn, where X is the power of that blocked creature. The creature gains \"This creature doesn't untap during your untap step if it has a glyph counter on it\" and \"At the beginning of your upkeep, remove a glyph counter from this creature.\"";
    }

    private GlyphOfDelusionEffect(final GlyphOfDelusionEffect effect) {
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

                SimpleStaticAbility ability = new SimpleStaticAbility(new ConditionalContinuousRuleModifyingEffect(new DontUntapInControllersUntapStepSourceEffect(),
                        new SourceHasCounterCondition(CounterType.GLYPH)).setText("This creature doesn't untap during your untap step if it has a glyph counter on it"));
                GainAbilityTargetEffect effect = new GainAbilityTargetEffect(ability, Duration.Custom);
                effect.setTargetPointer(new FixedTarget(targetPermanent.getId(), game));
                game.addEffect(effect, source);

                BeginningOfUpkeepTriggeredAbility ability2 = new BeginningOfUpkeepTriggeredAbility(new RemoveCounterSourceEffect(CounterType.GLYPH.createInstance()));
                GainAbilityTargetEffect effect2 = new GainAbilityTargetEffect(ability2, Duration.Custom);
                effect2.setTargetPointer(new FixedTarget(targetPermanent.getId(), game));
                game.addEffect(effect2, source);
            }
        }
        return false;
    }
}
