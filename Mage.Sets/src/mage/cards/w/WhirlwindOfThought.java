package mage.cards.w;

import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WhirlwindOfThought extends CardImpl {

    public WhirlwindOfThought(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}{R}{W}");

        // Whenever you cast a noncreature spell, draw a card.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new DrawCardSourceControllerEffect(1),
                StaticFilters.FILTER_SPELL_A_NON_CREATURE, false
        ));
    }

    private WhirlwindOfThought(final WhirlwindOfThought card) {
        super(card);
    }

    @Override
    public WhirlwindOfThought copy() {
        return new WhirlwindOfThought(this);
    }
}
