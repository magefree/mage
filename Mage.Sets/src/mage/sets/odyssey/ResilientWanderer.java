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
package mage.sets.odyssey;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.choices.ChoiceColor;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author cbt33, BetaSteward (GainProtectionFromColorTargetEffect)
 */
public class ResilientWanderer extends CardImpl<ResilientWanderer> {

    public ResilientWanderer(UUID ownerId) {
        super(ownerId, 43, "Resilient Wanderer", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{2}{W}{W}");
        this.expansionSetCode = "ODY";
        this.subtype.add("Human");
        this.subtype.add("Nomad");

        this.color.setWhite(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());
        // Discard a card: Resilient Wanderer gains protection from the color of your choice until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainProtectionFromColorSourceEffect(), new DiscardCardCost());
        ability.addChoice(new ChoiceColor());
        this.addAbility(ability);
    }

    public ResilientWanderer(final ResilientWanderer card) {
        super(card);
    }

    @Override
    public ResilientWanderer copy() {
        return new ResilientWanderer(this);
    }
}

class GainProtectionFromColorSourceEffect extends ContinuousEffectImpl<GainProtectionFromColorSourceEffect> {

    protected FilterCard protectionFilter;
    
public GainProtectionFromColorSourceEffect() {
        super(Duration.EndOfTurn, Outcome.AddAbility);
    }

    public GainProtectionFromColorSourceEffect(final GainProtectionFromColorSourceEffect effect) {
        super(effect);
    }

    @Override
    public GainProtectionFromColorSourceEffect copy() {
        return new GainProtectionFromColorSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = game.getPermanent(source.getSourceId());
        if (creature != null) {
            ChoiceColor choice = (ChoiceColor) source.getChoices().get(0);
            protectionFilter.add(new ColorPredicate(choice.getColor()));
            protectionFilter.setMessage(choice.getChoice());
            ProtectionAbility ability = new ProtectionAbility(protectionFilter);
            creature.addAbility(ability, source.getSourceId(), game);
            return true;
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        return "{this} gains protection from the color of your choice " + duration.toString();
    }
}

