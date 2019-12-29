package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MoldervineReclamation extends CardImpl {

    public MoldervineReclamation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}{G}");

        // Whenever a creature you control dies, you gain 1 life and draw a card.
        Ability ability = new DiesCreatureTriggeredAbility(
                new GainLifeEffect(1), false,
                StaticFilters.FILTER_CONTROLLED_A_CREATURE
        );
        ability.addEffect(new DrawCardSourceControllerEffect(1).concatBy("and"));
        this.addAbility(ability);
    }

    private MoldervineReclamation(final MoldervineReclamation card) {
        super(card);
    }

    @Override
    public MoldervineReclamation copy() {
        return new MoldervineReclamation(this);
    }
}
