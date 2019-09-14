package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.common.AttacksAllTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RevengeOfRavens extends CardImpl {

    public RevengeOfRavens(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}");

        // Whenever a creature attacks you or a planeswalker you control, that creature's controller loses 1 life and you gain 1 life.
        Ability ability = new AttacksAllTriggeredAbility(
                new LoseLifeTargetEffect(1).setText("that creature's controller loses 1 life"),
                false, StaticFilters.FILTER_PERMANENT_CREATURE,
                SetTargetPointer.PLAYER, true, true
        );
        ability.addEffect(new GainLifeEffect(1).concatBy("and"));
        this.addAbility(ability);
    }

    private RevengeOfRavens(final RevengeOfRavens card) {
        super(card);
    }

    @Override
    public RevengeOfRavens copy() {
        return new RevengeOfRavens(this);
    }
}
