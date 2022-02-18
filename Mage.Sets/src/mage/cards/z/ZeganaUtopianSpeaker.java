package mage.cards.z;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.AdaptAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ZeganaUtopianSpeaker extends CardImpl {

    public ZeganaUtopianSpeaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Zegana, Utopian Speaker enters the battlefield, if you control another creature with a +1/+1 counter on it, draw a card.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(
                        new DrawCardSourceControllerEffect(1), false
                ), new PermanentsOnTheBattlefieldCondition(StaticFilters.FILTER_OTHER_CONTROLLED_CREATURE_P1P1),
                "When {this} enters the battlefield, " +
                        "if you control another creature " +
                        "with a +1/+1 counter on it, draw a card."
                )
        );

        // {4}{G}{U}: Adapt 4.
        this.addAbility(new AdaptAbility(4, "{4}{G}{U}"));

        // Each creature you control with a +1/+1 counter on it has trample.
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new GainAbilityAllEffect(
                        TrampleAbility.getInstance(), Duration.WhileOnBattlefield,
                        StaticFilters.FILTER_EACH_CONTROLLED_CREATURE_P1P1)
                )
        );
    }

    private ZeganaUtopianSpeaker(final ZeganaUtopianSpeaker card) {
        super(card);
    }

    @Override
    public ZeganaUtopianSpeaker copy() {
        return new ZeganaUtopianSpeaker(this);
    }
}
