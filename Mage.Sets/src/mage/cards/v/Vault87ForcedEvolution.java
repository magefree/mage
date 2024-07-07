package mage.cards.v;

import java.util.UUID;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.Effects;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.AddCardSubTypeTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.constants.Duration;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author Cguy7777
 */
public final class Vault87ForcedEvolution extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("non-Mutant creature");
    private static final Hint hint = new ValueHint(
            "Highest power among Mutants you control", Vault87ForcedEvolutionValue.instance);

    static {
        filter.add(Predicates.not(SubType.MUTANT.getPredicate()));
    }

    public Vault87ForcedEvolution(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}{U}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I -- Gain control of target non-Mutant creature for as long as you control Vault 87.
        sagaAbility.addChapterEffect(
                this,
                SagaChapter.CHAPTER_I,
                new GainControlTargetEffect(Duration.WhileControlled),
                new TargetCreaturePermanent(filter));

        // II -- Put a +1/+1 counter on target creature you control. It becomes a Mutant in addition to its other types.
        sagaAbility.addChapterEffect(
                this,
                SagaChapter.CHAPTER_II,
                new Effects(
                        new AddCountersTargetEffect(CounterType.P1P1.createInstance()),
                        new AddCardSubTypeTargetEffect(SubType.MUTANT, Duration.Custom)
                                .setText("It becomes a Mutant in addition to its other types")),
                new TargetControlledCreaturePermanent());

        // III -- Draw cards equal to the greatest power among Mutants you control.
        sagaAbility.addChapterEffect(
                this,
                SagaChapter.CHAPTER_III,
                new DrawCardSourceControllerEffect(Vault87ForcedEvolutionValue.instance)
                        .setText("draw cards equal to the greatest power among Mutants you control"));
        this.addAbility(sagaAbility.addHint(hint));
    }

    private Vault87ForcedEvolution(final Vault87ForcedEvolution card) {
        super(card);
    }

    @Override
    public Vault87ForcedEvolution copy() {
        return new Vault87ForcedEvolution(this);
    }
}

enum Vault87ForcedEvolutionValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return game.getBattlefield()
                .getAllActivePermanents(sourceAbility.getControllerId())
                .stream()
                .filter(permanent1 -> permanent1.isCreature(game))
                .filter(permanent -> permanent.hasSubtype(SubType.MUTANT, game))
                .map(MageObject::getPower)
                .mapToInt(MageInt::getValue)
                .max()
                .orElse(0);
    }

    @Override
    public DynamicValue copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return "";
    }
}
