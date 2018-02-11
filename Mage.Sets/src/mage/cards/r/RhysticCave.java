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
package mage.cards.r;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.BasicManaEffect;
import mage.abilities.effects.common.DoUnlessAnyPlayerPaysManaEffect;
import mage.abilities.effects.common.ManaEffect;
import mage.abilities.mana.ActivatedManaAbilityImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChoiceColor;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author jerekwilson
 */
public class RhysticCave extends CardImpl {

    public RhysticCave(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}: Choose a color. Add one mana of that color to your mana pool unless any player pays {1}. Activate this ability only any time you could cast an instant.
        this.addAbility(new RhysticCaveManaAbility());
    }

    public RhysticCave(final RhysticCave card) {
        super(card);
    }

    @Override
    public RhysticCave copy() {
        return new RhysticCave(this);
    }
}

class RhysticCaveManaAbility extends ActivatedManaAbilityImpl {

    public RhysticCaveManaAbility() {
        super(Zone.BATTLEFIELD, new DoUnlessAnyPlayerPaysManaEffect(new RhysticCaveManaEffect(), new GenericManaCost(1), "Pay {1} to prevent mana adding from {this}."), new TapSourceCost());
        this.netMana.add(new Mana(0, 0, 0, 0, 0, 0, 1, 0));
        this.setUndoPossible(false);
    }

    public RhysticCaveManaAbility(Zone zone, Mana mana, Cost cost) {
        super(zone, new BasicManaEffect(mana), cost);

    }

    public RhysticCaveManaAbility(final RhysticCaveManaAbility ability) {
        super(ability);
    }

    @Override
    public boolean canActivate(UUID playerId, Game game) {
        Player player = game.getPlayer(playerId);
        if (player != null && !player.isInPayManaMode()) {
            return super.canActivate(playerId, game);
        }
        return false;
    }

    @Override
    public RhysticCaveManaAbility copy() {
        return new RhysticCaveManaAbility(this);
    }

    @Override
    public String getRule() {
        return super.getRule() + " Activate this ability only any time you could cast an instant.";
    }
}

class RhysticCaveManaEffect extends ManaEffect {

    private final Mana chosenMana;

    public RhysticCaveManaEffect() {
        super();
        chosenMana = new Mana();
        this.staticText = "Choose a color. Add one mana of that color to your mana pool ";
    }

    public RhysticCaveManaEffect(final RhysticCaveManaEffect effect) {
        super(effect);
        this.chosenMana = effect.chosenMana.copy();
    }

    @Override
    public Mana getMana(Game game, Ability source) {
        return null;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        ChoiceColor choice = new ChoiceColor(true);
        if (controller != null && controller.choose(outcome, choice, game)) {
            switch (choice.getColor().toString()) {
                case "R":
                    chosenMana.setRed(1);
                    break;
                case "U":
                    chosenMana.setBlue(1);
                    break;
                case "W":
                    chosenMana.setWhite(1);
                    break;
                case "B":
                    chosenMana.setBlack(1);
                    break;
                case "G":
                    chosenMana.setGreen(1);
                    break;
            }
            checkToFirePossibleEvents(chosenMana, game, source);
            controller.getManaPool().addMana(chosenMana, game, source);
            return true;
        }
        return false;
    }

    @Override
    public Effect copy() {
        return new RhysticCaveManaEffect(this);
    }
}
