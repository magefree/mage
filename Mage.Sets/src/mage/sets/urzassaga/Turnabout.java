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
package mage.sets.urzassaga;

import java.util.HashSet;
import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author Plopman
 */
public class Turnabout extends CardImpl<Turnabout> {

    
    public Turnabout(UUID ownerId) {
        super(ownerId, 105, "Turnabout", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{2}{U}{U}");
        this.expansionSetCode = "USG";

        this.color.setBlue(true);

        // Choose artifact, creature, or land. Tap all untapped permanents of the chosen type target player controls, or untap all tapped permanents of that type that player controls.
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new TurnaboutEffect());

    }

    public Turnabout(final Turnabout card) {
        super(card);
    }

    @Override
    public Turnabout copy() {
        return new Turnabout(this);
    }
}

class TurnaboutEffect extends OneShotEffect<TurnaboutEffect> {
    
    private static final HashSet<String> choice = new HashSet<String>();
    static{
        choice.add(CardType.ARTIFACT.toString());
        choice.add(CardType.CREATURE.toString());
        choice.add(CardType.LAND.toString());
    }
    
    private static final HashSet<String> choice2 = new HashSet<String>();
    static{
        choice2.add("Untap");
        choice2.add("Tap");
    }
    
    public TurnaboutEffect() {
        super(Constants.Outcome.Benefit);
        staticText = "Choose artifact, creature, or land. Tap all untapped permanents of the chosen type target player controls, or untap all tapped permanents of that type that player controls";
    }

    public TurnaboutEffect(final TurnaboutEffect effect) {
        super(effect);
    }

    @Override
    public TurnaboutEffect copy() {
        return new TurnaboutEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        UUID target = source.getFirstTarget();
        if(player != null && target != null){
            Choice choiceImpl = new ChoiceImpl();
            choiceImpl.setChoices(choice);
            while(!player.choose(outcome.Neutral, choiceImpl, game));
            CardType type;
            String choosenType = choiceImpl.getChoice();
            
            if(choosenType.equals(CardType.ARTIFACT.toString())){
                type = CardType.ARTIFACT;
            }else if(choosenType.equals(CardType.LAND.toString())){
                type = CardType.LAND;
            }else{
                type = CardType.CREATURE;                
            }
            
            choiceImpl = new ChoiceImpl();
            choiceImpl.setChoices(choice2);
            while(!player.choose(outcome.Neutral, choiceImpl, game));
            
            FilterPermanent filter = new FilterPermanent();
            filter.add(new CardTypePredicate(type));
            
            
            if(choiceImpl.getChoice().equals("Untap")){
                filter.add(new TappedPredicate());
                for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game)) {
                    if(permanent.getControllerId().equals(target)){
                        permanent.untap(game);
                    }
                }
            }
            else{
                filter.add(Predicates.not(new TappedPredicate()));
                for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game)) {
                    if(permanent.getControllerId().equals(target)){
                        permanent.tap(game);
                    }
                }
            }
            
            
        }
        
        
        return true;
    }
}