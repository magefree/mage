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
package mage.sets.modernmasters2015;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author LevelX2
 */
public class AllSunsDawn extends CardImpl {

    private final static FilterCard filterGreen = new FilterCard("green card from your graveyard");
    private final static FilterCard filterRed = new FilterCard("red card from your graveyard");
    private final static FilterCard filterBlue = new FilterCard("blue card from your graveyard");
    private final static FilterCard filterBlack = new FilterCard("black card from your graveyard");
    private final static FilterCard filterWhite = new FilterCard("white card from your graveyard");
    
    static {
        filterGreen.add(new ColorPredicate(ObjectColor.GREEN));
        filterRed.add(new ColorPredicate(ObjectColor.RED));
        filterBlue.add(new ColorPredicate(ObjectColor.BLUE));
        filterBlack.add(new ColorPredicate(ObjectColor.BLACK));
        filterWhite.add(new ColorPredicate(ObjectColor.WHITE));
    }
            
    public AllSunsDawn(UUID ownerId) {
        super(ownerId, 138, "All Suns' Dawn", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{4}{G}");
        this.expansionSetCode = "MM2";

        // For each color, return up to one target card of that color from your graveyard to your hand. 
        this.getSpellAbility().addEffect(new AllSunsDawnEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(0,1,filterGreen));
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(0,1,filterRed));
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(0,1,filterBlue));
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(0,1,filterBlack));
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(0,1,filterWhite));
        // Exile All Suns' Dawn.
        this.getSpellAbility().addEffect(ExileSpellEffect.getInstance());
    }

    public AllSunsDawn(final AllSunsDawn card) {
        super(card);
    }

    @Override
    public AllSunsDawn copy() {
        return new AllSunsDawn(this);
    }
}

class AllSunsDawnEffect extends OneShotEffect {
    
    public AllSunsDawnEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "For each color, return up to one target card of that color from your graveyard to your hand. Exile {this}";
    }
    
    public AllSunsDawnEffect(final AllSunsDawnEffect effect) {
        super(effect);
    }
    
    @Override
    public AllSunsDawnEffect copy() {
        return new AllSunsDawnEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for(Target target: source.getTargets()) {
                UUID targetId = target.getFirstTarget();
                Card card = game.getCard(targetId);
                if (card != null) {
                   controller.moveCardToHandWithInfo(card, source.getSourceId(), game, Zone.GRAVEYARD, true);
                }
            }
            return true;
        }
        return false;
    }
}
