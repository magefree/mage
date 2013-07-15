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
package mage.sets.ravnika;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author jeffwadsworth
 */
public class DimirDoppelganger extends CardImpl<DimirDoppelganger> {

    public DimirDoppelganger(UUID ownerId) {
        super(ownerId, 202, "Dimir Doppelganger", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{U}{B}");
        this.expansionSetCode = "RAV";
        this.subtype.add("Shapeshifter");

        this.color.setBlue(true);
        this.color.setBlack(true);
        this.power = new MageInt(0);
        this.toughness = new MageInt(2);

        // {1}{U}{B}: Exile target creature card from a graveyard. Dimir Doppelganger becomes a copy of that card and gains this ability.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DimirDoppelgangerEffect(), new ManaCostsImpl("{1}{U}{B}"));
        ability.addTarget(new TargetCardInGraveyard(new FilterCreatureCard("creature card in a graveyard")));
        this.addAbility(ability);

    }

    public DimirDoppelganger(final DimirDoppelganger card) {
        super(card);
    }

    @Override
    public DimirDoppelganger copy() {
        return new DimirDoppelganger(this);
    }
}

class DimirDoppelgangerEffect extends ContinuousEffectImpl<DimirDoppelgangerEffect> {

    public DimirDoppelgangerEffect() {
        super(Duration.WhileOnBattlefield, Layer.CopyEffects_1, SubLayer.NA, Outcome.BecomeCreature);
        staticText = "Exile target creature card from a graveyard. {this} becomes a copy of that card and gains this ability";
    }

    public DimirDoppelgangerEffect(final DimirDoppelgangerEffect effect) {
        super(effect);
    }

    @Override
    public DimirDoppelgangerEffect copy() {
        return new DimirDoppelgangerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(source.getFirstTarget());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (card == null || permanent == null) {
            return false;
        }
        Card cardToCopy = card.copy();
        cardToCopy.assignNewId();
        permanent.setName(cardToCopy.getName());
        permanent.getPower().setValue(cardToCopy.getPower().getValue());
        permanent.getToughness().setValue(cardToCopy.getToughness().getValue());
        permanent.getColor().setColor(cardToCopy.getColor());
        permanent.getManaCost().clear();
        permanent.getManaCost().add(cardToCopy.getManaCost());
        permanent.getCardType().clear();
        for (CardType type : cardToCopy.getCardType()) {
            if (!permanent.getCardType().contains(type)) {
                permanent.getCardType().add(type);
            }
        }
        permanent.getSubtype().clear();
        for (String type : cardToCopy.getSubtype()) {
            if (!permanent.getSubtype().contains(type)) {
                permanent.getSubtype().add(type);
            }
        }
        permanent.getSupertype().clear();
        for (String type : cardToCopy.getSupertype()) {
            if (!permanent.getSupertype().contains(type)) {
                permanent.getSupertype().add(type);
            }
        }
        permanent.removeAllAbilities(source.getSourceId(), game);
        // gains ability of Dimir Doppelganger
        Ability dimirDoppelgangerAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DimirDoppelgangerEffect(), new ManaCostsImpl("{1}{U}{B}"));
        dimirDoppelgangerAbility.addTarget(new TargetCardInGraveyard(new FilterCreatureCard("creature card in a graveyard")));
        permanent.addAbility(dimirDoppelgangerAbility, source.getSourceId(), game);

        for (Ability ability : cardToCopy.getAbilities()) {
            if (!permanent.getAbilities().contains(ability)) {
                permanent.addAbility(ability, source.getSourceId(), game);
            }
        }
        return true;
    }
}
