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
package mage.sets.innistrad;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author Alvin
 */
public class SkaabRuinator extends CardImpl {

    public SkaabRuinator(UUID ownerId) {
        super(ownerId, 77, "Skaab Ruinator", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{1}{U}{U}");
        this.expansionSetCode = "ISD";
        this.subtype.add("Zombie");
        this.subtype.add("Horror");

        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        // As an additional cost to cast Skaab Ruinator, exile three creature cards from your graveyard.
        this.getSpellAbility().addCost(new ExileFromGraveCost(new TargetCardInYourGraveyard(3, 3, new FilterCreatureCard("creature card from your graveyard"))));

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // You may cast Skaab Ruinator from your graveyard.
        this.addAbility(new SimpleStaticAbility(Zone.GRAVEYARD, new SkaabRuinatorPlayEffect()));
    }

    public SkaabRuinator(final SkaabRuinator card) {
        super(card);
    }

    @Override
    public SkaabRuinator copy() {
        return new SkaabRuinator(this);
    }
}


class SkaabRuinatorPlayEffect extends AsThoughEffectImpl {

    public SkaabRuinatorPlayEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfGame, Outcome.PutCreatureInPlay);
        staticText = "You may cast {this} from your graveyard";
    }

    public SkaabRuinatorPlayEffect(final SkaabRuinatorPlayEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public SkaabRuinatorPlayEffect copy() {
        return new SkaabRuinatorPlayEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (objectId.equals(source.getSourceId()) &&
                affectedControllerId.equals(source.getControllerId())) {
            Card card = game.getCard(source.getSourceId());
            if (card != null && game.getState().getZone(source.getSourceId()) == Zone.GRAVEYARD) {
                return true;
            }
        }
        return false;
    }
}
