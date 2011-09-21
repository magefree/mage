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
package mage.sets.newphyrexia;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.Mana;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ManaEffect;
import mage.abilities.mana.TriggeredManaAbility;
import mage.cards.CardImpl;
import mage.choices.ChoiceColor;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author BetaSteward
 */
public class CagedSun extends CardImpl<CagedSun> {

    public CagedSun(UUID ownerId) {
        super(ownerId, 132, "Caged Sun", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{6}");
        this.expansionSetCode = "NPH";

        // As Caged Sun enters the battlefield, choose a color.
        this.addAbility(new EntersBattlefieldAbility(new CagedSunEffect1()));
        
        // Creatures you control of the chosen color get +1/+1.
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new CagedSunEffect2()));
        
        // Whenever a land's ability adds one or more mana of the chosen color to your mana pool, add one additional mana of that color to your mana pool.
        this.addAbility(new CagedSunTriggeredAbility());
    }

    public CagedSun(final CagedSun card) {
        super(card);
    }

    @Override
    public CagedSun copy() {
        return new CagedSun(this);
    }
}

class CagedSunEffect1 extends OneShotEffect<CagedSunEffect1> {

	public CagedSunEffect1() {
		super(Constants.Outcome.BoostCreature);
		staticText = "As {this} enters the battlefield, choose a color";
	}

	public CagedSunEffect1(final CagedSunEffect1 effect) {
		super(effect);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		Player player = game.getPlayer(source.getControllerId());
		Permanent permanent = game.getPermanent(source.getSourceId());
		if (player != null && permanent != null) {
			ChoiceColor colorChoice = new ChoiceColor();
			if (player.choose(Constants.Outcome.BoostCreature, colorChoice, game)) {
				game.informPlayers(permanent.getName() + ": " + player.getName() + " has chosen " + colorChoice.getChoice());
				game.getState().setValue(permanent.getId() + "_color", colorChoice.getColor());
			}
		}
		return false;
	}

	@Override
	public CagedSunEffect1 copy() {
		return new CagedSunEffect1(this);
	}

}

class CagedSunEffect2 extends ContinuousEffectImpl<CagedSunEffect2> {

	private static final FilterCreaturePermanent filter = FilterCreaturePermanent.getDefault();

	public CagedSunEffect2() {
		super(Duration.WhileOnBattlefield, Constants.Layer.PTChangingEffects_7, Constants.SubLayer.ModifyPT_7c, Constants.Outcome.BoostCreature);
		staticText = "Creatures you control of the chosen color get +1/+1";
	}

	public CagedSunEffect2(final CagedSunEffect2 effect) {
		super(effect);
	}

	@Override
	public CagedSunEffect2 copy() {
		return new CagedSunEffect2(this);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		Permanent permanent = game.getPermanent(source.getSourceId());
		if (permanent != null) {
            ObjectColor color = (ObjectColor) game.getState().getValue(permanent.getId() + "_color");
            if (color != null) {
                for (Permanent perm: game.getBattlefield().getAllActivePermanents(filter, source.getControllerId())) {
                    if (perm.getColor().contains(color)) {
                        perm.addPower(1);
                        perm.addToughness(1);
                    }
                }
            }
        }
		return true;
	}

}

class CagedSunTriggeredAbility extends TriggeredManaAbility<CagedSunTriggeredAbility> {

	private static final String staticText = "Whenever a land's ability adds one or more mana of the chosen color to your mana pool, add one additional mana of that color to your mana pool.";

	public CagedSunTriggeredAbility() {
		super(Zone.BATTLEFIELD, new CagedSunEffect());
	}

	public CagedSunTriggeredAbility(CagedSunTriggeredAbility ability) {
		super(ability);
	}

	@Override
	public boolean checkTrigger(GameEvent event, Game game) {
		if (event.getType() == GameEvent.EventType.MANA_ADDED && event.getPlayerId().equals(controllerId)) {
			Permanent permanent = game.getPermanent(event.getTargetId());
			if (permanent == null) {
				permanent = (Permanent) game.getLastKnownInformation(event.getTargetId(), Zone.BATTLEFIELD);
			}
			if (permanent != null && permanent.getCardType().contains(CardType.LAND)) {
                ObjectColor color = (ObjectColor) game.getState().getValue(this.sourceId + "_color");
                if (color != null && event.getData().contains(color.toString()))
                    return true;
			}
		}
		return false;
	}

	@Override
	public CagedSunTriggeredAbility copy() {
		return new CagedSunTriggeredAbility(this);
	}

	@Override
	public String getRule() {
		return staticText;
	}
}


class CagedSunEffect extends ManaEffect<CagedSunEffect> {

    public CagedSunEffect() {
        super();
        staticText = "add one additional mana of that color to your mana pool";
    }
    
    public CagedSunEffect(final CagedSunEffect effect) {
        super(effect);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            ObjectColor color = (ObjectColor) game.getState().getValue(source.getSourceId() + "_color");
            if (color.isBlack())
                player.getManaPool().addMana(Mana.BlackMana, game, source);
            else if (color.isBlue())
                player.getManaPool().addMana(Mana.BlueMana, game, source);
            else if (color.isRed())
                player.getManaPool().addMana(Mana.RedMana, game, source);
            else if (color.isGreen())
                player.getManaPool().addMana(Mana.GreenMana, game, source);
            else if (color.isWhite())
                player.getManaPool().addMana(Mana.WhiteMana, game, source);
        }
        return true;
    }

    @Override
    public CagedSunEffect copy() {
        return new CagedSunEffect(this);
    }
    
}
