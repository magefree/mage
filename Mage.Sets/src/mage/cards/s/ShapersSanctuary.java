package mage.cards.s;

import mage.abilities.common.BecomesTargetAnyTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class ShapersSanctuary extends CardImpl {

    public ShapersSanctuary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{G}");

        // Whenever a creature you control becomes the target of a spell or ability an opponent controls, you may draw a card.
        this.addAbility(new BecomesTargetAnyTriggeredAbility(new DrawCardSourceControllerEffect(1),
                StaticFilters.FILTER_CONTROLLED_A_CREATURE, StaticFilters.FILTER_SPELL_OR_ABILITY_OPPONENTS,
                SetTargetPointer.NONE, true));
    }

    private ShapersSanctuary(final ShapersSanctuary card) {
        super(card);
    }

    @Override
    public ShapersSanctuary copy() {
        return new ShapersSanctuary(this);
    }
}
