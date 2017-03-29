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
package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.combat.CantAttackBlockTargetEffect;
import mage.abilities.effects.common.continuous.UntapSourceDuringEachOtherPlayersUntapStepEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.target.common.TargetCreatureOrPlayer;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public class Endbringer extends CardImpl {

    public Endbringer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{C}");
        this.subtype.add("Eldrazi");
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Untap Endbringer during each other player's untap step.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new UntapSourceDuringEachOtherPlayersUntapStepEffect()));
        
        // {T}: Endbringer deals 1 damage to target creature or player.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(1), new TapSourceCost());
        ability.addTarget(new TargetCreatureOrPlayer());
        this.addAbility(ability);
        
        // {C}, {T}: Target creature can't attack or block this turn.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CantAttackBlockTargetEffect(Duration.EndOfTurn), new ManaCostsImpl("{C}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
        
        // {C}{C}, {T}: Draw a card.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), new ManaCostsImpl("{C}{C}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    public Endbringer(final Endbringer card) {
        super(card);
    }

    @Override
    public Endbringer copy() {
        return new Endbringer(this);
    }
}
