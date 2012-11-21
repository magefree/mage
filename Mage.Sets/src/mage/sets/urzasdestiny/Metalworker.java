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
package mage.sets.urzasdestiny;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.ManaEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.filter.FilterCard;
import mage.filter.common.FilterArtifactCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author anonymous
 */
public class Metalworker extends CardImpl<Metalworker> {

    public Metalworker(UUID ownerId) {
        super(ownerId, 135, "Metalworker", Rarity.RARE, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");
        this.expansionSetCode = "UDS";
        this.subtype.add("Construct");

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {tap}: Reveal any number of artifact cards in your hand. Add {2} to your mana pool for each card revealed this way.
        this.addAbility(new SimpleManaAbility(Constants.Zone.BATTLEFIELD, new MetalworkerManaEffect(), new TapSourceCost()));
    }

    public Metalworker(final Metalworker card) {
        super(card);
    }

    @Override
    public Metalworker copy() {
        return new Metalworker(this);
    }
}


class MetalworkerManaEffect extends ManaEffect<MetalworkerManaEffect> {

    
    private static final FilterCard filter = new FilterArtifactCard();
    
    public MetalworkerManaEffect() {
        super();
        staticText = "Reveal any number of artifact cards in your hand. Add {2} to your mana pool for each card revealed this way";
    }



    public MetalworkerManaEffect(final MetalworkerManaEffect effect) {
        super(effect);
    }

    @Override
    public MetalworkerManaEffect copy() {
        return new MetalworkerManaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        Cards cards = new CardsImpl();
        if (player.getHand().count(filter, game) > 0) {
            TargetCardInHand target = new TargetCardInHand(0, Integer.MAX_VALUE, filter);
            if (player.choose(Outcome.Benefit, target, source.getSourceId(), game)) {
                for(UUID uuid : target.getTargets()){
                    cards.add(player.getHand().get(uuid, game));
                }
                player.revealCards("Revealed card", cards, game);
                player.getManaPool().addMana(Mana.ColorlessMana(target.getTargets().size()*2), game, source);
            }
        } 

        
        return true;
    }

}
