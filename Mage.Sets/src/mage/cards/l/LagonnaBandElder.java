
package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class LagonnaBandElder extends CardImpl {

    public LagonnaBandElder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");
        this.subtype.add(SubType.CENTAUR);
        this.subtype.add(SubType.ADVISOR);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Lagonna-Band Elder enters the battlefield, if you control an enchantment, you gain 3 life.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new GainLifeEffect(3), false),
                new PermanentsOnTheBattlefieldCondition(StaticFilters.FILTER_PERMANENT_ENCHANTMENT),
                "When Lagonna-Band Elder enters the battlefield, if you control an enchantment, you gain 3 life");
        this.addAbility(ability);
    }

    private LagonnaBandElder(final LagonnaBandElder card) {
        super(card);
    }

    @Override
    public LagonnaBandElder copy() {
        return new LagonnaBandElder(this);
    }
}
