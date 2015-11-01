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
package mage.sets.shadowmoor;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.PutTokenOntoBattlefieldCopyTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public class RhysTheRedeemed extends CardImpl {

    public RhysTheRedeemed(UUID ownerId) {
        super(ownerId, 237, "Rhys the Redeemed", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{G/W}");
        this.expansionSetCode = "SHM";
        this.supertype.add("Legendary");
        this.subtype.add("Elf");
        this.subtype.add("Warrior");

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {2}{GW}, {tap}: Put a 1/1 green and white Elf Warrior creature token onto the battlefield.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new RhysTheRedeemedToken()), new ManaCostsImpl("{2}{G/W}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // {4}{GW}{GW}, {tap}: For each creature token you control, put a token that's a copy of that creature onto the battlefield.
        Ability ability2 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new RhysTheRedeemedEffect(), new ManaCostsImpl("{4}{G/W}{G/W}"));
        ability2.addCost(new TapSourceCost());
        this.addAbility(ability2);

    }

    public RhysTheRedeemed(final RhysTheRedeemed card) {
        super(card);
    }

    @Override
    public RhysTheRedeemed copy() {
        return new RhysTheRedeemed(this);
    }
}

class RhysTheRedeemedEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(new CardTypePredicate(CardType.CREATURE));
        filter.add(new TokenPredicate());
    }

    public RhysTheRedeemedEffect() {
        super(Outcome.Neutral);
        this.staticText = "For each creature token you control, put a token that's a copy of that creature onto the battlefield";
    }

    public RhysTheRedeemedEffect(final RhysTheRedeemedEffect effect) {
        super(effect);
    }

    @Override
    public RhysTheRedeemedEffect copy() {
        return new RhysTheRedeemedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(filter, game)) {
                if (permanent.getControllerId().equals(source.getControllerId())) {
                    PutTokenOntoBattlefieldCopyTargetEffect effect = new PutTokenOntoBattlefieldCopyTargetEffect();
                    effect.setTargetPointer(new FixedTarget(permanent, game));
                    effect.apply(game, source);
                }
            }
            return true;
        }
        return false;
    }
}

class RhysTheRedeemedToken extends Token {

    public RhysTheRedeemedToken() {
        super("Elf Warrior", "1/1 green and white Elf Warrior creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        color.setWhite(true);
        subtype.add("Elf");
        subtype.add("Warrior");
        power = new MageInt(1);
        toughness = new MageInt(1);
    }
}
