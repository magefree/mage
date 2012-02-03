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

package mage.sets.scarsofmirrodin;

import java.util.UUID;

import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.VariableCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.IntimidateAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.Filter.ComparisonScope;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInOpponentsGraveyard;

/**
 *
 * @author nantuko
 */
public class GethLordOfTheVault extends CardImpl<GethLordOfTheVault> {

	private static final FilterCard filter = new FilterCard("artifact or creature card from an opponent's graveyard");

	static {
		filter.getCardType().add(CardType.CREATURE);
		filter.getCardType().add(CardType.ARTIFACT);
		filter.setScopeCardType(ComparisonScope.Any);
	}
	
    public GethLordOfTheVault (UUID ownerId) {
        super(ownerId, 64, "Geth, Lord of the Vault", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");
        this.expansionSetCode = "SOM";
        this.supertype.add("Legendary");
        this.subtype.add("Zombie");
		this.color.setBlack(true);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);
        
        this.addAbility(IntimidateAbility.getInstance());
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GethLordOfTheVaultEffect(), new ManaCostsImpl("{X}{B}"));
        ability.addTarget(new TargetCardInOpponentsGraveyard(filter));
        this.addAbility(ability);
    }

	@Override
	public void adjustCosts(Ability ability, Game game) {
		Card card = game.getCard(ability.getFirstTarget());
		if (card != null) {
			// insert at the beginning (so it will be {2}{B}, not {B}{2})
			ability.getManaCostsToPay().add(0, new GenericManaCost(card.getManaCost().convertedManaCost()));
		}
		// no {X} anymore as we already have chosen the target with defined manacost
		for (ManaCost cost : ability.getManaCostsToPay()) {
			if (cost instanceof VariableCost) {
				cost.setPaid();
			}
		}
	}
	
    public GethLordOfTheVault (final GethLordOfTheVault card) {
        super(card);
    }

    @Override
    public GethLordOfTheVault copy() {
        return new GethLordOfTheVault(this);
    }

}
class GethLordOfTheVaultEffect extends OneShotEffect<GethLordOfTheVaultEffect> {
    
	public GethLordOfTheVaultEffect() {
        super(Outcome.Benefit);
        staticText = "Put target artifact or creature card with converted mana cost X from an opponent's graveyard onto the battlefield under your control tapped. Then that player puts the top X cards of his or her library into his or her graveyard";
    }

    public GethLordOfTheVaultEffect(final GethLordOfTheVaultEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
    	Card card = game.getCard(source.getFirstTarget());
    	if (card != null) {
    		// if still in graveyard
    		if (game.getState().getZone(card.getId()).equals(Zone.GRAVEYARD)) {
				Player player = game.getPlayer(card.getOwnerId());
				if (card.putOntoBattlefield(game, Zone.GRAVEYARD, source.getId(), source.getControllerId())) {
					player.getGraveyard().remove(card);
					
					Permanent permanent = game.getPermanent(card.getId());
					if (permanent != null) {
						permanent.setTapped(true);
					}

					int xvalue = card.getManaCost().convertedManaCost();
					int cardsCount = Math.min(xvalue, player.getLibrary().size());

					for (int i = 0; i < cardsCount; i++) {
						Card removedCard = player.getLibrary().getFromTop(game);
						if (removedCard != null) {
							removedCard.moveToZone(Zone.GRAVEYARD, source.getId(), game, false);
						} else {
							break;
						}
					}

				}
    		}
    	}
    	return false;
    }

    @Override
    public GethLordOfTheVaultEffect copy() {
        return new GethLordOfTheVaultEffect(this);
    }

}
