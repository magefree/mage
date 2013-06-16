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
package mage.sets.dragonsmaze;

import java.util.UUID;

import mage.constants.CardType;
import mage.constants.Rarity;
import mage.MageInt;
import mage.Mana;
import mage.abilities.Abilities;
import mage.abilities.Ability;
import mage.abilities.effects.common.ManaEffect;
import mage.abilities.mana.ManaAbility;
import mage.abilities.mana.TriggeredManaAbility;
import mage.cards.CardImpl;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public class ZhurTaaAncient extends CardImpl<ZhurTaaAncient> {

    public ZhurTaaAncient(UUID ownerId) {
        super(ownerId, 119, "Zhur-Taa Ancient", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{R}{G}");
        this.expansionSetCode = "DGM";
        this.subtype.add("Beast");

        this.color.setRed(true);
        this.color.setGreen(true);
        this.power = new MageInt(7);
        this.toughness = new MageInt(5);

        // Whenever a player taps a land for mana, that player adds one mana to his or her mana pool of any type that land produced.
        this.addAbility(new ZhurTaaAncientAbility());
    }

    public ZhurTaaAncient(final ZhurTaaAncient card) {
        super(card);
    }

    @Override
    public ZhurTaaAncient copy() {
        return new ZhurTaaAncient(this);
    }
}

class ZhurTaaAncientAbility extends TriggeredManaAbility<ZhurTaaAncientAbility> {

    private static final String staticText = "Whenever a player taps a land for mana, that player adds one mana to his or her mana pool of any type that land produced.";

    public ZhurTaaAncientAbility() {
        super(Zone.BATTLEFIELD, new ZhurTaaAncientEffect());
    }

    public ZhurTaaAncientAbility(ZhurTaaAncientAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.TAPPED_FOR_MANA) {
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
    public ZhurTaaAncientAbility copy() {
        return new ZhurTaaAncientAbility(this);
    }

    @Override
    public String getRule() {
        return staticText;
    }
}

class ZhurTaaAncientEffect extends ManaEffect<ZhurTaaAncientEffect> {

    public ZhurTaaAncientEffect() {
        super();
        staticText = "that player adds one mana to his or her mana pool of any type that land produced";
    }

    public ZhurTaaAncientEffect(final ZhurTaaAncientEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent land = game.getPermanent(this.targetPointer.getFirst(game, source));
        Abilities<ManaAbility> mana = land.getAbilities().getManaAbilities(Zone.BATTLEFIELD);
        Mana types = new Mana();
        for (ManaAbility ability : mana) {
            types.add(ability.getNetMana(game));
        }
        Choice choice = new ChoiceImpl(true);
        choice.setMessage("Pick a mana color");
        if (types.getBlack() > 0) {
            choice.getChoices().add("Black");
        }
        if (types.getRed() > 0) {
            choice.getChoices().add("Red");
        }
        if (types.getBlue() > 0) {
            choice.getChoices().add("Blue");
        }
        if (types.getGreen() > 0) {
            choice.getChoices().add("Green");
        }
        if (types.getWhite() > 0) {
            choice.getChoices().add("White");
        }
        if (types.getColorless() > 0) {
            choice.getChoices().add("Colorless");
        }
        if (choice.getChoices().size() > 0) {
            Player player = game.getPlayer(land.getControllerId());
            if (choice.getChoices().size() == 1) {
                choice.setChoice(choice.getChoices().iterator().next());
            } else {
                player.choose(outcome, choice, game);
            }
            if (choice.getChoice().equals("Black")) {
                player.getManaPool().addMana(Mana.BlackMana, game, source);
                return true;
            } else if (choice.getChoice().equals("Blue")) {
                player.getManaPool().addMana(Mana.BlueMana, game, source);
                return true;
            } else if (choice.getChoice().equals("Red")) {
                player.getManaPool().addMana(Mana.RedMana, game, source);
                return true;
            } else if (choice.getChoice().equals("Green")) {
                player.getManaPool().addMana(Mana.GreenMana, game, source);
                return true;
            } else if (choice.getChoice().equals("White")) {
                player.getManaPool().addMana(Mana.WhiteMana, game, source);
                return true;
            } else if (choice.getChoice().equals("Colorless")) {
                player.getManaPool().addMana(Mana.ColorlessMana, game, source);
                return true;
            }
        }
        return true;
    }

    @Override
    public ZhurTaaAncientEffect copy() {
        return new ZhurTaaAncientEffect(this);
    }
}