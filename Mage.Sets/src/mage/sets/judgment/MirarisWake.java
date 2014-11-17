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
package mage.sets.judgment;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Abilities;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.ManaEffect;
import mage.abilities.effects.common.continious.BoostControlledEffect;
import mage.abilities.mana.ManaAbility;
import mage.abilities.mana.TriggeredManaAbility;
import mage.cards.CardImpl;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class MirarisWake extends CardImpl {

    public MirarisWake(UUID ownerId) {
        super(ownerId, 139, "Mirari's Wake", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}{W}");
        this.expansionSetCode = "JUD";

        this.color.setGreen(true);
        this.color.setWhite(true);

        // Creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(1,1,Duration.WhileOnBattlefield)));
        // Whenever you tap a land for mana, add one mana to your mana pool of any type that land produced.
        this.addAbility(new MirarisWakeManaAbility());

    }

    public MirarisWake(final MirarisWake card) {
        super(card);
    }

    @Override
    public MirarisWake copy() {
        return new MirarisWake(this);
    }
}
class MirarisWakeManaAbility extends TriggeredManaAbility {

    private static final String staticText = "Whenever you tap a land for mana, add one mana to your mana pool of any type that land produced.";

    public MirarisWakeManaAbility() {
        super(Zone.BATTLEFIELD, new MirarisWakeManaEffect());
    }

    public MirarisWakeManaAbility(MirarisWakeManaAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.TAPPED_FOR_MANA && event.getPlayerId().equals(controllerId)) {
            Permanent permanent = game.getPermanentOrLKIBattlefield(event.getSourceId());
            if (permanent != null && permanent.getCardType().contains(CardType.LAND)) {
                getEffects().get(0).setTargetPointer(new FixedTarget(permanent.getId()));
                return true;
            }
        }
        return false;
    }

    @Override
    public MirarisWakeManaAbility copy() {
        return new MirarisWakeManaAbility(this);
    }

    @Override
    public String getRule() {
        return staticText;
    }
}


class MirarisWakeManaEffect extends ManaEffect {

    public MirarisWakeManaEffect() {
        super();
        staticText = "add one mana to your mana pool of any type that land produced";
    }

    public MirarisWakeManaEffect(final MirarisWakeManaEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent land = game.getPermanentOrLKIBattlefield(this.targetPointer.getFirst(game, source));
        Abilities<ManaAbility> mana = land.getAbilities().getManaAbilities(Zone.BATTLEFIELD);
        Mana types = new Mana();
        for (ManaAbility ability: mana) {
            for (Mana netMana: ability.getNetMana(game)) {
                types.add(netMana);
            }                
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
            Player player = game.getPlayer(source.getControllerId());
            if (choice.getChoices().size() == 1) {
                choice.setChoice(choice.getChoices().iterator().next());
            } else {
                player.choose(outcome, choice, game);
            }
            switch (choice.getChoice()) {
                case "Black":
                    player.getManaPool().addMana(Mana.BlackMana, game, source);
                    return true;
                case "Blue":
                    player.getManaPool().addMana(Mana.BlueMana, game, source);
                    return true;
                case "Red":
                    player.getManaPool().addMana(Mana.RedMana, game, source);
                    return true;
                case "Green":
                    player.getManaPool().addMana(Mana.GreenMana, game, source);
                    return true;
                case "White":
                    player.getManaPool().addMana(Mana.WhiteMana, game, source);
                    return true;
                case "Colorless":
                    player.getManaPool().addMana(Mana.ColorlessMana, game, source);
                    return true;
            }
        }
        return true;
    }

    @Override
    public MirarisWakeManaEffect copy() {
        return new MirarisWakeManaEffect(this);
    }

}
