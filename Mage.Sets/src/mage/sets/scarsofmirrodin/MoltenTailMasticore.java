/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */

package mage.sets.scarsofmirrodin;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetCreatureOrPlayer;

/**
 *
 * @author Loki
 */
public class MoltenTailMasticore extends CardImpl<MoltenTailMasticore> {

    public MoltenTailMasticore (UUID ownerId) {
        super(ownerId, 177, "Molten-Tail Masticore", Rarity.MYTHIC, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}");
        this.expansionSetCode = "SOM";
        this.subtype.add("Masticore");
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        this.addAbility(new MoltenTailMasticoreAbility());
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(4), new GenericManaCost(4));
        ability.addCost(new ExileFromGraveCost(new TargetCardInYourGraveyard(new FilterCreatureCard("creature card from your graveyard"))));
        ability.addTarget(new TargetCreatureOrPlayer());
        this.addAbility(ability);
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect(), new GenericManaCost(2)));
    }

    public MoltenTailMasticore (final MoltenTailMasticore card) {
        super(card);
    }

    @Override
    public MoltenTailMasticore copy() {
        return new MoltenTailMasticore(this);
    }

}

class MoltenTailMasticoreAbility extends TriggeredAbilityImpl<MoltenTailMasticoreAbility> {
    public MoltenTailMasticoreAbility() {
        super(Zone.BATTLEFIELD, new SacrificeSourceUnlessPaysEffect(new DiscardTargetCost(new TargetCardInHand())));
    }

    public MoltenTailMasticoreAbility(final MoltenTailMasticoreAbility ability) {
        super(ability);
    }

    @Override
    public MoltenTailMasticoreAbility copy() {
        return new MoltenTailMasticoreAbility(this);
    }

    @Override
	public boolean checkTrigger(GameEvent event, Game game) {
		if (event.getType() == GameEvent.EventType.UPKEEP_STEP_PRE && event.getPlayerId().equals(this.controllerId)) {
			return true;
		}
		return false;
	}

	@Override
	public String getRule() {
		return "At the beginning of your upkeep, sacrifice {this} unless you discard a card.";
	}
}