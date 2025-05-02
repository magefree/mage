package mage.cards.h;

import mage.MageInt;
import mage.abilities.common.DealsDamageToYouAllTriggeredAbility;
import mage.abilities.condition.common.SourceEnteredThisTurnCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.ExileUntilSourceLeavesEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class HixusPrisonWarden extends CardImpl {

    public HixusPrisonWarden(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Whenever a creature deals combat damage to you, if Hixus, Prison Warden entered the battlefield this turn, exile that creature until Hixus leaves the battlefield.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(new DealsDamageToYouAllTriggeredAbility(
                StaticFilters.FILTER_PERMANENT_CREATURE, new ExileUntilSourceLeavesEffect(), true
        ).setTriggerPhrase("Whenever a creature deals combat damage to you, if {this} entered the battlefield this turn, "),
                SourceEnteredThisTurnCondition.instance, null
        ));
    }

    private HixusPrisonWarden(final HixusPrisonWarden card) {
        super(card);
    }

    @Override
    public HixusPrisonWarden copy() {
        return new HixusPrisonWarden(this);
    }
}
