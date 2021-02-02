package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BrawlBashOgre extends CardImpl {

    public BrawlBashOgre(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{R}");

        this.subtype.add(SubType.OGRE);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Menace
        this.addAbility(new MenaceAbility());

        // Whenever Brawl-Bash Ogre attacks, you may sacrifice another creature. If you do, Brawl-Bash Ogre gets +2/+2 until end of turn.
        this.addAbility(new AttacksTriggeredAbility(
                new DoIfCostPaid(
                        new BoostSourceEffect(2, 2, Duration.EndOfTurn),
                        new SacrificeTargetCost(new TargetControlledPermanent(
                                StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE
                        ))
                ), false
        ));
    }

    private BrawlBashOgre(final BrawlBashOgre card) {
        super(card);
    }

    @Override
    public BrawlBashOgre copy() {
        return new BrawlBashOgre(this);
    }
}
