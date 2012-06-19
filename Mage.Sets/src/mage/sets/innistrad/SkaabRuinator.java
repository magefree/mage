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

import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.TimingRule;
import mage.Constants;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author Alvin
 */
public class SkaabRuinator extends CardImpl<SkaabRuinator> {

    public SkaabRuinator(UUID ownerId) {
        super(ownerId, 77, "Skaab Ruinator", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{1}{U}{U}");
        this.expansionSetCode = "ISD";
        this.subtype.add("Zombie");
        this.subtype.add("Horror");

        this.color.setBlue(true);
        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        // As an additional cost to cast Skaab Ruinator, exile three creature cards from your graveyard.
        this.getSpellAbility().addCost(new ExileFromGraveCost(new TargetCardInYourGraveyard(3, 3, new FilterCreatureCard("creature card from your graveyard"))));

        this.addAbility(FlyingAbility.getInstance());
        // You may cast Skaab Ruinator from your graveyard.
        this.addAbility(new SkaabRuinatorAbility(new ManaCostsImpl("{1}{U}{U}"), TimingRule.INSTANT));

    }

    public SkaabRuinator(final SkaabRuinator card) {
        super(card);
    }

    @Override
    public SkaabRuinator copy() {
        return new SkaabRuinator(this);
    }
}

class SkaabRuinatorAbility extends ActivatedAbilityImpl<SkaabRuinatorAbility> {

    public SkaabRuinatorAbility(ManaCosts costs, Constants.TimingRule timingRule) {
        super(Constants.Zone.GRAVEYARD, new SkaabRuinatorEffect(), costs);
        this.timing = TimingRule.SORCERY;
        this.addCost(new ExileFromGraveCost(new TargetCardInYourGraveyard(3, 3, new FilterCreatureCard("creature card from your graveyard"))));
        this.usesStack = false;
    }

    @Override
    public boolean activate(Game game, boolean noMana) {
        Card card = game.getCard(sourceId);
        if (card != null) {
            getEffects().get(0).setTargetPointer(new FixedTarget(card.getId()));
            return super.activate(game, noMana);
        }
        return false;
    }

    public SkaabRuinatorAbility(final SkaabRuinatorAbility ability) {
        super(ability);
    }

    @Override
    public SkaabRuinatorAbility copy() {
        return new SkaabRuinatorAbility(this);
    }

    @Override
    public String getRule() {
        return "You may cast Skaab Ruinator from your graveyard.";
    }
}

class SkaabRuinatorEffect extends OneShotEffect<SkaabRuinatorEffect> {

    public SkaabRuinatorEffect() {
        // should it be?
        super(Constants.Outcome.PutCreatureInPlay);
        staticText = "";
    }

    public SkaabRuinatorEffect(final SkaabRuinatorEffect effect) {
        super(effect);
    }

    @Override
    public SkaabRuinatorEffect copy() {
        return new SkaabRuinatorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card target = (Card) game.getObject(targetPointer.getFirst(game, source));
        if (target != null) {
            Player controller = game.getPlayer(target.getOwnerId());
            if (controller != null) {
                //return controller.cast(target.getSpellAbility(), game, true);
                return target.cast(game, Zone.GRAVEYARD, target.getSpellAbility(), controller.getId());
            }
        }
        return false;
    }
}
