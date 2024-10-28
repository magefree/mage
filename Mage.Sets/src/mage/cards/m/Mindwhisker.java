package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.ThresholdCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Mindwhisker extends CardImpl {

    public Mindwhisker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.RAT);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // At the beginning of your upkeep, surveil 1.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new SurveilEffect(1), false
        ));

        // Threshold -- As long as seven or more cards are in your graveyard, creatures your opponents control get -1/-0.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostAllEffect(
                        -1, 0, Duration.WhileOnBattlefield,
                        StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE, false
                ), ThresholdCondition.instance, "as long as seven or more cards " +
                "are in your graveyard, creatures your opponents control get -1/-0"
        )).setAbilityWord(AbilityWord.THRESHOLD));
    }

    private Mindwhisker(final Mindwhisker card) {
        super(card);
    }

    @Override
    public Mindwhisker copy() {
        return new Mindwhisker(this);
    }
}
