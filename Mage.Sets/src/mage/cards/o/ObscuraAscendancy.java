package mage.cards.o;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Spirit22Token;
import mage.game.stack.Spell;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author weirddan455
 */
public final class ObscuraAscendancy extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.SPIRIT, "Spirits");

    public ObscuraAscendancy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{W}{U}{B}");

        // Whenever you cast a spell, if its mana value is equal to the number of soul counters on Obscura Ascendancy plus one, put a soul counter on Obscura Ascendancy, then create a 2/2 white Spirit creature token with flying.
        Ability ability = new SpellCastControllerTriggeredAbility(
                new AddCountersSourceEffect(CounterType.SOUL.createInstance()), false
        ).withInterveningIf(ObscuraAscendancyCondition.instance);
        ability.addEffect(new CreateTokenEffect(new Spirit22Token()).concatBy(", then"));
        this.addAbility(ability);

        // As long as Obscura Ascendancy has five or more soul counters on it, Spirits you control get +3/+3.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostControlledEffect(3, 3, Duration.WhileOnBattlefield, filter),
                new SourceHasCounterCondition(CounterType.SOUL, 5),
                "as long as there are five or more soul counters on {this}, Spirits you control get +3/+3"
        )));
    }

    private ObscuraAscendancy(final ObscuraAscendancy card) {
        super(card);
    }

    @Override
    public ObscuraAscendancy copy() {
        return new ObscuraAscendancy(this);
    }
}

enum ObscuraAscendancyCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentOrLKI(game);
        return permanent != null
                && CardUtil
                .getEffectValueFromAbility(source, "spellCast", Spell.class)
                .map(Spell::getManaValue)
                .filter(x -> x == permanent.getCounters(game).getCount(CounterType.SOUL) + 1)
                .isPresent();
    }

    @Override
    public String toString() {
        return "its mana value is equal to 1 plus the number of soul counters on {this}";
    }
}
