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

package mage.sets.guildpact;

import java.util.UUID;

import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnFromExileEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetOpponent;

/**
 *
 * @author Loki
 */
public class GhostCouncilOfOrzhova extends CardImpl<GhostCouncilOfOrzhova> {

    public GhostCouncilOfOrzhova (UUID ownerId) {
        super(ownerId, 114, "Ghost Council of Orzhova", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{W}{W}{B}{B}");
        this.expansionSetCode = "GPT";
        this.supertype.add("Legendary");
        this.subtype.add("Spirit");
		this.color.setWhite(true);
		this.color.setBlack(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        Ability ability = new EntersBattlefieldTriggeredAbility(new GhostCouncilOfOrzhovaEffect());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GhostCouncilOfOrzhovaRemovingEffect(), new GenericManaCost(1));
        ability.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent()));
        this.addAbility(ability);
    }

    public GhostCouncilOfOrzhova (final GhostCouncilOfOrzhova card) {
        super(card);
    }

    @Override
    public GhostCouncilOfOrzhova copy() {
        return new GhostCouncilOfOrzhova(this);
    }

}

class GhostCouncilOfOrzhovaEffect extends OneShotEffect<GhostCouncilOfOrzhovaEffect> {
    GhostCouncilOfOrzhovaEffect() {
        super(Outcome.Damage);
    }

    GhostCouncilOfOrzhovaEffect(final GhostCouncilOfOrzhovaEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        Player controllerPlayer = game.getPlayer(source.getControllerId());
        if (targetPlayer != null && controllerPlayer != null) {
            targetPlayer.damage(1, source.getSourceId(), game, false, true);
            controllerPlayer.gainLife(1, game);
        }
        return false;
    }

    @Override
    public GhostCouncilOfOrzhovaEffect copy() {
        return new GhostCouncilOfOrzhovaEffect(this);
    }

    @Override
    public String getText(Ability source) {
        return "target opponent loses 1 life and you gain 1 life";
    }
}

class GhostCouncilOfOrzhovaRemovingEffect extends OneShotEffect<GhostCouncilOfOrzhovaRemovingEffect> {

	private static final String effectText = "Exile Ghost Council of Orzhova. Return it to the battlefield under its owner's control at the beginning of the next end step";

	GhostCouncilOfOrzhovaRemovingEffect () {
		super(Outcome.Benefit);
	}

	GhostCouncilOfOrzhovaRemovingEffect(GhostCouncilOfOrzhovaRemovingEffect effect) {
		super(effect);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		Permanent permanent = game.getPermanent(source.getSourceId());
		if (permanent != null) {
			if (permanent.moveToExile(source.getSourceId(), " Ghost Council of Orzhova Exile", source.getId(), game)) {
				//create delayed triggered ability
				GhostCouncilOfOrzhovaDelayedTriggeredAbility delayedAbility = new GhostCouncilOfOrzhovaDelayedTriggeredAbility(source.getSourceId());
				delayedAbility.setSourceId(source.getSourceId());
				delayedAbility.setControllerId(source.getControllerId());
				game.addDelayedTriggeredAbility(delayedAbility);
				return true;
			}
		}
		return false;
	}

	@Override
	public GhostCouncilOfOrzhovaRemovingEffect copy() {
		return new GhostCouncilOfOrzhovaRemovingEffect(this);
	}

	@Override
	public String getText(Ability source) {
		return effectText;
	}
}

class GhostCouncilOfOrzhovaDelayedTriggeredAbility extends DelayedTriggeredAbility<GhostCouncilOfOrzhovaDelayedTriggeredAbility> {

	GhostCouncilOfOrzhovaDelayedTriggeredAbility ( UUID exileId ) {
		super(new ReturnFromExileEffect(exileId, Zone.BATTLEFIELD));
	}

	GhostCouncilOfOrzhovaDelayedTriggeredAbility(GhostCouncilOfOrzhovaDelayedTriggeredAbility ability) {
		super(ability);
	}

	@Override
	public boolean checkTrigger(GameEvent event, Game game) {
		if (event.getType() == GameEvent.EventType.END_TURN_STEP_PRE) {
			return true;
		}
		return false;
	}
	@Override
	public GhostCouncilOfOrzhovaDelayedTriggeredAbility copy() {
		return new GhostCouncilOfOrzhovaDelayedTriggeredAbility(this);
	}
}