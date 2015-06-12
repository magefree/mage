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
package mage.sets.fifthdawn;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.costs.VariableCost;
import mage.abilities.costs.common.PayVariableLifeCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.EntwineAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetCard;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author emerald000
 */
public class PlungeIntoDarkness extends CardImpl {

    public PlungeIntoDarkness(UUID ownerId) {
        super(ownerId, 57, "Plunge into Darkness", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{1}{B}");
        this.expansionSetCode = "5DN";


        // Choose one - 
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(1);
        // Sacrifice any number of creatures, then you gain 3 life for each sacrificed creature;
        this.getSpellAbility().addEffect(new PlungeIntoDarknessLifeEffect());
        // or pay X life, then look at the top X cards of your library, put one of those cards into your hand, and exile the rest.
        Mode mode = new Mode();
        mode.getEffects().add(new PlungeIntoDarknessSearchEffect());
        this.getSpellAbility().getModes().addMode(mode);
        
        // Entwine {B}
        this.addAbility(new EntwineAbility("{B}"));
    }

    public PlungeIntoDarkness(final PlungeIntoDarkness card) {
        super(card);
    }

    @Override
    public PlungeIntoDarkness copy() {
        return new PlungeIntoDarkness(this);
    }
}

class PlungeIntoDarknessLifeEffect extends OneShotEffect {
    
    PlungeIntoDarknessLifeEffect() {
        super(Outcome.GainLife);
        this.staticText = "Sacrifice any number of creatures, then you gain 3 life for each sacrificed creature";
    }
    
    PlungeIntoDarknessLifeEffect(final PlungeIntoDarknessLifeEffect effect) {
        super(effect);
    }
    
    @Override
    public PlungeIntoDarknessLifeEffect copy() {
        return new PlungeIntoDarknessLifeEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            Target target = new TargetControlledCreaturePermanent(0, Integer.MAX_VALUE, new FilterControlledCreaturePermanent(), true);
            player.chooseTarget(Outcome.Sacrifice, target, source, game);
            int numSacrificed = 0;
            for (UUID permanentId : target.getTargets()) {
                Permanent permanent = game.getPermanent(permanentId);
                if (permanent != null) {
                    if (permanent.sacrifice(source.getSourceId(), game)) {
                        numSacrificed++;
                    }
                }
            }
            if (numSacrificed > 0) {
                player.gainLife(3 * numSacrificed, game);
            }
            return true;
        }
        return false;
    }
}

class PlungeIntoDarknessSearchEffect extends OneShotEffect {
    
    PlungeIntoDarknessSearchEffect() {
        super(Outcome.Benefit);
        this.staticText = "pay X life, then look at the top X cards of your library, put one of those cards into your hand, and exile the rest.";
    }
    
    PlungeIntoDarknessSearchEffect(final PlungeIntoDarknessSearchEffect effect) {
        super(effect);
    }
    
    @Override
    public PlungeIntoDarknessSearchEffect copy() {
        return new PlungeIntoDarknessSearchEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            VariableCost cost = new PayVariableLifeCost();
            int xValue = cost.announceXValue(source, game);
            cost.getFixedCostsFromAnnouncedValue(xValue).pay(source, game, source.getSourceId(), source.getControllerId(), false);
            
            Cards cards = new CardsImpl(Zone.PICK);
            int count = Math.min(player.getLibrary().size(), xValue);
            for (int i = 0; i < count; i++) {
                Card card = player.getLibrary().removeFromTop(game);
                if (card != null) {
                    cards.add(card);
                    game.setZone(card.getId(), Zone.PICK);
                }
            }
            player.lookAtCards("Plunge into Darkness", cards, game);

            TargetCard target = new TargetCard(Zone.PICK, new FilterCard("card to put into your hand"));
            if (player.choose(Outcome.DrawCard, cards, target, game)) {
                Card card = cards.get(target.getFirstTarget(), game);
                if (card != null) {
                    cards.remove(card);
                    card.moveToZone(Zone.HAND, source.getSourceId(), game, false);
                    game.informPlayers("Plunge into Darkness: " + player.getLogName() + " puts a card into his or her hand");
                }
            }
            for (UUID cardId : cards) {
                Card card = game.getCard(cardId);
                card.moveToExile(null, "", source.getSourceId(), game);
            }
            return true;
        }
        return false;
    }
}
