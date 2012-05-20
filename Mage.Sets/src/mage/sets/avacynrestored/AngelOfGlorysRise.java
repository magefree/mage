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
package mage.sets.avacynrestored;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.filter.Filter;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.players.Player;
import mage.game.permanent.Permanent;

/**
 *
 * @author jeffwadsworth
 */
public class AngelOfGlorysRise extends CardImpl<AngelOfGlorysRise> {

    public AngelOfGlorysRise(UUID ownerId) {
        super(ownerId, 1, "Angel of Glory's Rise", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{5}{W}{W}");
        this.expansionSetCode = "AVR";
        this.subtype.add("Angel");

        this.color.setWhite(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(6);

        this.addAbility(FlyingAbility.getInstance());
        
        // When Angel of Glory's Rise enters the battlefield, exile all Zombies, then return all Human creature cards from your graveyard to the battlefield.
        EntersBattlefieldTriggeredAbility ability = new EntersBattlefieldTriggeredAbility(new AngelOfGlorysRiseEffect());
        this.addAbility(ability);
    }

    public AngelOfGlorysRise(final AngelOfGlorysRise card) {
        super(card);
    }

    @Override
    public AngelOfGlorysRise copy() {
        return new AngelOfGlorysRise(this);
    }
}

class AngelOfGlorysRiseEffect extends OneShotEffect<AngelOfGlorysRiseEffect> {
    
    private static final FilterCreatureCard filterHuman = new FilterCreatureCard();
    private static final FilterCreaturePermanent filterZombie = new FilterCreaturePermanent();
    
    static {
        filterZombie.getSubtype().add("Zombie");
        filterZombie.setScopeSubtype(Filter.ComparisonScope.Any);
        filterHuman.getSubtype().add("Human");
        filterHuman.setScopeSubtype(Filter.ComparisonScope.Any);
    }

    public AngelOfGlorysRiseEffect() {
        super(Constants.Outcome.PutCreatureInPlay);
        staticText = "Exile all Zombies, then return all Human creature cards from your graveyard to the battlefield";
    }

    public AngelOfGlorysRiseEffect(final AngelOfGlorysRiseEffect effect) {
        super(effect);
    }

    @Override
    public AngelOfGlorysRiseEffect copy() {
        return new AngelOfGlorysRiseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            for (Permanent zombie : game.getBattlefield().getAllActivePermanents(filterZombie)) {
                zombie.moveToExile(source.getId(), zombie.getName(), source.getSourceId(), game);
            }
            for (Card human : player.getGraveyard().getCards(filterHuman, game)) {
                human.putOntoBattlefield(game, Constants.Zone.GRAVEYARD, source.getId(), source.getControllerId());
            }
        }
        return true;
    }
}