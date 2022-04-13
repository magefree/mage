package mage.cards.o;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.Effects;
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

/**
 *
 * @author weirddan455
 */
public final class ObscuraAscendancy extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.SPIRIT, "Spirits");

    public ObscuraAscendancy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{W}{U}{B}");

        // Whenever you cast a spell, if its mana value is equal to the number of soul counters on Obscura Ascendancy plus one, put a soul counter on Obscura Ascendancy, then create a 2/2 white Spirit creature token with flying.
        SpellCastControllerTriggeredAbility triggeredAbility = new SpellCastControllerTriggeredAbility(new AddCountersSourceEffect(CounterType.SOUL.createInstance()), false);
        triggeredAbility.addEffect(new CreateTokenEffect(new Spirit22Token()));
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(triggeredAbility, ObscuraAscendancyCondition.instance,
                "Whenever you cast a spell, if its mana value is equal to the number of soul counters on {this} plus one, put a soul counter on {this}, then create a 2/2 white Spirit creature token with flying."
        ));

        // As long as Obscura Ascendancy has five or more soul counters on it, Spirits you control get +3/+3.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostControlledEffect(3, 3, Duration.WhileOnBattlefield, filter),
                new SourceHasCounterCondition(CounterType.SOUL, 5),
                "as long as {this} has five or more soul counters on it, Spirits you control get +3/+3"
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
        if (permanent != null) {
            Effects effects = source.getEffects();
            if (!effects.isEmpty()) {
                Object spellCast = effects.get(0).getValue("spellCast");
                if (spellCast instanceof Spell) {
                    return ((Spell) spellCast).getManaValue() == permanent.getCounters(game).getCount(CounterType.SOUL) + 1;
                }
            }
        }
        return false;
    }
}
