package mage.cards.w;

import java.util.UUID;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.watchers.common.SpellsCastWatcher;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.dynamicvalue.common.TargetPermanentPowerCount;
import mage.abilities.dynamicvalue.common.TargetPermanentToughnessCount;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effects;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SagaChapter;
import mage.constants.SubLayer;

/**
 *
 * @author muz
 */
public final class WorldWarHulk extends CardImpl {

    public WorldWarHulk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}{G}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I -- The next red or green creature spell you cast this turn can be cast without paying its mana cost.
        sagaAbility.addChapterEffect(
            this, SagaChapter.CHAPTER_I, new WorldWarHulkEffect()
        );

        // II -- Put three +1/+1 counters on target creature you control.
        sagaAbility.addChapterEffect(
            this, SagaChapter.CHAPTER_II,
            new AddCountersTargetEffect(CounterType.P1P1.createInstance(3)),
            new TargetControlledCreaturePermanent()
        );

        // III -- Choose target creature you control. Until end of turn, double its power and toughness and it gains trample.
        sagaAbility.addChapterEffect(
            this, SagaChapter.CHAPTER_III,
            new Effects(
                new BoostTargetEffect(TargetPermanentPowerCount.instance, TargetPermanentToughnessCount.instance)
                    .setText("choose target creature you control. Until end of turn, double its power and toughness"),
                new GainAbilityTargetEffect(TrampleAbility.getInstance())
                    .setText("and it gains trample")
            ),
            new TargetControlledCreaturePermanent()
        );

        this.addAbility(sagaAbility);
    }

    private WorldWarHulk(final WorldWarHulk card) {
        super(card);
    }

    @Override
    public WorldWarHulk copy() {
        return new WorldWarHulk(this);
    }
}

class WorldWarHulkEffect extends OneShotEffect {

    WorldWarHulkEffect() {
        super(Outcome.Benefit);
        staticText = "the next red or green creature spell you cast this turn can be cast without paying its mana cost";
    }

    private WorldWarHulkEffect(final WorldWarHulkEffect effect) {
        super(effect);
    }

    @Override
    public WorldWarHulkEffect copy() {
        return new WorldWarHulkEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        game.addEffect(new WorldWarHulkCastWithoutManaEffect(), source);
        return true;
    }
}

class WorldWarHulkCastWithoutManaEffect extends ContinuousEffectImpl {

    class WorldWarHulkCondition implements Condition {
        private final int spellCastCount;

        private WorldWarHulkCondition(int spellCastCount) {
            this.spellCastCount = spellCastCount;
        }

        @Override
        public boolean apply(Game game, Ability source) {
            SpellsCastWatcher watcher = game.getState().getWatcher(SpellsCastWatcher.class);
            if (watcher != null) {
                long count = watcher.getSpellsCastThisTurn(source.getControllerId()).stream()
                        .filter(spell -> filter.match(spell, game))
                        .count();
                return count == spellCastCount;
            }
            return false;
        }
    }

    private static final FilterCard filter = new FilterCard("red or green creature spell");

    static {
        filter.add(CardType.CREATURE.getPredicate());
        filter.add(Predicates.or(
            new ColorPredicate(ObjectColor.RED),
            new ColorPredicate(ObjectColor.GREEN)
        ));
    }

    private int spellCastCount;
    private AlternativeCostSourceAbility alternativeCostSourceAbility;

    WorldWarHulkCastWithoutManaEffect() {
        super(Duration.EndOfTurn, Layer.RulesEffects, SubLayer.NA, Outcome.PlayForFree);
        staticText = "The next red or green creature spell you cast this turn can be cast without paying its mana cost";
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        SpellsCastWatcher watcher = game.getState().getWatcher(SpellsCastWatcher.class);
        if (watcher != null) {
            spellCastCount = (int) watcher.getSpellsCastThisTurn(source.getControllerId()).stream()
                    .filter(spell -> filter.match(spell, game))
                    .count();
            Condition condition = new WorldWarHulkCondition(spellCastCount);
            alternativeCostSourceAbility = new AlternativeCostSourceAbility(
                    null, condition, null, filter, true
            );
        }
    }

    private WorldWarHulkCastWithoutManaEffect(final WorldWarHulkCastWithoutManaEffect effect) {
        super(effect);
        this.used = effect.used;
        this.spellCastCount = effect.spellCastCount;
        this.alternativeCostSourceAbility = effect.alternativeCostSourceAbility;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        alternativeCostSourceAbility.setSourceId(source.getSourceId());
        controller.getAlternativeSourceCosts().add(alternativeCostSourceAbility);
        return true;
    }

    @Override
    public WorldWarHulkCastWithoutManaEffect copy() {
        return new WorldWarHulkCastWithoutManaEffect(this);
    }
}
