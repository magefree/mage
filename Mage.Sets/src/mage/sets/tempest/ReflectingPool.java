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
package mage.sets.tempest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.Mana;
import mage.abilities.Abilities;
import mage.abilities.Ability;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.ManaEffect;
import mage.abilities.mana.ManaAbility;
import mage.cards.CardImpl;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public class ReflectingPool extends CardImpl {

    public ReflectingPool(UUID ownerId) {
        super(ownerId, 328, "Reflecting Pool", Rarity.RARE, new CardType[]{CardType.LAND}, "");
        this.expansionSetCode = "TMP";

        // {T}: Add to your mana pool one mana of any type that a land you control could produce.
        this.addAbility(new ReflectingPoolManaAbility());
    }

    public ReflectingPool(final ReflectingPool card) {
        super(card);
    }

    @Override
    public ReflectingPool copy() {
        return new ReflectingPool(this);
    }
}

class ReflectingPoolManaAbility extends ManaAbility {

    public ReflectingPoolManaAbility() {
        super(Zone.BATTLEFIELD, new ReflectingPoolEffect(),new TapSourceCost());
    }

    public ReflectingPoolManaAbility(final ReflectingPoolManaAbility ability) {
        super(ability);
    }

    @Override
    public ReflectingPoolManaAbility copy() {
        return new ReflectingPoolManaAbility(this);
    }

    @Override
    public List<Mana> getNetMana(Game game) {
        return ((ReflectingPoolEffect)getEffects().get(0)).getNetMana(game, this);
    }
}

class ReflectingPoolEffect extends ManaEffect {

    private static final FilterControlledPermanent filter = new FilterControlledLandPermanent();

    public ReflectingPoolEffect() {
        super();
        staticText = "Add to your mana pool one mana of any type that a land you control could produce";
    }

    public ReflectingPoolEffect(final ReflectingPoolEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Mana types = getManaTypes(game, source);
        Choice choice = new ChoiceImpl(false);
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
        if (types.getAny() > 0) {
            choice.getChoices().add("Black");
            choice.getChoices().add("Red");
            choice.getChoices().add("Blue");
            choice.getChoices().add("Green");
            choice.getChoices().add("White");
            choice.getChoices().add("Colorless");
        }
        if (choice.getChoices().size() > 0) {
            Player player = game.getPlayer(source.getControllerId());
            if (choice.getChoices().size() == 1) {
                choice.setChoice(choice.getChoices().iterator().next());
            } else {
                player.choose(outcome, choice, game);
            }
            if (choice.getChoice() != null) {
                Mana mana = new Mana();
                switch (choice.getChoice()) {
                    case "Black":
                        mana.setBlack(1);
                        break;
                    case "Blue":
                        mana.setBlue(1);
                        break;
                    case "Red":
                        mana.setRed(1);
                        break;
                    case "Green":
                        mana.setGreen(1);
                        break;
                    case "White":
                        mana.setWhite(1);
                        break;
                    case "Colorless":
                        mana.setColorless(1);
                        break;
                }
                checkToFirePossibleEvents(mana, game, source);
                player.getManaPool().addMana(mana, game, source);
                return true;
            }
            return false;
        }
        return true;
    }

    public  List<Mana> getNetMana(Game game, Ability source) {
        List<Mana> netManas = new ArrayList<>();
        Mana types = getManaTypes(game, source);
        if (types.getAny()> 0) {
            netManas.add(new Mana(0,0,0,0,0,0,1));
            return netManas;
        }
        if (types.getBlack() > 0) {
            netManas.add(new Mana(ColoredManaSymbol.B));
        }
        if (types.getRed() > 0) {
            netManas.add(new Mana(ColoredManaSymbol.R));
        }
        if (types.getBlue() > 0) {
            netManas.add(new Mana(ColoredManaSymbol.U));
        }
        if (types.getGreen() > 0) {
            netManas.add(new Mana(ColoredManaSymbol.G));
        }
        if (types.getWhite() > 0) {
            netManas.add(new Mana(ColoredManaSymbol.W));
        }
        if (types.getColorless() > 0) {
            netManas.add(new Mana(0,0,0,0,0,1,0));
        }
        return netManas;
    }

    private Mana getManaTypes(Game game, Ability source) {
        List<Permanent> lands = game.getBattlefield().getActivePermanents(filter, source.getControllerId(), game);
        Mana types = new Mana();
        for (Permanent land : lands) {
            Abilities<ManaAbility> manaAbilities = land.getAbilities().getManaAbilities(Zone.BATTLEFIELD);
            for (ManaAbility ability : manaAbilities) {
                if (!ability.equals(source) && ability.definesMana()) {
                    for (Mana netMana: ability.getNetMana(game)) {
                        types.add(netMana);
                        if (netMana.getAny() > 0) {
                            return types;
                        }
                    }
                }
            }
        }
        return types;
    }

    @Override
    public Mana getMana(Game game, Ability source) {
        return null;
    }

    @Override
    public ReflectingPoolEffect copy() {
        return new ReflectingPoolEffect(this);
    }
}
