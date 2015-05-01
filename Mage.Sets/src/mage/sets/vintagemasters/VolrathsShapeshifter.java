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
package mage.sets.vintagemasters;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author ImperatorPrime

 */
public class VolrathsShapeshifter extends CardImpl {

    public VolrathsShapeshifter(UUID ownerId) {
        super(ownerId, 101, "Volrath's Shapeshifter", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{U}{U}");
        this.expansionSetCode = "VMA";
        this.subtype.add("Shapeshifter");
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // As long as the top card of your graveyard is a creature card, Volrath's Shapeshifter has the full text of that card and has the text "{2}: Discard a card."
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new VolrathsShapeshifterEffect()));
        
        // {2}: Discard a card.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new DiscardControllerEffect(1), new ManaCostsImpl("{2}")));
    }

    public VolrathsShapeshifter(final VolrathsShapeshifter card) {
        super(card);
    }

    @Override
    public VolrathsShapeshifter copy() {
        return new VolrathsShapeshifter(this);
    }
}

class VolrathsShapeshifterEffect extends ContinuousEffectImpl {
	
	public VolrathsShapeshifterEffect() {
        super(Duration.WhileOnBattlefield, Layer.TextChangingEffects_3, SubLayer.NA, Outcome.BecomeCreature);
	}
	
    public VolrathsShapeshifterEffect(final VolrathsShapeshifterEffect effect) {
        super(effect);
    }
	
	@Override
	public VolrathsShapeshifterEffect copy() {
		return new VolrathsShapeshifterEffect(this);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		Card card = game.getPlayer(source.getControllerId()).getGraveyard().getTopCard(game);
        Permanent permanent = game.getPermanent(source.getSourceId());
        
        if (card == null || permanent == null || !card.getCardType().contains(CardType.CREATURE)) {
            return false;
        }
        
        permanent.getPower().setValue(card.getPower().getValue());
        permanent.getToughness().setValue(card.getToughness().getValue());
        permanent.getColor().setColor(card.getColor());
        permanent.getManaCost().clear();
        permanent.getManaCost().add(card.getManaCost());
        permanent.getCardType().clear();
        permanent.setName(card.getName());
        
        for (CardType type : card.getCardType()) {
            if (!permanent.getCardType().contains(type)) {
                permanent.getCardType().add(type);
            }
        }
        
        permanent.getSubtype().clear();
        for (String type : card.getSubtype()) {
            if (!permanent.getSubtype().contains(type)) {
                permanent.getSubtype().add(type);
            }
        }
        
        permanent.getSupertype().clear();
        for (String type : card.getSupertype()) {
            if (!permanent.getSupertype().contains(type)) {
                permanent.getSupertype().add(type);
            }
        }
        
        for (Ability ability : card.getAbilities()) {
            if (!permanent.getAbilities().contains(ability)) {
                permanent.addAbility(ability, source.getSourceId(), game);
            }
        }
        
        return true;
	}
}
