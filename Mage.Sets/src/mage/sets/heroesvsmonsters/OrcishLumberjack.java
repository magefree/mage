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
package mage.sets.heroesvsmonsters;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.ManaEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class OrcishLumberjack extends CardImpl<OrcishLumberjack> {

    public OrcishLumberjack(UUID ownerId) {
        super(ownerId, 44, "Orcish Lumberjack", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{R}");
        this.expansionSetCode = "DDL";
        this.subtype.add("Orc");

        this.color.setRed(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}, Sacrifice a Forest: Add three mana in any combination of {R} and/or {G} to your mana pool.
        Ability ability = new SimpleManaAbility(Zone.BATTLEFIELD, new OrcishLumberjackManaEffect(),  new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);

    }

    public OrcishLumberjack(final OrcishLumberjack card) {
        super(card);
    }

    @Override
    public OrcishLumberjack copy() {
        return new OrcishLumberjack(this);
    }
}

class OrcishLumberjackManaEffect extends ManaEffect <OrcishLumberjackManaEffect> {

    public OrcishLumberjackManaEffect() {
        super();
        this.staticText = "Add three mana in any combination of {R} and/or {G} to your mana pool";
    }

    public OrcishLumberjackManaEffect(final OrcishLumberjackManaEffect effect) {
        super(effect);
    }

    @Override
    public OrcishLumberjackManaEffect copy() {
        return new OrcishLumberjackManaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if(player != null){
            Choice manaChoice = new ChoiceImpl();
            Set<String> choices = new LinkedHashSet<String>();
            choices.add("Red");
            choices.add("Green");
            manaChoice.setChoices(choices);
            manaChoice.setMessage("Select color of mana to add");

            for(int i = 0; i < 3; i++){
                Mana mana = new Mana();
                while (!player.choose(Outcome.Benefit, manaChoice, game)  && player.isInGame()) {
                    game.debugMessage("player canceled choosing color. retrying.");
                }

                if (manaChoice.getChoice().equals("Green")) {
                    mana.addGreen();
                } else if (manaChoice.getChoice().equals("Red")) {
                    mana.addRed();
                }
                player.getManaPool().addMana(mana, game, source);
            }
            return true;
        }
        return false;
    }
}
