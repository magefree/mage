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
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.Mana;
import mage.abilities.Abilities;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ManaEffect;
import mage.abilities.effects.common.SkipNextUntapTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.mana.ManaAbility;
import mage.abilities.mana.TriggeredManaAbility;
import mage.cards.CardImpl;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author BetaSteward
 */
public class VorinclexVoiceOfHunger extends CardImpl<VorinclexVoiceOfHunger> {

    public VorinclexVoiceOfHunger(UUID ownerId) {
        super(ownerId, 127, "Vorinclex, Voice of Hunger", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{6}{G}{G}");
        this.expansionSetCode = "NPH";
        this.supertype.add("Legendary");
        this.subtype.add("Praetor");

        this.color.setGreen(true);
        this.power = new MageInt(7);
        this.toughness = new MageInt(6);

        this.addAbility(TrampleAbility.getInstance());
        // Whenever you tap a land for mana, add one mana to your mana pool of any type that land produced.
        this.addAbility(new VorinclexTriggeredAbility1());
        
        // Whenever an opponent taps a land for mana, that land doesn't untap during its controller's next untap step.
        this.addAbility(new VorinclexTriggeredAbility2());
    }

    public VorinclexVoiceOfHunger(final VorinclexVoiceOfHunger card) {
        super(card);
    }

    @Override
    public VorinclexVoiceOfHunger copy() {
        return new VorinclexVoiceOfHunger(this);
    }
}

class VorinclexTriggeredAbility1 extends TriggeredManaAbility<VorinclexTriggeredAbility1> {

	private static final String staticText = "Whenever you tap a land for mana, add one mana to your mana pool of any type that land produced.";

	public VorinclexTriggeredAbility1() {
		super(Zone.BATTLEFIELD, new VorinclexEffect());
	}

	public VorinclexTriggeredAbility1(VorinclexTriggeredAbility1 ability) {
		super(ability);
	}

	@Override
	public boolean checkTrigger(GameEvent event, Game game) {
		if (event.getType() == GameEvent.EventType.TAPPED_FOR_MANA && event.getPlayerId().equals(controllerId)) {
			Permanent permanent = game.getPermanent(event.getSourceId());
			if (permanent == null) {
				permanent = (Permanent) game.getLastKnownInformation(event.getSourceId(), Zone.BATTLEFIELD);
			}
			if (permanent != null && permanent.getCardType().contains(CardType.LAND)) {
                getEffects().get(0).setTargetPointer(new FixedTarget(permanent.getId()));
                return true;
			}
		}
		return false;
	}

	@Override
	public VorinclexTriggeredAbility1 copy() {
		return new VorinclexTriggeredAbility1(this);
	}

	@Override
	public String getRule() {
		return staticText;
	}
}


class VorinclexEffect extends ManaEffect<VorinclexEffect> {

    public VorinclexEffect() {
        super();
        staticText = "add one mana to your mana pool of any type that land produced";
    }
    
    public VorinclexEffect(final VorinclexEffect effect) {
        super(effect);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Permanent land = game.getPermanent(this.targetPointer.getFirst(source));
        Abilities<ManaAbility> mana = land.getAbilities().getManaAbilities(Zone.BATTLEFIELD);
        Mana types = new Mana();
        for (ManaAbility ability: mana) {
            types.add(ability.getNetMana(game));
        }
        Choice choice = new ChoiceImpl(true);
        choice.setMessage("Pick a mana color");
        if (types.getBlack() > 0)
            choice.getChoices().add("Black");
        if (types.getRed() > 0)
            choice.getChoices().add("Red");
        if (types.getBlue() > 0)
            choice.getChoices().add("Blue");
        if (types.getGreen() > 0)
            choice.getChoices().add("Green");
        if (types.getWhite() > 0)
            choice.getChoices().add("White");
        if (types.getColorless() > 0)
            choice.getChoices().add("Colorless");
        if (choice.getChoices().size() > 0) {
            Player player = game.getPlayer(source.getControllerId());
            if (choice.getChoices().size() == 1)
                choice.setChoice(choice.getChoices().iterator().next());
            else
                player.choose(outcome, choice, game);
            if (choice.getChoice().equals("Black")) {
                player.getManaPool().changeMana(Mana.BlackMana);
                return true;
            }
            else if (choice.getChoice().equals("Blue")) {
                player.getManaPool().changeMana(Mana.BlueMana);
                return true;
            }
            else if (choice.getChoice().equals("Red")) {
                player.getManaPool().changeMana(Mana.RedMana);
                return true;
            }
            else if (choice.getChoice().equals("Green")) {
                player.getManaPool().changeMana(Mana.GreenMana);
                return true;
            }
            else if (choice.getChoice().equals("White")) {
                player.getManaPool().changeMana(Mana.WhiteMana);
                return true;
            }
            else if (choice.getChoice().equals("Colorless")) {
                player.getManaPool().changeMana(Mana.ColorlessMana);
                return true;
            }
        }
        return true;
    }

    @Override
    public VorinclexEffect copy() {
        return new VorinclexEffect(this);
    }
    
}

class VorinclexTriggeredAbility2 extends TriggeredAbilityImpl<VorinclexTriggeredAbility2> {

	private static final String staticText = "Whenever an opponent taps a land for mana, that land doesn't untap during its controller's next untap step.";

	public VorinclexTriggeredAbility2() {
		super(Zone.BATTLEFIELD, new SkipNextUntapTargetEffect());
	}

	public VorinclexTriggeredAbility2(VorinclexTriggeredAbility2 ability) {
		super(ability);
	}

	@Override
	public boolean checkTrigger(GameEvent event, Game game) {
		if (event.getType() == GameEvent.EventType.TAPPED_FOR_MANA && game.getOpponents(controllerId).contains(event.getPlayerId())) {
			Permanent permanent = game.getPermanent(event.getSourceId());
			if (permanent != null && permanent.getCardType().contains(CardType.LAND)) {
                getEffects().get(0).setTargetPointer(new FixedTarget(permanent.getId()));
				return true;
			}
		}
		return false;
	}

	@Override
	public VorinclexTriggeredAbility2 copy() {
		return new VorinclexTriggeredAbility2(this);
	}

	@Override
	public String getRule() {
		return staticText;
	}
}
