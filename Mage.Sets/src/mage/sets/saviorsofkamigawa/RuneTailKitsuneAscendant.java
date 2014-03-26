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
package mage.sets.saviorsofkamigawa;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.StateTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.FlipSourceEffect;
import mage.abilities.effects.common.PreventAllDamageToEffect;
import mage.abilities.effects.common.continious.BoostControlledEffect;
import mage.abilities.effects.common.continious.BoostSourceEffect;
import mage.abilities.effects.common.continious.GainAbilityControlledEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreatureInPlay;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.Token;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class RuneTailKitsuneAscendant extends CardImpl<RuneTailKitsuneAscendant> {

    public RuneTailKitsuneAscendant(UUID ownerId) {
        super(ownerId, 27, "Rune-Tail, Kitsune Ascendant", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{W}");
        this.expansionSetCode = "SOK";
        this.supertype.add("Legendary");
        this.subtype.add("Fox");
        this.subtype.add("Monk");

        this.color.setWhite(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.flipCard = true;
        this.flipCardName = "Rune-Tail's Essence";
        

        // When you have 30 or more life, flip Rune-Tail, Kitsune Ascendant.
        this.addAbility(new RuneTailKitsuneAscendantFlipAbility());
    }

    public RuneTailKitsuneAscendant(final RuneTailKitsuneAscendant card) {
        super(card);
    }

    @Override
    public RuneTailKitsuneAscendant copy() {
        return new RuneTailKitsuneAscendant(this);
    }
}

class RuneTailKitsuneAscendantFlipAbility extends StateTriggeredAbility<RuneTailKitsuneAscendantFlipAbility> {

    public RuneTailKitsuneAscendantFlipAbility() {
        super(Zone.BATTLEFIELD, new FlipSourceEffect(new RuneTailEssence()));
    }

    public RuneTailKitsuneAscendantFlipAbility(final RuneTailKitsuneAscendantFlipAbility ability) {
        super(ability);
    }

    @Override
    public RuneTailKitsuneAscendantFlipAbility copy() {
        return new RuneTailKitsuneAscendantFlipAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Player controller = game.getPlayer(this.getControllerId());
        if (controller != null) {
            return controller.getLife() >= 30;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "When you have 30 or more life, flip {this}";
    }

}

class RuneTailEssence extends Token {


    RuneTailEssence() {
        super("Rune-Tail's Essence", "");
        supertype.add("Legendary");
        cardType.add(CardType.ENCHANTMENT);
        
        color.setWhite(true);

        // Prevent all damage that would be dealt to creatures you control.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PreventAllDamageToEffect(Duration.WhileOnBattlefield, new FilterControlledCreatureInPlay())));
    }
}
