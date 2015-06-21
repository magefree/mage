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
package mage.sets.magicorigins;

import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.RequirementEffect;
import mage.abilities.effects.common.PreventAllDamageToSourceEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.TurnPhase;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class GideonBattleForged extends CardImpl {

    private final static FilterCreaturePermanent filter = new FilterCreaturePermanent();
    
    static {
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
    }
    
    public GideonBattleForged(UUID ownerId) {
        super(ownerId, 23, "Gideon, Battle-Forged", Rarity.MYTHIC, new CardType[]{CardType.PLANESWALKER}, "");
        this.expansionSetCode = "ORI";
        this.subtype.add("Gideon");
        
        this.color.setWhite(true);
        
        this.nightCard = true;
        this.canTransform = true;
        
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.LOYALTY.createInstance(3)), false));
        
        // +2: Up to one target creature an opponent controls attacks Gideon, Battle-Forged during its controller's next turn if able.        
        LoyaltyAbility loyaltyAbility = new LoyaltyAbility(new GideonBattleForgedAttacksIfAbleTargetEffect(Duration.Custom), 2);
        loyaltyAbility.addTarget(new TargetCreaturePermanent(0,1,filter, false));
        this.addAbility(loyaltyAbility);        

        // +1: Until your next turn, target creature gains indestructible. Untap that creature.
        Effect effect = new GainAbilityTargetEffect(IndestructibleAbility.getInstance(), Duration.UntilYourNextTurn);
        effect.setText("Until your next turn, target creature gains indestructible");
        loyaltyAbility = new LoyaltyAbility(effect, 1);
        loyaltyAbility.addTarget(new TargetCreaturePermanent());
        effect = new UntapTargetEffect();
        effect.setText("Untap that creature");
        loyaltyAbility.addEffect(effect);
        this.addAbility(loyaltyAbility);        

        // 0: Until end of turn, Gideon, Battle-Forged becomes a 4/4 Human Soldier creature with indestructible that's still a planeswalker. Prevent all damage that would be dealt to him this turn.
        LoyaltyAbility ability3 = new LoyaltyAbility(new BecomesCreatureSourceEffect(new GideonBattleForgedToken(), "planeswalker", Duration.EndOfTurn), 0);
        effect = new PreventAllDamageToSourceEffect(Duration.EndOfTurn);
        effect.setText("Prevent all damage that would be dealt to him this turn");
        ability3.addEffect(effect);
        this.addAbility(ability3);        

    }

    public GideonBattleForged(final GideonBattleForged card) {
        super(card);
    }

    @Override
    public GideonBattleForged copy() {
        return new GideonBattleForged(this);
    }
}

class GideonBattleForgedToken extends Token {

    public GideonBattleForgedToken() {
        super("", "4/4 Human Soldier creature with indestructible");
        cardType.add(CardType.CREATURE);
        subtype.add("Human");
        subtype.add("Soldier");
        power = new MageInt(4);
        toughness = new MageInt(4);
        this.addAbility(IndestructibleAbility.getInstance());
    }

}

class GideonBattleForgedAttacksIfAbleTargetEffect extends RequirementEffect {

    int nextTurnTargetController = 0;
    protected MageObjectReference targetPermanentReference;
            
    public GideonBattleForgedAttacksIfAbleTargetEffect(Duration duration) {
        super(duration);
        staticText = "Up to one target creature an opponent controls attacks {this} during its controller's next turn if able";
    }

    public GideonBattleForgedAttacksIfAbleTargetEffect(final GideonBattleForgedAttacksIfAbleTargetEffect effect) {
        super(effect);
        this.nextTurnTargetController = effect.nextTurnTargetController;
        this.targetPermanentReference = effect.targetPermanentReference;
    }

    @Override
    public GideonBattleForgedAttacksIfAbleTargetEffect copy() {
        return new GideonBattleForgedAttacksIfAbleTargetEffect(this);
    }
    
    @Override
    public boolean isInactive(Ability source, Game game) {
        if (targetPermanentReference == null) {
            return true;
        }
        Permanent targetPermanent = targetPermanentReference.getPermanent(game);
        if (targetPermanent == null) {
            return true;
        }
        if (nextTurnTargetController == 0 && startingTurn != game.getTurnNum() && game.getActivePlayerId().equals(targetPermanent.getControllerId())) {
            nextTurnTargetController = game.getTurnNum();
        }
        if (game.getPhase().getType() == TurnPhase.END && nextTurnTargetController > 0 && game.getTurnNum() > nextTurnTargetController) {
            return true;
        }
        return false;
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        if (getTargetPointer().getFirst(game, source) == null) {
            discard();
        } else {
            targetPermanentReference = new MageObjectReference(getTargetPointer().getFirst(game, source), game);
        }
    }
    
    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (permanent.getId().equals(getTargetPointer().getFirst(game, source))) {
            if (game.getActivePlayerId().equals(permanent.getControllerId())) {
                Permanent planeswalker = game.getPermanent(source.getSourceId());
                if (planeswalker != null) {
                    return true;
                } else {
                    discard();
                }
            }
        }
        return false;
    }

    @Override
    public UUID mustAttackDefender(Ability source, Game game) {
        return source.getSourceId();
    }

    @Override
    public boolean mustAttack(Game game) {
        return true;
    }

    @Override
    public boolean mustBlock(Game game) {
        return false;
    }

}
