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
package mage.sets.magic2012;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continious.BoostSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author nantuko
 */
public class SuturedGhoul extends CardImpl<SuturedGhoul> {

	private static final String staticText = "exile any number of creature cards from your graveyard";
	private static final String staticText2 = "Sutured Ghoul's power is equal to the total power of the exiled cards and its toughness is equal to their total toughness";

	public SuturedGhoul(UUID ownerId) {
		super(ownerId, 112, "Sutured Ghoul", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{4}{B}{B}{B}");
		this.expansionSetCode = "M12";
		this.subtype.add("Zombie");

		this.color.setBlack(true);
		this.power = new MageInt(0);
		this.toughness = new MageInt(0);

		this.addAbility(TrampleAbility.getInstance());

		// As Sutured Ghoul enters the battlefield, exile any number of creature cards from your graveyard.
		this.addAbility(new EntersBattlefieldAbility(new SuturedGhoulEffect(), staticText));

		// Sutured Ghoul's power is equal to the total power of the exiled cards and its toughness is equal to their total toughness.
		BoostSourceEffect effect = new BoostSourceEffect(new SuturedGhoulPowerCount(), new SuturedGhoulToughnessCount(), Constants.Duration.WhileOnBattlefield);
		effect.setRule(staticText2);
		this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, effect));
	}

	public SuturedGhoul(final SuturedGhoul card) {
		super(card);
	}

	@Override
	public SuturedGhoul copy() {
		return new SuturedGhoul(this);
	}
}

class SuturedGhoulEffect extends OneShotEffect<SuturedGhoulEffect> {

    public SuturedGhoulEffect() {
        super(Constants.Outcome.Benefit);
        staticText = "exile any number of creature cards from your graveyard";
    }

    public SuturedGhoulEffect(SuturedGhoulEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (player.getGraveyard().size() > 0) {

            TargetCardInYourGraveyard target = new TargetCardInYourGraveyard(0, Integer.MAX_VALUE, new FilterCreatureCard("creature cards from your graveyard"));
            if (player.chooseTarget(Constants.Outcome.Benefit, target, source, game)) {
                int count = 0;
                for (UUID uuid : target.getTargets()) {
                    Card card = player.getGraveyard().get(uuid, game);
                    if (card != null) {
                        card.moveToExile(getId(), "Sutured Ghoul", source.getSourceId(), game);
                        if (permanent != null) {
                            permanent.imprint(card.getId(), game);
                            count++;
                        }
                    }
                }

                String msg = count == 1 ? "1 card" : count + "cards";
                game.informPlayers("Sutured Ghoul: " + player.getName() + " exiled " + msg);
            }

        } else {
            game.informPlayers("Sutured Ghoul: No cards in graveyard.");
        }
        return true;
    }

    @Override
    public SuturedGhoulEffect copy() {
        return new SuturedGhoulEffect(this);
    }
}

class SuturedGhoulPowerCount implements DynamicValue {

    private static SuturedGhoulPowerCount instance;

    public static SuturedGhoulPowerCount getInstance() {
        if (instance == null) {
            instance = new SuturedGhoulPowerCount();
        }
        return instance;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility) {
        int amount = 0;
        Permanent permanent = game.getPermanent(sourceAbility.getSourceId());
		if (permanent != null) {
			for (UUID uuid: permanent.getImprinted()) {
				Card card = game.getCard(uuid);
				if (card != null) {
					amount += card.getPower().getValue();
				}
			}
		}
        return amount;
    }

    @Override
    public DynamicValue clone() {
        return getInstance();
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return "the total power of the exiled cards";
    }
}

class SuturedGhoulToughnessCount implements DynamicValue {

    private static SuturedGhoulToughnessCount instance;

    public static SuturedGhoulToughnessCount getInstance() {
        if (instance == null) {
            instance = new SuturedGhoulToughnessCount();
        }
        return instance;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility) {
        int amount = 0;
        Permanent permanent = game.getPermanent(sourceAbility.getSourceId());
		if (permanent != null) {
			for (UUID uuid: permanent.getImprinted()) {
				Card card = game.getCard(uuid);
				if (card != null) {
					amount += card.getToughness().getValue();
				}
			}
		}
        return amount;
    }

    @Override
    public DynamicValue clone() {
        return getInstance();
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return "the total toughness of the exiled cards";
    }
}


