package mage.cards.r;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.hint.common.CardTypesInGraveyardHint;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;

import mage.filter.StaticFilters;

import mage.target.common.TargetControlledCreaturePermanent;

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
        this.addAbility(new ConditionalActivatedAbility(Zone.BATTLEFIELD,
                new BoostSourceEffect(2, 1, Duration.EndOfTurn),
                new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT),
                DeliriumCondition.instance,
                "<i>Delirium</i> &mdash; Sacrifice another creature: Reaper of Flight Moonsilver gets +2/+1 until end of turn. "
                        + "Activate only if there are four or more card types among cards in your graveyard.")
                .addHint(CardTypesInGraveyardHint.YOU));
    }

    private ReaperOfFlightMoonsilver(final ReaperOfFlightMoonsilver card) {
        super(card);
    }

    @Override
    public ReaperOfFlightMoonsilver copy() {
        return new ReaperOfFlightMoonsilver(this);
    }
}
