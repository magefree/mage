package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.AttacksIfAbleAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

/**
 *
 * @author LevelX2
 */
public final class HellraiserGoblin extends CardImpl {

    public HellraiserGoblin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.BERSERKER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Creatures you control have haste and attack each combat if able.
        Ability ability = new SimpleStaticAbility(new GainAbilityControlledEffect(
                HasteAbility.getInstance(),
                Duration.WhileOnBattlefield,
                StaticFilters.FILTER_PERMANENT_CREATURES
        ));
        ability.addEffect(new AttacksIfAbleAllEffect(StaticFilters.FILTER_CONTROLLED_CREATURES).setText("and attack each combat if able"));
        this.addAbility(ability);
    }

    private HellraiserGoblin(final HellraiserGoblin card) {
        super(card);
    }

    @Override
    public HellraiserGoblin copy() {
        return new HellraiserGoblin(this);
    }
}
