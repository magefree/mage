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

package mage.sets.worldwake;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.TargetController;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.filter.Filter.ComparisonScope;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.token.PlantToken;
import mage.game.permanent.token.Token;

/**
 *
 * @author Loki, nantuko, North
 */
public class AvengerofZendikar extends CardImpl<AvengerofZendikar> {
    private static final FilterPermanent filter = new FilterPermanent("Plant creature you control");

    static {
        filter.getCardType().add(CardType.CREATURE);
        filter.setScopeCardType(ComparisonScope.Any);
        filter.getSubtype().add("Plant");
        filter.setScopeSubtype(ComparisonScope.Any);
        filter.setTargetController(TargetController.YOU);
    }

    public AvengerofZendikar (UUID ownerId) {
        super(ownerId, 96, "Avenger of Zendikar", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{5}{G}{G}");
        this.expansionSetCode = "WWK";
        this.subtype.add("Elemental");
		this.color.setGreen(true);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);
        this.addAbility(new EntersBattlefieldAbility(new AvengerofZendikarTokensCreateEffect(), "put a 0/1 green Plant creature token onto the battlefield for each land you control"));
        this.addAbility(new LandfallAbility(new AddCountersAllEffect(CounterType.P1P1.createInstance(), filter), false));
    }

    public AvengerofZendikar (final AvengerofZendikar card) {
        super(card);
    }

    @Override
    public AvengerofZendikar copy() {
        return new AvengerofZendikar(this);
    }
}

class AvengerofZendikarTokensCreateEffect extends OneShotEffect<AvengerofZendikarTokensCreateEffect> {
    private static final FilterPermanent filter = new FilterPermanent();
    private Token token = new PlantToken();

    static {
        filter.getCardType().add(CardType.LAND);
        filter.setScopeCardType(ComparisonScope.Any);
    }

    public AvengerofZendikarTokensCreateEffect() {
        super(Constants.Outcome.PutCreatureInPlay);
    }

    public AvengerofZendikarTokensCreateEffect(final AvengerofZendikarTokensCreateEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (int i = 0; i < game.getBattlefield().countAll(filter, source.getControllerId()); i++) {
			token.putOntoBattlefield(game, source.getId(), source.getControllerId());
		}
		return true;
    }

    @Override
    public AvengerofZendikarTokensCreateEffect copy() {
        return new AvengerofZendikarTokensCreateEffect(this);
    }
}