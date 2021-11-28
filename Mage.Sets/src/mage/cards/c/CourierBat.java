package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.YouGainedLifeCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;
import mage.watchers.common.PlayerGainedLifeWatcher;

/**
 *
 * @author weirddan455
 */
public final class CourierBat extends CardImpl {

    public CourierBat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.BAT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Courier Bat enters the battlefield, if you gained life this turn, return up to one target creature card from your graveyard to your hand.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new ReturnFromGraveyardToHandTargetEffect()),
                new YouGainedLifeCondition(ComparisonType.MORE_THAN, 0),
                "When {this} enters the battlefield, if you gained life this turn, return up to one target creature card from your graveyard to your hand."
        );
        ability.addTarget(new TargetCardInYourGraveyard(0, 1, StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        this.addAbility(ability, new PlayerGainedLifeWatcher());
    }

    private CourierBat(final CourierBat card) {
        super(card);
    }

    @Override
    public CourierBat copy() {
        return new CourierBat(this);
    }
}
