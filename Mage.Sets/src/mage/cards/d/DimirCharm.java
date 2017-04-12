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
package mage.cards.d;

import mage.abilities.Ability;
import mage.constants.ComparisonType;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.FilterSpell;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPlayer;
import mage.target.TargetSpell;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author Plopman
 */
public class DimirCharm extends CardImpl {
    
    private static final FilterCreaturePermanent filterCreature = new FilterCreaturePermanent("creature with power 2 or less");
    private static final FilterSpell filterSorcery = new FilterSpell("sorcery spell");
    
    static {
        filterCreature.add(new PowerPredicate(ComparisonType.FEWER_THAN, 3));
        filterSorcery.add(new CardTypePredicate(CardType.SORCERY));
    }

    public DimirCharm (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{U}{B}");


        //Choose one - Counter target sorcery spell
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell(filterSorcery));

        //or destroy target creature with power 2 or less
        Mode mode1 = new Mode();
        mode1.getEffects().add(new DestroyTargetEffect());
        mode1.getTargets().add(new TargetCreaturePermanent(filterCreature));
        this.getSpellAbility().addMode(mode1);

        //or look at the top three cards of target player's library, then put one back and the rest into that player's graveyard
        Mode mode2 = new Mode();
        mode2.getEffects().add(new DimirCharmEffect());
        mode2.getTargets().add(new TargetPlayer());
        this.getSpellAbility().addMode(mode2);
    }

    public DimirCharm(final DimirCharm card) {
        super(card);
    }

    @Override
    public DimirCharm  copy() {
        return new DimirCharm(this);
    }
}

class DimirCharmEffect extends OneShotEffect {
    public DimirCharmEffect() {
            super(Outcome.Benefit);
    }

    public DimirCharmEffect(final DimirCharmEffect effect) {
        super(effect);
    }
   
    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        Player controller = game.getPlayer(source.getControllerId());
        if(controller != null && player != null){
            Cards cards = new CardsImpl();
            for(int i = 0; i < 3; i++){
                Card card = player.getLibrary().removeFromTop(game);
                if(card != null){
                    cards.add(card);
                }
            }
            if(!cards.isEmpty()){
                TargetCard target = new TargetCard(Zone.LIBRARY, new FilterCard("Card to put back on top of library"));
                if(controller.chooseTarget(Outcome.Benefit, cards, target, source, game)){
                    Card card = cards.get(target.getFirstTarget(), game);
                    if(card != null){
                        card.moveToZone(Zone.LIBRARY, source.getSourceId(), game, true);
                        cards.remove(card);
                    }
                    controller.moveCards(cards, Zone.GRAVEYARD, source, game);
                }
            }
        }
        return false;
    }
  
    @Override
    public DimirCharmEffect copy() {
        return new DimirCharmEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        return "look at the top three cards of target player's library, then put one back and the rest into that player's graveyard";
    }    
}
