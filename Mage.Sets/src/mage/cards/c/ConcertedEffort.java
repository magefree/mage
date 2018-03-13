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
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author emerald000
 */
public class ConcertedEffort extends CardImpl {

    public ConcertedEffort(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{W}{W}");

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

    private static final FilterControlledCreaturePermanent filterFlying = new FilterControlledCreaturePermanent();
    private static final FilterControlledCreaturePermanent filterFear = new FilterControlledCreaturePermanent();
    private static final FilterControlledCreaturePermanent filterFirstStrike = new FilterControlledCreaturePermanent();
    private static final FilterControlledCreaturePermanent filterDoubleStrike = new FilterControlledCreaturePermanent();
    private static final FilterControlledCreaturePermanent filterLandwalk = new FilterControlledCreaturePermanent();
    private static final FilterControlledCreaturePermanent filterProtection = new FilterControlledCreaturePermanent();
    private static final FilterControlledCreaturePermanent filterTrample = new FilterControlledCreaturePermanent();
    private static final FilterControlledCreaturePermanent filterVigilance = new FilterControlledCreaturePermanent();
    private static final FilterControlledCreaturePermanent filterCreatures = new FilterControlledCreaturePermanent();

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
            game.addEffect(new GainAbilityControlledEffect(FlyingAbility.getInstance(), Duration.EndOfTurn, filterCreatures), source);
        }

        // Fear
        if (game.getBattlefield().contains(filterFear, source.getControllerId(), 1, game)) {
            game.addEffect(new GainAbilityControlledEffect(FearAbility.getInstance(), Duration.EndOfTurn, filterCreatures), source);
        }

        // First strike
        if (game.getBattlefield().contains(filterFirstStrike, source.getControllerId(), 1, game)) {
            game.addEffect(new GainAbilityControlledEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn, filterCreatures), source);
        }

        // Double strike
        if (game.getBattlefield().contains(filterDoubleStrike, source.getControllerId(), 1, game)) {
            game.addEffect(new GainAbilityControlledEffect(DoubleStrikeAbility.getInstance(), Duration.EndOfTurn, filterCreatures), source);
        }

        // Landwalk
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(filterLandwalk, source.getControllerId(), game)) {
            for (Ability ability : permanent.getAbilities(game)) {
                if (ability instanceof LandwalkAbility) {
                    game.addEffect(new GainAbilityControlledEffect(ability, Duration.EndOfTurn, filterCreatures), source);
                }
            }
        }

        // Protection
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(filterProtection, source.getControllerId(), game)) {
            for (Ability ability : permanent.getAbilities(game)) {
                if (ability instanceof ProtectionAbility) {
                    game.addEffect(new GainAbilityControlledEffect(ability, Duration.EndOfTurn, filterCreatures), source);
                }
            }
        }

        // Trample
        if (game.getBattlefield().contains(filterTrample, source.getControllerId(), 1, game)) {
            game.addEffect(new GainAbilityControlledEffect(TrampleAbility.getInstance(), Duration.EndOfTurn, filterCreatures), source);
        }

        // Vigilance
        if (game.getBattlefield().contains(filterVigilance, source.getControllerId(), 1, game)) {
            game.addEffect(new GainAbilityControlledEffect(VigilanceAbility.getInstance(), Duration.EndOfTurn, filterCreatures), source);
        }
        return true;
    }
}
