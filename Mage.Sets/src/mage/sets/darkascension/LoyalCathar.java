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
package mage.sets.darkascension;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.common.delayed.AtEndOfTurnDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.TransformAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author BetaSteward
 */
public class LoyalCathar extends CardImpl<LoyalCathar> {

    public LoyalCathar(UUID ownerId) {
        super(ownerId, 13, "Loyal Cathar", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{W}{W}");
        this.expansionSetCode = "DKA";
        this.subtype.add("Human");
        this.subtype.add("Soldier");

        this.canTransform = true;
        this.secondSideCard = new UnhallowedCathar(ownerId);

        this.color.setWhite(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(VigilanceAbility.getInstance());
        
        // When Loyal Cathar dies, return it to the battlefield transformed under your control at the beginning of the next end step.
        this.addAbility(new TransformAbility());
        this.addAbility(new DiesTriggeredAbility(new LoyalCatharEffect()));
    }

    public LoyalCathar(final LoyalCathar card) {
        super(card);
    }

    @Override
    public LoyalCathar copy() {
        return new LoyalCathar(this);
    }
}

class LoyalCatharEffect extends OneShotEffect<LoyalCatharEffect> {

	private static final String effectText = "return it to the battlefield transformed under your control at the beginning of the next end step";

	LoyalCatharEffect ( ) {
		super(Constants.Outcome.Benefit);
		staticText = effectText;
	}

	LoyalCatharEffect(LoyalCatharEffect effect) {
		super(effect);
	}

	@Override
	public boolean apply(Game game, Ability source) {
        //create delayed triggered ability
        AtEndOfTurnDelayedTriggeredAbility delayedAbility = new AtEndOfTurnDelayedTriggeredAbility(new ReturnLoyalCatharEffect(source.getSourceId()));
        delayedAbility.setSourceId(source.getSourceId());
        delayedAbility.setControllerId(source.getControllerId());
        game.addDelayedTriggeredAbility(delayedAbility);
        return true;
	}

	@Override
	public LoyalCatharEffect copy() {
		return new LoyalCatharEffect(this);
	}

}

class ReturnLoyalCatharEffect extends OneShotEffect<ReturnLoyalCatharEffect> {

	private UUID cardId;

	public ReturnLoyalCatharEffect(UUID cardId) {
		super(Constants.Outcome.PutCardInPlay);
		this.cardId = cardId;
        this.staticText = "return it to the battlefield transformed under your control";
	}

	public ReturnLoyalCatharEffect(final ReturnLoyalCatharEffect effect) {
		super(effect);
		this.cardId = effect.cardId;
	}

	@Override
	public ReturnLoyalCatharEffect copy() {
		return new ReturnLoyalCatharEffect(this);
	}

	@Override
	public boolean apply(Game game, Ability source) {
        Card card = game.getCard(cardId);
        if (card != null) {
            card.putOntoBattlefield(game, Constants.Zone.GRAVEYARD, source.getSourceId(), source.getControllerId());
            Permanent perm = game.getPermanent(cardId);
            if (perm != null && perm.canTransform()) {
                perm.transform(game);
                return true;
            }
        }
        return false;
	}

}
