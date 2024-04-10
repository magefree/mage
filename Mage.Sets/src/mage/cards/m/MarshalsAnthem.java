
package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.MultikickerCount;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.MultikickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetadjustment.TargetsCountAdjuster;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class MarshalsAnthem extends CardImpl {

    private static final String rule = "return up to X target creature cards from your graveyard to the battlefield, " +
            "where X is the number of times {this} was kicked";

    public MarshalsAnthem(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}{W}");

        // Multikicker {1}{W}
        this.addAbility(new MultikickerAbility("{1}{W}"));

        // Creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD, new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield)
        ));

        // When Marshal's Anthem enters the battlefield, return up to X target creature cards from your graveyard to the battlefield, where X is the number of times Marshal's Anthem was kicked.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new ReturnFromGraveyardToBattlefieldTargetEffect().setText(rule), false
        );
        ability.setTargetAdjuster(new TargetsCountAdjuster(MultikickerCount.instance));
        ability.addTarget(new TargetCardInYourGraveyard(0, 1, StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        this.addAbility(ability);
    }

    private MarshalsAnthem(final MarshalsAnthem card) {
        super(card);
    }

    @Override
    public MarshalsAnthem copy() {
        return new MarshalsAnthem(this);
    }
}
