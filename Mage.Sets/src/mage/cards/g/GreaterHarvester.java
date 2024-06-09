package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.SacrificeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class GreaterHarvester extends CardImpl {

    public GreaterHarvester(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}{B}{B}");
        this.subtype.add(SubType.HORROR);

        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        // At the beginning of your upkeep, sacrifice a permanent.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new SacrificeEffect(StaticFilters.FILTER_PERMANENT_A, 1, ""), TargetController.YOU, false));

        //Whenever Greater Harvester deals combat damage to a player, that player sacrifices two permanents.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new SacrificeEffect(StaticFilters.FILTER_PERMANENTS, 2, "that player"), false, true));
    }

    private GreaterHarvester(final GreaterHarvester card) {
        super(card);
    }

    @Override
    public GreaterHarvester copy() {
        return new GreaterHarvester(this);
    }
}
