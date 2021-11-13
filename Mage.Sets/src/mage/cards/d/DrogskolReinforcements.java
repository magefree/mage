package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.PreventAllNonCombatDamageToAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.MeleeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DrogskolReinforcements extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledPermanent(SubType.SPIRIT, "Spirits you control");

    public DrogskolReinforcements(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Melee
        this.addAbility(new MeleeAbility());

        // Other Spirits you control have melee.
        this.addAbility(new SimpleStaticAbility(new GainAbilityAllEffect(
                new MeleeAbility(), Duration.WhileOnBattlefield, filter, true
        )));

        // Prevent all noncombat damage that would be dealt to Spirits you control.
        this.addAbility(new SimpleStaticAbility(new PreventAllNonCombatDamageToAllEffect(
                Duration.WhileOnBattlefield, StaticFilters.FILTER_CONTROLLED_CREATURES
        )));
    }

    private DrogskolReinforcements(final DrogskolReinforcements card) {
        super(card);
    }

    @Override
    public DrogskolReinforcements copy() {
        return new DrogskolReinforcements(this);
    }
}
