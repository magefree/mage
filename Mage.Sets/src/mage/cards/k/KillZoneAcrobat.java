package mage.cards.k;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
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
public final class KillZoneAcrobat extends CardImpl {

    public KillZoneAcrobat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever Kill-Zone Acrobat attacks, you may sacrifice another creature or artifact. If you do, Kill-Zone Acrobat gains flying until end of turn.
        this.addAbility(new AttacksTriggeredAbility(new DoIfCostPaid(
                new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.EndOfTurn),
                new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_ANOTHER_ARTIFACT_OR_CREATURE_SHORT_TEXT)
        )));
    }

    private KillZoneAcrobat(final KillZoneAcrobat card) {
        super(card);
    }

    @Override
    public KillZoneAcrobat copy() {
        return new KillZoneAcrobat(this);
    }
}
