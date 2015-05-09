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
package mage.sets.fatereforged;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.OneControlledCreatureCondition;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalRestrictionEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.abilities.effects.common.continuous.BecomesFaceDownCreatureEffect;
import mage.abilities.effects.common.continuous.BecomesFaceDownCreatureEffect.FaceDownType;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author emerald000
 */
public class JeskaiInfiltrator extends CardImpl {

    public JeskaiInfiltrator(UUID ownerId) {
        super(ownerId, 36, "Jeskai Infiltrator", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{U}");
        this.expansionSetCode = "FRF";
        this.subtype.add("Human");
        this.subtype.add("Monk");
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Jeskai Infiltrator can't be blocked as long as you control no other creatures.
        Effect effect = new ConditionalRestrictionEffect(new CantBeBlockedSourceEffect(), new OneControlledCreatureCondition());
        effect.setText("{this} can't be blocked as long as you control no other creatures");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
        
        // Whenever Jeskai Infiltrator deals combat damage to a player, exile it and the top card of your library in a face-down pile, shuffle that pile, then manifest those cards.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new JeskaiInfiltratorEffect(), false));
    }

    public JeskaiInfiltrator(final JeskaiInfiltrator card) {
        super(card);
    }

    @Override
    public JeskaiInfiltrator copy() {
        return new JeskaiInfiltrator(this);
    }
}

class JeskaiInfiltratorEffect extends OneShotEffect {
    
    JeskaiInfiltratorEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "exile it and the top card of your library in a face-down pile, shuffle that pile, then manifest those cards";
    }
    
    JeskaiInfiltratorEffect(final JeskaiInfiltratorEffect effect) {
        super(effect);
    }
    
    @Override
    public JeskaiInfiltratorEffect copy() {
        return new JeskaiInfiltratorEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            List<Card> cardsToManifest = new ArrayList<>(2);
            Permanent sourcePermanent = game.getPermanent(source.getSourceId());
            Card sourceCard = game.getCard(source.getSourceId());
            if (sourcePermanent != null && sourceCard != null) {                
                player.moveCardToExileWithInfo(sourcePermanent, sourcePermanent.getId(), sourcePermanent.getName(), source.getSourceId(), game, Zone.BATTLEFIELD, true);
                cardsToManifest.add(sourceCard);
            }
            if (sourcePermanent!= null && player.getLibrary().size() > 0) {
                Card cardFromLibrary = player.getLibrary().removeFromTop(game);
                player.moveCardToExileWithInfo(cardFromLibrary, sourcePermanent.getId(), sourcePermanent.getName(), source.getSourceId(), game, Zone.LIBRARY, true);
                cardsToManifest.add(cardFromLibrary);
            }
            Collections.shuffle(cardsToManifest);
            game.fireUpdatePlayersEvent(); // removes Jeskai from Battlefield, so he returns as a fresh permanent to the battlefield with new position
            Ability newSource = source.copy();
            newSource.setWorksFaceDown(true);
            for (Card card : cardsToManifest) {
                ManaCosts manaCosts = null;
                if (card.getCardType().contains(CardType.CREATURE)) {
                    manaCosts = card.getSpellAbility().getManaCosts();
                    if (manaCosts == null) {
                        manaCosts = new ManaCostsImpl("{0}");
                    }
                }
                MageObjectReference objectReference= new MageObjectReference(card.getId(), card.getZoneChangeCounter(game) +1, game);
                game.addEffect(new BecomesFaceDownCreatureEffect(manaCosts, objectReference, Duration.Custom, FaceDownType.MANIFESTED), newSource);                                
                if (player.putOntoBattlefieldWithInfo(card, game, Zone.EXILED, source.getSourceId(), false, true)) {
                    game.informPlayers(new StringBuilder(player.getLogName())
                            .append(" puts facedown card from exile onto the battlefield").toString());
                }
            }
            return true;
        }
        return false;
    }
}
