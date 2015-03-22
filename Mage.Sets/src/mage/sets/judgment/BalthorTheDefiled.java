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
package mage.sets.judgment;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.ExileSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.players.Player;


/**
 *
 * @author LevelX2
 */
public class BalthorTheDefiled extends CardImpl {

    public BalthorTheDefiled(UUID ownerId) {
        super(ownerId, 61, "Balthor the Defiled", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");
        this.expansionSetCode = "JUD";
        this.supertype.add("Legendary");
        this.subtype.add("Zombie");
        this.subtype.add("Dwarf");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Minion creatures get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostAllEffect(1, 1, Duration.WhileOnBattlefield,
                new FilterCreaturePermanent("Minion", "Minion creatures"), false)));

        // {B}{B}{B}, Exile Balthor the Defiled: Each player returns all black and all red creature cards from his or her graveyard to the battlefield.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BalthorTheDefiledEffect(), new ManaCostsImpl("{B}{B}{B}"));
        ability.addCost(new ExileSourceCost());
        this.addAbility(ability);
        
    }

    public BalthorTheDefiled(final BalthorTheDefiled card) {
        super(card);
    }

    @Override
    public BalthorTheDefiled copy() {
        return new BalthorTheDefiled(this);
    }
}

class BalthorTheDefiledEffect extends OneShotEffect {

    private static final FilterCreatureCard filter = new FilterCreatureCard();

    static {
        filter.add(Predicates.or(
                new ColorPredicate(ObjectColor.BLACK),
                new ColorPredicate(ObjectColor.RED)));
    }

    public  BalthorTheDefiledEffect() {
        super(Outcome.Detriment);
        this.staticText = "Each player returns all black and all red creature cards from his or her graveyard to the battlefield";
    }

    public  BalthorTheDefiledEffect(final  BalthorTheDefiledEffect effect) {
        super(effect);
    }

    @Override
    public  BalthorTheDefiledEffect copy() {
        return new  BalthorTheDefiledEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID playerId: controller.getInRange()) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    for (Card card: player.getGraveyard().getCards(filter, source.getSourceId(), source.getControllerId(), game)) {
                        player.putOntoBattlefieldWithInfo(card, game, Zone.GRAVEYARD, source.getSourceId());
                    }
                }
            }
            return true;
        }
        return false;
    }
}
