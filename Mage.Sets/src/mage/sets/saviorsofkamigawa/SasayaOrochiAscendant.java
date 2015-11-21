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
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.TapForManaAllTriggeredManaAbility;
import mage.abilities.costs.common.RevealHandSourceControllerCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.FlipSourceEffect;
import mage.abilities.effects.common.ManaEffect;
import mage.cards.CardImpl;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterLandCard;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class SasayaOrochiAscendant extends CardImpl {

    public SasayaOrochiAscendant(UUID ownerId) {
        super(ownerId, 145, "Sasaya, Orochi Ascendant", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{G}{G}");
        this.expansionSetCode = "SOK";
        this.supertype.add("Legendary");
        this.subtype.add("Snake");
        this.subtype.add("Monk");

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        this.flipCard = true;
        this.flipCardName = "Sasaya's Essence";

        // Reveal your hand: If you have seven or more land cards in your hand, flip Sasaya, Orochi Ascendant.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new SasayaOrochiAscendantFlipEffect(), new RevealHandSourceControllerCost() ));
    }

    public SasayaOrochiAscendant(final SasayaOrochiAscendant card) {
        super(card);
    }

    @Override
    public SasayaOrochiAscendant copy() {
        return new SasayaOrochiAscendant(this);
    }
}

class SasayaOrochiAscendantFlipEffect extends OneShotEffect {
    
    public SasayaOrochiAscendantFlipEffect() {
        super(Outcome.Benefit);
        this.staticText = "If you have seven or more land cards in your hand, flip {this}";
    }
    
    public SasayaOrochiAscendantFlipEffect(final SasayaOrochiAscendantFlipEffect effect) {
        super(effect);
    }
    
    @Override
    public SasayaOrochiAscendantFlipEffect copy() {
        return new SasayaOrochiAscendantFlipEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            if (controller.getHand().count(new FilterLandCard(), game) > 6) {
                new FlipSourceEffect(new SasayasEssence()).apply(game, source);
            }
            return true;
        }
        return false;
    }
}

class SasayasEssence extends Token {

    SasayasEssence  () {
        super("Sasaya's Essence", "");
        supertype.add("Legendary");
        cardType.add(CardType.ENCHANTMENT);

        color.setGreen(true);

        // Whenever a land you control is tapped for mana, for each other land you control with the same name, add one mana to your mana pool of any type that land produced.
        this.addAbility(new TapForManaAllTriggeredManaAbility(
                new SasayasEssenceManaEffectEffect(),
                new FilterControlledLandPermanent(), SetTargetPointer.PERMANENT));
    }
}

class SasayasEssenceManaEffectEffect extends ManaEffect {

    public SasayasEssenceManaEffectEffect() {
        super();
        this.staticText = "for each other land you control with the same name, add one mana to your mana pool of any type that land produced";
    }

    public SasayasEssenceManaEffectEffect(final SasayasEssenceManaEffectEffect effect) {
        super(effect);
    }

    @Override
    public SasayasEssenceManaEffectEffect copy() {
        return new SasayasEssenceManaEffectEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Mana mana = (Mana) this.getValue("mana");
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (controller != null && mana != null && permanent != null) {
            FilterPermanent filter = new FilterLandPermanent();
            filter.add(Predicates.not(new PermanentIdPredicate(permanent.getId())));
            filter.add(new NamePredicate(permanent.getName()));
            int count = game.getBattlefield().countAll(filter, controller.getId(), game);
            if (count > 0) {
                Choice choice = new ChoiceImpl(true);
                choice.setMessage("Pick the type of mana to produce");
                if (mana.getBlack() > 0) {
                    choice.getChoices().add("Black");
                }
                if (mana.getRed() > 0) {
                    choice.getChoices().add("Red");
                }
                if (mana.getBlue() > 0) {
                    choice.getChoices().add("Blue");
                }
                if (mana.getGreen() > 0) {
                    choice.getChoices().add("Green");
                }
                if (mana.getWhite() > 0) {
                    choice.getChoices().add("White");
                }
                if (mana.getColorless() > 0) {
                    choice.getChoices().add("Colorless");
                }

                if (choice.getChoices().size() > 0) {
                    Mana newMana = new Mana();
                    for (int i = 0; i < count; i++) {
                        choice.clearChoice();
                        if (choice.getChoices().size() == 1) {
                            choice.setChoice(choice.getChoices().iterator().next());
                        } else {
                            while(!choice.isChosen()) {
                                controller.choose(outcome, choice, game);
                                if (!controller.canRespond()) {
                                    return false;
                                }
                            }
                        }
                        if (choice.getChoice() == null) {
                            return false;
                        }
                        switch (choice.getChoice()) {
                            case "Black":
                                newMana.increaseBlack();
                                break;
                            case "Blue":
                                newMana.increaseBlue();
                                break;
                            case "Red":
                                newMana.increaseRed();
                                break;
                            case "Green":
                                newMana.increaseGreen();
                                break;
                            case "White":
                                newMana.increaseWhite();
                                break;
                            case "Colorless":
                                newMana.increaseColorless();
                                break;
                        }
                    }
                    controller.getManaPool().addMana(newMana, game, source);
                    checkToFirePossibleEvents(newMana, game, source);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public Mana getMana(Game game, Ability source) {
        return null;
    }

}
