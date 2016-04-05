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
package mage.sets.shadowsoverinnistrad;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.SkulkAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public class OdricLunarchMarshal extends CardImpl {

    public OdricLunarchMarshal(UUID ownerId) {
        super(ownerId, 31, "Odric, Lunarch Marshal", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{W}");
        this.expansionSetCode = "SOI";
        this.supertype.add("Legendary");
        this.subtype.add("Human");
        this.subtype.add("Soldier");
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // At the beginning of each combat, creatures you control gain first strike until end of turn if a creature you control has first strike. The same is true for flying, deathtouch, double strike, haste, hexproof, indestructible, lifelink, menace, reach, skulk, trample, and vigilance.
        this.addAbility(new BeginningOfCombatTriggeredAbility(new OdricLunarchMarshalEffect(), TargetController.ANY, false));
    }

    public OdricLunarchMarshal(final OdricLunarchMarshal card) {
        super(card);
    }

    @Override
    public OdricLunarchMarshal copy() {
        return new OdricLunarchMarshal(this);
    }
}

class OdricLunarchMarshalEffect extends OneShotEffect {
        
    private static final FilterControlledCreaturePermanent filterFirstStrike = new FilterControlledCreaturePermanent();
    private static final FilterControlledCreaturePermanent filterFlying = new FilterControlledCreaturePermanent();    
    private static final FilterControlledCreaturePermanent filterDeathtouch = new FilterControlledCreaturePermanent();
    private static final FilterControlledCreaturePermanent filterDoubleStrike = new FilterControlledCreaturePermanent();
    private static final FilterControlledCreaturePermanent filterHaste = new FilterControlledCreaturePermanent();
    private static final FilterControlledCreaturePermanent filterHexproof = new FilterControlledCreaturePermanent();    
    private static final FilterControlledCreaturePermanent filterIndestructible = new FilterControlledCreaturePermanent();
    private static final FilterControlledCreaturePermanent filterLifelink = new FilterControlledCreaturePermanent();
    private static final FilterControlledCreaturePermanent filterMenace = new FilterControlledCreaturePermanent();
    private static final FilterControlledCreaturePermanent filterReach = new FilterControlledCreaturePermanent();
    private static final FilterControlledCreaturePermanent filterSkulk = new FilterControlledCreaturePermanent();    
    private static final FilterControlledCreaturePermanent filterTrample = new FilterControlledCreaturePermanent();
    private static final FilterControlledCreaturePermanent filterVigilance = new FilterControlledCreaturePermanent();
    private static final FilterControlledCreaturePermanent filterCreatures = new FilterControlledCreaturePermanent();

    static {        
        filterFirstStrike.add(new AbilityPredicate(FirstStrikeAbility.class));
        filterFlying.add(new AbilityPredicate(FlyingAbility.class));
        filterDeathtouch.add(new AbilityPredicate(DeathtouchAbility.class));
        filterDoubleStrike.add(new AbilityPredicate(DoubleStrikeAbility.class));
        filterHaste.add(new AbilityPredicate(HasteAbility.class));
        filterHexproof.add(new AbilityPredicate(HexproofAbility.class));        
        filterIndestructible.add(new AbilityPredicate(IndestructibleAbility.class));
        filterLifelink.add(new AbilityPredicate(LifelinkAbility.class));
        filterMenace.add(new AbilityPredicate(MenaceAbility.class));
        filterReach.add(new AbilityPredicate(ReachAbility.class));
        filterSkulk.add(new AbilityPredicate(SkulkAbility.class));        
        filterTrample.add(new AbilityPredicate(TrampleAbility.class));
        filterVigilance.add(new AbilityPredicate(VigilanceAbility.class));
    }

    OdricLunarchMarshalEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "creatures you control gain first strike until end of turn if a creature you control has first strike. The same is true for flying, deathtouch, double strike, haste, hexproof, indestructible, lifelink, menace, reach, skulk, trample, and vigilance.";
    }

    OdricLunarchMarshalEffect(final OdricLunarchMarshalEffect effect) {
        super(effect);
    }

    @Override
    public OdricLunarchMarshalEffect copy() {
        return new OdricLunarchMarshalEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        
        // First strike
        if (game.getBattlefield().contains(filterFirstStrike, source.getControllerId(), 1, game)) {
            game.addEffect(new GainAbilityControlledEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn, filterCreatures), source);
        }
        
        // Flying
        if (game.getBattlefield().contains(filterFlying, source.getControllerId(), 1, game)) {
            game.addEffect(new GainAbilityControlledEffect(FlyingAbility.getInstance(), Duration.EndOfTurn, filterCreatures), source);
        }
        
        // Deathtouch
        if (game.getBattlefield().contains(filterDeathtouch, source.getControllerId(), 1, game)) {
            game.addEffect(new GainAbilityControlledEffect(DeathtouchAbility.getInstance(), Duration.EndOfTurn, filterCreatures), source);
        }

        // Double strike
        if (game.getBattlefield().contains(filterDoubleStrike, source.getControllerId(), 1, game)) {
            game.addEffect(new GainAbilityControlledEffect(DoubleStrikeAbility.getInstance(), Duration.EndOfTurn, filterCreatures), source);
        }
        
        // Haste
        if (game.getBattlefield().contains(filterHaste, source.getControllerId(), 1, game)) {
            game.addEffect(new GainAbilityControlledEffect(HasteAbility.getInstance(), Duration.EndOfTurn, filterCreatures), source);
        }
        
        // Hexproof
        if (game.getBattlefield().contains(filterHexproof, source.getControllerId(), 1, game)) {
            game.addEffect(new GainAbilityControlledEffect(HexproofAbility.getInstance(), Duration.EndOfTurn, filterCreatures), source);
        }
        
        // Indestructible
        if (game.getBattlefield().contains(filterIndestructible, source.getControllerId(), 1, game)) {
            game.addEffect(new GainAbilityControlledEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn, filterCreatures), source);
        }
        
        // Lifelink
        if (game.getBattlefield().contains(filterLifelink, source.getControllerId(), 1, game)) {
            game.addEffect(new GainAbilityControlledEffect(LifelinkAbility.getInstance(), Duration.EndOfTurn, filterCreatures), source);
        }
        
        // Menace
        if (game.getBattlefield().contains(filterMenace, source.getControllerId(), 1, game)) {
            game.addEffect(new GainAbilityControlledEffect(new MenaceAbility(), Duration.EndOfTurn, filterCreatures), source);
        }
        
        // Reach
        if (game.getBattlefield().contains(filterReach, source.getControllerId(), 1, game)) {
            game.addEffect(new GainAbilityControlledEffect(ReachAbility.getInstance(), Duration.EndOfTurn, filterCreatures), source);
        }
        
        // Skulk
        if (game.getBattlefield().contains(filterSkulk, source.getControllerId(), 1, game)) {
            game.addEffect(new GainAbilityControlledEffect(new SkulkAbility(), Duration.EndOfTurn, filterCreatures), source);
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