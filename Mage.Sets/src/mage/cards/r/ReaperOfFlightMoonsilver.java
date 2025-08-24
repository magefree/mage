package mage.cards.r;

import mage.MageInt;
import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.dynamicvalue.common.CardTypesInGraveyardCount;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class ReaperOfFlightMoonsilver extends CardImpl {

    public ReaperOfFlightMoonsilver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");
        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // <i>Delirium</i> &mdash; Sacrifice another creature: Reaper of Flight Moonsilver gets +2/+1 until end of turn.
        // Activate this ability only if there are four or more card types among cards in your graveyard.
        this.addAbility(new ActivateIfConditionActivatedAbility(
                new BoostSourceEffect(2, 1, Duration.EndOfTurn),
                new SacrificeTargetCost(StaticFilters.FILTER_ANOTHER_CREATURE), DeliriumCondition.instance
        ).setAbilityWord(AbilityWord.DELIRIUM).addHint(CardTypesInGraveyardCount.YOU.getHint()));
    }

    private ReaperOfFlightMoonsilver(final ReaperOfFlightMoonsilver card) {
        super(card);
    }

    @Override
    public ReaperOfFlightMoonsilver copy() {
        return new ReaperOfFlightMoonsilver(this);
    }
}
