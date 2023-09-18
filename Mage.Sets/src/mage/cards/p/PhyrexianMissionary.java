package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.keyword.KickerAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PhyrexianMissionary extends CardImpl {

    public PhyrexianMissionary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Kicker {1}{B}
        this.addAbility(new KickerAbility("{1}{B}"));

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // When Phyrexian Missionary enters the battlefield, if it was kicked, return target creature card from your graveyard to your hand.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new ReturnFromGraveyardToHandTargetEffect()),
                KickedCondition.ONCE, "When {this} enters the battlefield, if it was kicked, " +
                "return target creature card from your graveyard to your hand."
        );
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        this.addAbility(ability);
    }

    private PhyrexianMissionary(final PhyrexianMissionary card) {
        super(card);
    }

    @Override
    public PhyrexianMissionary copy() {
        return new PhyrexianMissionary(this);
    }
}
