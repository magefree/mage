package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.condition.common.OpponentsTurnCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.continuous.CastAsThoughItHadFlashAllEffect;
import mage.abilities.hint.common.OpponentsTurnHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BreathOfTheSleepless extends CardImpl {

    private static final FilterCard filter = new FilterCard("Spirit spells");

    static {
        filter.add(SubType.SPIRIT.getPredicate());
    }

    public BreathOfTheSleepless(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{U}");

        // You may cast Spirit spells as though they had flash.
        this.addAbility(new SimpleStaticAbility(
                new CastAsThoughItHadFlashAllEffect(Duration.WhileOnBattlefield, filter)
        ));

        // Whenever you cast a creature spell during an opponent's turn, tap up to one target creature.
        Ability ability = new ConditionalTriggeredAbility(new SpellCastControllerTriggeredAbility(
                new TapTargetEffect(), StaticFilters.FILTER_SPELL_A_CREATURE, false
        ), OpponentsTurnCondition.instance, "Whenever you cast a creature spell " +
                "during an opponent's turn, tap up to one target creature.");
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.addAbility(ability.addHint(OpponentsTurnHint.instance));
    }

    private BreathOfTheSleepless(final BreathOfTheSleepless card) {
        super(card);
    }

    @Override
    public BreathOfTheSleepless copy() {
        return new BreathOfTheSleepless(this);
    }
}
