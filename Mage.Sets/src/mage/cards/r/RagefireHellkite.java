package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RagefireHellkite extends CardImpl {

    public RagefireHellkite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{R}");

        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Ragefire Hellkite attacks, you may sacrifice another creature. If you do, Ragefire Hellkite gains double strike until end of turn.
        this.addAbility(new AttacksTriggeredAbility(new DoIfCostPaid(
                new GainAbilitySourceEffect(DoubleStrikeAbility.getInstance(), Duration.EndOfTurn),
                new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT)
        )));
    }

    private RagefireHellkite(final RagefireHellkite card) {
        super(card);
    }

    @Override
    public RagefireHellkite copy() {
        return new RagefireHellkite(this);
    }
}
