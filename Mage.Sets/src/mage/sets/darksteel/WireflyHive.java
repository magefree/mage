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
package mage.sets.darksteel;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.game.permanent.token.Token;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public class WireflyHive extends CardImpl {

    public WireflyHive(UUID ownerId) {
        super(ownerId, 161, "Wirefly Hive", Rarity.UNCOMMON, new CardType[]{CardType.ARTIFACT}, "{3}");
        this.expansionSetCode = "DST";

        // {3}, {tap}: Flip a coin. If you win the flip, put a 2/2 colorless Insect artifact creature token with flying named Wirefly onto the battlefield. 
        // If you lose the flip, destroy all permanents named Wirefly.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new WireflyHiveEffect(), new GenericManaCost(3));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    public WireflyHive(final WireflyHive card) {
        super(card);
    }

    @Override
    public WireflyHive copy() {
        return new WireflyHive(this);
    }
}

class WireflyHiveEffect extends OneShotEffect {

    public WireflyHiveEffect() {
        super(Outcome.Damage);
        staticText = "Flip a coin. If you win the flip, put a 2/2 colorless Insect artifact creature token with flying named Wirefly onto the battlefield."
                + "If you lose the flip, destroy all permanents named Wirefly";
    }

    public WireflyHiveEffect(WireflyHiveEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            if (controller.flipCoin(game)) {
                new CreateTokenEffect(new WireflyToken()).apply(game, source);
                return true;
            } else {
                FilterPermanent filter = new FilterPermanent("permanents named Wirefly");
                filter.add(new NamePredicate("Wirefly"));
                new DestroyAllEffect(filter).apply(game, source);
                return true;
            }
        }
        return false;
    }

    @Override
    public WireflyHiveEffect copy() {
        return new WireflyHiveEffect(this);
    }
}

class WireflyToken extends Token {

    WireflyToken() {
        super("Wirefly", "2/2 colorless Insect artifact creature token named Wirefly");
        this.setOriginalExpansionSetCode("DST");
        this.getPower().modifyBaseValue(2);
        this.getToughness().modifyBaseValue(2);
        this.getSubtype(null).add("Insect");
        this.getCardType().add(CardType.ARTIFACT);
        this.getCardType().add(CardType.CREATURE);
    }
}
