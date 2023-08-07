package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.common.AttacksAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.FirstCombatPhaseCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.AdditionalCombatPhaseEffect;
import mage.abilities.effects.common.UntapAllEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.ForMirrodinAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class HexplateWallbreaker extends CardImpl {

    public HexplateWallbreaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}{R}{R}");
        this.subtype.add(SubType.EQUIPMENT);

        // For Mirrodin!
        this.addAbility(new ForMirrodinAbility());

        // Equipped creature gets +2/+2.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(2, 2)));

        // Whenever equipped creature attacks, if it's the first combat phase of the turn, untap
        // each attacking creature. After this phase, there is an additional combat phase.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new AttacksAttachedTriggeredAbility(new UntapAllEffect(StaticFilters.FILTER_ATTACKING_CREATURES)),
                FirstCombatPhaseCondition.instance,
                "Whenever equipped creature attacks, if it's the first combat phase of the turn, untap " +
                        "each attacking creature. After this phase, there is an additional combat phase"
        );
        ability.addEffect(new AdditionalCombatPhaseEffect());
        this.addAbility(ability);

        // Equip {3}{R}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new ManaCostsImpl<>("{3}{R}"), false));
    }

    private HexplateWallbreaker(final HexplateWallbreaker card) {
        super(card);
    }

    @Override
    public HexplateWallbreaker copy() {
        return new HexplateWallbreaker(this);
    }
}
