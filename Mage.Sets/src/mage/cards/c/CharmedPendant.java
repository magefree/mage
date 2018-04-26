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
package mage.cards.c;

import java.util.Set;
import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.PutTopCardOfYourLibraryToGraveyardCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.costs.mana.ColorlessManaCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.costs.mana.MonoHybridManaCost;
import mage.abilities.costs.mana.VariableManaCost;
import mage.abilities.effects.common.BasicManaEffect;
import mage.abilities.effects.common.ManaEffect;
import mage.abilities.mana.ActivatedManaAbilityImpl;
import mage.abilities.mana.ManaOptions;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class CharmedPendant extends CardImpl {

    public CharmedPendant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // {T}, Put the top card of your library into your graveyard: For each colored mana symbol in that card's mana cost, add one mana of that color. Activate this ability only any time you could cast an instant.
        this.addAbility(new CharmedPendantAbility());
    }

    public CharmedPendant(final CharmedPendant card) {
        super(card);
    }

    @Override
    public CharmedPendant copy() {
        return new CharmedPendant(this);
    }
}

class CharmedPendantAbility extends ActivatedManaAbilityImpl {

    public CharmedPendantAbility() {
        super(Zone.BATTLEFIELD, new CharmedPendantManaEffect(), new TapSourceCost());
        this.addCost(new PutTopCardOfYourLibraryToGraveyardCost());
        this.netMana.add(new Mana(0, 0, 0, 0, 0, 0, 0, 0));
        this.setUndoPossible(false); // Otherwise you could retunrn the card from graveyard
    }

    public CharmedPendantAbility(Zone zone, Mana mana, Cost cost) {
        super(zone, new BasicManaEffect(mana), cost);

    }

    public CharmedPendantAbility(final CharmedPendantAbility ability) {
        super(ability);
    }

    @Override
    public boolean canActivate(UUID playerId, Game game) {
        Player player = game.getPlayer(playerId);
        if (player != null && !player.isInPayManaMode()) { // while paying the costs of a spell you cant activate this
            return super.canActivate(playerId, game);
        }
        return false;
    }

    @Override
    public CharmedPendantAbility copy() {
        return new CharmedPendantAbility(this);
    }

    @Override
    public String getRule() {
        return super.getRule() + " Activate this ability only any time you could cast an instant.";
    }
}

class CharmedPendantManaEffect extends ManaEffect {

    public CharmedPendantManaEffect() {
        super();
        staticText = "For each colored mana symbol in that card's mana cost, add one mana of that color";
    }

    public CharmedPendantManaEffect(final CharmedPendantManaEffect effect) {
        super(effect);
    }

    @Override
    public CharmedPendantManaEffect copy() {
        return new CharmedPendantManaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Mana mana = new Mana();
            for (Cost cost : source.getCosts()) {
                if (cost instanceof PutTopCardOfYourLibraryToGraveyardCost) {
                    Set<Card> cards = ((PutTopCardOfYourLibraryToGraveyardCost) cost).getCardsMovedToGraveyard();
                    if (!cards.isEmpty()) {
                        Card card = cards.iterator().next();
                        if (card != null && card.getManaCost() != null) {
                            ManaCosts<ManaCost> newManaCosts = new ManaCostsImpl<>();
                            for (ManaCost manaCost : card.getManaCost()) {
                                if (manaCost instanceof ColorlessManaCost || manaCost instanceof GenericManaCost || manaCost instanceof VariableManaCost) {
                                    continue;
                                }
                                if (manaCost instanceof MonoHybridManaCost) {
                                    newManaCosts.add(new ColoredManaCost(((MonoHybridManaCost) manaCost).getManaColor()));
                                } else {
                                    newManaCosts.add(manaCost.copy());
                                }
                            }
                            ManaOptions manaOptions = newManaCosts.getOptions();
                            if (manaOptions.size() == 1) {
                                mana = newManaCosts.getMana();
                            } else {
                                Choice manaChoice = new ChoiceImpl(true);
                                manaChoice.setMessage("Select which mana you like to produce");
                                for (Mana manaOption : manaOptions) {
                                    manaChoice.getChoices().add(manaOption.toString());
                                }
                                if (controller.choose(outcome, manaChoice, game)) {
                                    for (Mana manaOption : manaOptions) {
                                        if (manaChoice.getChoice().equals(manaOption.toString())) {
                                            mana = manaOption;
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

            }
            checkToFirePossibleEvents(mana, game, source);
            controller.getManaPool().addMana(mana, game, source);
            return true;
        }

        return false;
    }

    @Override
    public Mana getMana(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            if (controller.isTopCardRevealed()) {
                Card card = controller.getLibrary().getFromTop(game);
                if (card != null) {
                    Mana mana = card.getManaCost().getMana().copy();
                    mana.setColorless(0);
                    mana.setGeneric(0);
                    return mana;
                }
            }
        }
        return null; // You don't know if and which amount or color of mana you get
    }

}
