package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.SurgedCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.SurgeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

/**
 *
 * @author fireshoes
 */
public final class RecklessBushwhacker extends CardImpl {

    public RecklessBushwhacker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Surge {1}{R} (You may cast this spell for its surge cost if you or a teammate has cast another spell this turn)
        addAbility(new SurgeAbility(this, "{1}{R}"));

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // When Reckless Bushwhacker enters the battlefield, if its surge cost was paid, other creatures you control get +1/+0 and gain haste until end of turn.
        EntersBattlefieldTriggeredAbility ability = new EntersBattlefieldTriggeredAbility(new BoostControlledEffect(1, 0, Duration.EndOfTurn, true), false);
        ability.addEffect(new GainAbilityControlledEffect(HasteAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_OTHER_CONTROLLED_CREATURES, true));
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, SurgedCondition.instance,
                "When {this} enters the battlefield, if its surge cost was paid, other creatures you control get +1/+0 and gain haste until end of turn."));

    }

    private RecklessBushwhacker(final RecklessBushwhacker card) {
        super(card);
    }

    @Override
    public RecklessBushwhacker copy() {
        return new RecklessBushwhacker(this);
    }
}
