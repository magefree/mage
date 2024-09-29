package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledEnchantmentPermanent;
import mage.filter.common.FilterControlledPermanent;

/**
 *
 * @author TheElk801
 */
public final class EndlessWurm extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledEnchantmentPermanent("an enchantment");

    public EndlessWurm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");

        this.subtype.add(SubType.WURM);
        this.power = new MageInt(9);
        this.toughness = new MageInt(9);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // At the beginning of your upkeep, sacrifice Endless Wurm unless you sacrifice an enchantment.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new SacrificeSourceUnlessPaysEffect(new SacrificeTargetCost(filter)), TargetController.YOU, false));

    }

    private EndlessWurm(final EndlessWurm card) {
        super(card);
    }

    @Override
    public EndlessWurm copy() {
        return new EndlessWurm(this);
    }
}
