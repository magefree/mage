/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.sets.tempest;


import java.util.UUID;

import mage.constants.CardType;
import mage.constants.Rarity;
import mage.abilities.Ability;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.constants.Zone;
import mage.filter.common.FilterArtifactCard;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author Trevor
 */

public class RootwaterDiver extends CardImpl {
    public RootwaterDiver(UUID ownerId) {
        super(ownerId, 81, "Rootwater Diver", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{U}");
      this.expansionSetCode = "TMP";
        this.subtype.add("Merfolk");
        this.color.setBlue(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.addAbility(new ColorlessManaAbility());
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ReturnToHandTargetEffect(), new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetCardInYourGraveyard(new FilterArtifactCard("artifact card from your graveyard")));
        this.addAbility(ability);

    }
        public RootwaterDiver(final RootwaterDiver card) {
        super(card);
    }

    @Override
    public RootwaterDiver copy() {
        return new RootwaterDiver(this);
    }
}