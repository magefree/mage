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
package mage.sets.magicorigins;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition.CountType;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.mageobject.SupertypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author emerald000
 */
public class NissaVastwoodSeer extends CardImpl {
    
    private static final FilterCard filter = new FilterCard("basic Forest card");
    static {
        filter.add(new SupertypePredicate("Basic"));
        filter.add(new SubtypePredicate("Forest"));
    }

    public NissaVastwoodSeer(UUID ownerId) {
        super(ownerId, 189, "Nissa, Vastwood Seer", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{2}{G}");
        this.expansionSetCode = "ORI";
        this.supertype.add("Legendary");
        this.subtype.add("Elf");
        this.subtype.add("Scout");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        
        this.canTransform = true;
        this.secondSideCard = new NissaSageAnimist(ownerId);

        // When Nissa, Vastwood Seer enters the battlefield, you may search your library for a basic Forest card, reveal it, put it into your hand, then shuffle your library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SearchLibraryPutInHandEffect(new TargetCardInLibrary(0, 1, filter), true, true), true));

        // Whenever a land enters the battlefield under your control, if you control seven or more lands, exile Nissa, then return her to the battlefield transformed under her owner's control.
        this.addAbility(new TransformAbility());
        this.addAbility(new ConditionalTriggeredAbility(
                new EntersBattlefieldControlledTriggeredAbility(new NissaVastwoodSeerTransformEffect(), new FilterLandPermanent()), 
                new PermanentsOnTheBattlefieldCondition(new FilterLandPermanent(), CountType.MORE_THAN, 6, true), 
                "Whenever a land enters the battlefield under your control, if you control seven or more lands, exile {this}, then return her to the battlefield transformed under her owner's control."));
    }

    public NissaVastwoodSeer(final NissaVastwoodSeer card) {
        super(card);
    }

    @Override
    public NissaVastwoodSeer copy() {
        return new NissaVastwoodSeer(this);
    }
}

class NissaVastwoodSeerTransformEffect extends OneShotEffect {
    
    NissaVastwoodSeerTransformEffect() {
        super(Outcome.Benefit);
        this.staticText = "exile {this}, then return her to the battlefield transformed under her owner's control";
    }
    
    NissaVastwoodSeerTransformEffect(final NissaVastwoodSeerTransformEffect effect) {
        super(effect);
    }
    
    @Override
    public NissaVastwoodSeerTransformEffect copy() {
        return new NissaVastwoodSeerTransformEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        MageObject sourceObject = source.getSourceObjectIfItStillExists(game);
        Player controller = game.getPlayer(source.getControllerId());
        if (sourceObject != null && controller != null) {
            Card card = (Card) sourceObject;
            if (controller.moveCards(card, Zone.BATTLEFIELD, Zone.EXILED, source, game)) {
                game.getState().setValue(TransformAbility.VALUE_KEY_ENTER_TRANSFORMED + source.getSourceId(), Boolean.TRUE);
                controller.putOntoBattlefieldWithInfo(card, game, Zone.EXILED, source.getSourceId());
            }
        }
        return true;
    }
}