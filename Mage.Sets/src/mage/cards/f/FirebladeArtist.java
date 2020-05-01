package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetOpponentOrPlaneswalker;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FirebladeArtist extends CardImpl {

    public FirebladeArtist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // At the beginning of your upkeep, you may sacrifice a creature. When you do, Fireblade Artist deals 2 damage to target opponent or planeswalker.
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new DamageTargetEffect(2), false,
                "{this} deals 2 damage to target opponent or planeswalker"
        );
        ability.addTarget(new TargetOpponentOrPlaneswalker());
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new DoWhenCostPaid(ability, new SacrificeTargetCost(
                        new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT)
                ), "Sacrifice a creature?"), TargetController.YOU, false
        ));
    }

    private FirebladeArtist(final FirebladeArtist card) {
        super(card);
    }

    @Override
    public FirebladeArtist copy() {
        return new FirebladeArtist(this);
    }
}
