package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.ProwessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.watchers.common.SpellsCastWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LyseHext extends CardImpl {

    private static final FilterCard filter = new FilterCard("noncreature spells");
    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
    }

    public LyseHext(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.REBEL);
        this.subtype.add(SubType.MONK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Prowess
        this.addAbility(new ProwessAbility());

        // Noncreature spells you cast cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(new SpellsCostReductionControllerEffect(filter, 1)));

        // As long as you've cast two or more noncreature spells this turn, Lyse Hext has double strike.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(
                        DoubleStrikeAbility.getInstance(), Duration.WhileOnBattlefield
                ), LyseHextCondition.instance, "as long as you've cast two or more " +
                "noncreature spells this turn, {this} has double strike"
        )).addHint(LyseHextValue.getHint()));
    }

    private LyseHext(final LyseHext card) {
        super(card);
    }

    @Override
    public LyseHext copy() {
        return new LyseHext(this);
    }
}

enum LyseHextCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return LyseHextValue.instance.calculate(game, source, null) >= 2;
    }
}

enum LyseHextValue implements DynamicValue {
    instance;
    private static final Hint hint = new ValueHint("Noncreature spells you've cast this turn", instance);

    public static Hint getHint() {
        return hint;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return game
                .getState()
                .getWatcher(SpellsCastWatcher.class)
                .getSpellsCastThisTurn(sourceAbility.getControllerId())
                .stream()
                .mapToInt(spell -> spell.isCreature(game) ? 0 : 1)
                .sum();
    }

    @Override
    public LyseHextValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "";
    }

    @Override
    public String toString() {
        return "1";
    }
}
