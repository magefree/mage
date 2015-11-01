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
package mage.sets.ravnica;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.FearAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LandwalkAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author emerald000
 */
public class ConcertedEffort extends CardImpl {

    public ConcertedEffort(UUID ownerId) {
        super(ownerId, 8, "Concerted Effort", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}{W}");
        this.expansionSetCode = "RAV";

        // At the beginning of each upkeep, creatures you control gain flying until end of turn if a creature you control has flying. The same is true for fear, first strike, double strike, landwalk, protection, trample, and vigilance.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new ConcertedEffortEffect(), TargetController.ANY, false));
    }

    public ConcertedEffort(final ConcertedEffort card) {
        super(card);
    }

    @Override
    public ConcertedEffort copy() {
        return new ConcertedEffort(this);
    }
}

class ConcertedEffortEffect extends OneShotEffect {
    
    private static final FilterCreaturePermanent filterFlying = new FilterCreaturePermanent();
    private static final FilterCreaturePermanent filterFear = new FilterCreaturePermanent();
    private static final FilterCreaturePermanent filterFirstStrike = new FilterCreaturePermanent();
    private static final FilterCreaturePermanent filterDoubleStrike = new FilterCreaturePermanent();
    private static final FilterCreaturePermanent filterLandwalk = new FilterCreaturePermanent();
    private static final FilterCreaturePermanent filterProtection = new FilterCreaturePermanent();
    private static final FilterCreaturePermanent filterTrample = new FilterCreaturePermanent();
    private static final FilterCreaturePermanent filterVigilance = new FilterCreaturePermanent();
    static {
        filterFlying.add(new AbilityPredicate(FlyingAbility.class));
        filterFear.add(new AbilityPredicate(FearAbility.class));
        filterFirstStrike.add(new AbilityPredicate(FirstStrikeAbility.class));
        filterDoubleStrike.add(new AbilityPredicate(DoubleStrikeAbility.class));
        filterLandwalk.add(new AbilityPredicate(LandwalkAbility.class));
        filterProtection.add(new AbilityPredicate(ProtectionAbility.class));
        filterTrample.add(new AbilityPredicate(TrampleAbility.class));
        filterVigilance.add(new AbilityPredicate(VigilanceAbility.class));
    }
    
    ConcertedEffortEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "creatures you control gain flying until end of turn if a creature you control has flying. The same is true for fear, first strike, double strike, landwalk, protection, trample, and vigilance";
    }
    
    ConcertedEffortEffect(final ConcertedEffortEffect effect) {
        super(effect);
    }
    
    @Override
    public ConcertedEffortEffect copy() {
        return new ConcertedEffortEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        // Flying
        if (game.getBattlefield().contains(filterFlying, source.getControllerId(), 1, game)) {
            game.addEffect(new GainAbilityAllEffect(FlyingAbility.getInstance(), Duration.EndOfTurn), source);
        }
        
        // Fear
        if (game.getBattlefield().contains(filterFear, source.getControllerId(), 1, game)) {
            game.addEffect(new GainAbilityAllEffect(FearAbility.getInstance(), Duration.EndOfTurn), source);
        }
        
        // First strike
        if (game.getBattlefield().contains(filterFirstStrike, source.getControllerId(), 1, game)) {
            game.addEffect(new GainAbilityAllEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn), source);
        }
        
        // Double strike 
        if (game.getBattlefield().contains(filterDoubleStrike, source.getControllerId(), 1, game)) {
            game.addEffect(new GainAbilityAllEffect(DoubleStrikeAbility.getInstance(), Duration.EndOfTurn), source);
        }
              
        // Landwalk
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(filterLandwalk, source.getControllerId(), game)) {
            for (Ability ability : permanent.getAbilities(game)) {
                if (ability instanceof LandwalkAbility) {
                    game.addEffect(new GainAbilityAllEffect(ability, Duration.EndOfTurn), source);
                }
            }
        }
        
        // Protection
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(filterProtection, source.getControllerId(), game)) {
            for (Ability ability : permanent.getAbilities(game)) {
                if (ability instanceof ProtectionAbility) {
                    game.addEffect(new GainAbilityAllEffect(ability, Duration.EndOfTurn), source);
                }
            }
        }
        
        // Trample
        if (game.getBattlefield().contains(filterTrample, source.getControllerId(), 1, game)) {
            game.addEffect(new GainAbilityAllEffect(TrampleAbility.getInstance(), Duration.EndOfTurn), source);
        }
        
        // Vigilance
        if (game.getBattlefield().contains(filterVigilance, source.getControllerId(), 1, game)) {
            game.addEffect(new GainAbilityAllEffect(VigilanceAbility.getInstance(), Duration.EndOfTurn), source);
        }
        return true;
    }
}
