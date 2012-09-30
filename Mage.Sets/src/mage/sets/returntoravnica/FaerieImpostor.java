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
package mage.sets.returntoravnica;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */
public class FaerieImpostor extends CardImpl<FaerieImpostor> {

    public FaerieImpostor(UUID ownerId) {
        super(ownerId, 39, "Faerie Impostor", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{U}");
        this.expansionSetCode = "RTR";
        this.subtype.add("Faerie");
        this.subtype.add("Rogue");

        this.color.setBlue(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Faerie Impostor enters the battlefield, sacrifice it unless you return another creature you control to its owner's hand.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new FaerieImpostorEffect()));
    }

    public FaerieImpostor(final FaerieImpostor card) {
        super(card);
    }

    @Override
    public FaerieImpostor copy() {
        return new FaerieImpostor(this);
    }
}

class FaerieImpostorEffect extends OneShotEffect<FaerieImpostorEffect> {

    private static final FilterControlledCreaturePermanent filter;
    private static final String effectText = "sacrifice it unless you return another creature you control to its owner's hand";

    static {
        filter = new FilterControlledCreaturePermanent("another creature you control");
        filter.add(new AnotherPredicate());
    }

    FaerieImpostorEffect ( ) {
        super(Constants.Outcome.ReturnToHand);
        staticText = effectText;
    }

    FaerieImpostorEffect ( FaerieImpostorEffect effect ) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean targetChosen = false;
        Player player = game.getPlayer(source.getControllerId());
        TargetPermanent target = new TargetPermanent(1, 1, filter, false);

        if (target.canChoose(player.getId(), game)) {
            player.choose(Constants.Outcome.ReturnToHand, target, source.getSourceId(), game);
            Permanent permanent = game.getPermanent(target.getFirstTarget());

            if ( permanent != null ) {
                targetChosen = true;
                permanent.moveToZone(Constants.Zone.HAND, this.getId(), game, false);
            }
        }

        if ( !targetChosen ) {
            new SacrificeSourceEffect().apply(game, source);
        }

        return false;
    }

    @Override
    public FaerieImpostorEffect copy() {
        return new FaerieImpostorEffect(this);
    }

}

