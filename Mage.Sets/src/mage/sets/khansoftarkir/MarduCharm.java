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
package mage.sets.khansoftarkir;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.discard.DiscardCardYouChooseTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class MarduCharm extends CardImpl {

    private static final FilterCard filter = new FilterCard("a noncreature, nonland card");
    
    static {
        filter.add(Predicates.not(new CardTypePredicate(CardType.CREATURE)));
        filter.add(Predicates.not(new CardTypePredicate(CardType.LAND)));
    }
    
    public MarduCharm(UUID ownerId) {
        super(ownerId, 186, "Mardu Charm", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{R}{W}{B}");
        this.expansionSetCode = "KTK";


        // Choose one -
        // <strong>*</strong> Mardu Charm deals 4 damage to target creature.
        this.getSpellAbility().addEffect(new DamageTargetEffect(4));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        
        // <strong>*</strong> Put two 1/1 white Warrior creature tokens onto the battlefield. They gain first strike until end of turn.
        Mode mode = new Mode();
        mode.getEffects().add(new MarduCharmCreateTokenEffect());
        this.getSpellAbility().addMode(mode);
        
        // <strong>*</strong> Target opponent reveals his or her hand. You choose a noncreature, nonland card from it.  That player discards that card.
        mode = new Mode();
        mode.getEffects().add(new DiscardCardYouChooseTargetEffect(filter));
        mode.getTargets().add(new TargetOpponent());
        this.getSpellAbility().addMode(mode);
        
    }

    public MarduCharm(final MarduCharm card) {
        super(card);
    }

    @Override
    public MarduCharm copy() {
        return new MarduCharm(this);
    }
}

class MarduCharmCreateTokenEffect extends OneShotEffect {
    
    public MarduCharmCreateTokenEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Put two 1/1 white Warrior creature tokens onto the battlefield. They gain first strike until end of turn";
    }
    
    public MarduCharmCreateTokenEffect(final MarduCharmCreateTokenEffect effect) {
        super(effect);
    }
    
    @Override
    public MarduCharmCreateTokenEffect copy() {
        return new MarduCharmCreateTokenEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            CreateTokenEffect effect = new CreateTokenEffect(new MarduCharmWarriorToken(), 2);
            effect.apply(game, source);
            for (UUID tokenId :effect.getLastAddedTokenIds()) {
                Permanent token = game.getPermanent(tokenId);
                if (token != null) {
                    ContinuousEffect continuousEffect = new GainAbilityTargetEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn);
                    continuousEffect.setTargetPointer(new FixedTarget(tokenId));
                    game.addEffect(continuousEffect, source);
                }
            }
            return true;
        }
        return false;
    }
}
class MarduCharmWarriorToken extends Token {

    public MarduCharmWarriorToken() {
        super("Warrior", "1/1 white Warrior creature token");
        this.setOriginalExpansionSetCode("KTK");
        this.setTokenType(2);
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add("Warrior");
        power = new MageInt(1);
        toughness = new MageInt(1);
    }
}
