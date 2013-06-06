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
package mage.sets.commander;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.TurnPhase;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.AttacksIfAbleTargetEffect;
import mage.abilities.effects.common.continious.GainAbilityTargetEffect;
import mage.abilities.keyword.AttacksThisTurnMarkerAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public class BasandraBattleSeraph extends CardImpl<BasandraBattleSeraph> {

    public BasandraBattleSeraph(UUID ownerId) {
        super(ownerId, 184, "Basandra, Battle Seraph", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{R}{W}");
        this.expansionSetCode = "CMD";
        this.supertype.add("Legendary");
        this.subtype.add("Angel");

        this.color.setRed(true);
        this.color.setWhite(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Players can't cast spells during combat.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BasandraBattleSeraphEffect()));

        // {R}: Target creature attacks this turn if able.
        Ability ability = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new AttacksIfAbleTargetEffect(Constants.Duration.EndOfTurn), new ManaCostsImpl("{R}"));
        ability.addEffect(new GainAbilityTargetEffect(AttacksThisTurnMarkerAbility.getInstance(), Constants.Duration.EndOfTurn, null));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

    }

    public BasandraBattleSeraph(final BasandraBattleSeraph card) {
        super(card);
    }

    @Override
    public BasandraBattleSeraph copy() {
        return new BasandraBattleSeraph(this);
    }
}

class BasandraBattleSeraphEffect extends ReplacementEffectImpl<BasandraBattleSeraphEffect> {

    public BasandraBattleSeraphEffect() {
        super(Constants.Duration.EndOfTurn, Constants.Outcome.Neutral);
        staticText = "Players can't cast spells during combat";
    }

    public BasandraBattleSeraphEffect(final BasandraBattleSeraphEffect effect) {
        super(effect);
    }

    @Override
    public BasandraBattleSeraphEffect copy() {
        return new BasandraBattleSeraphEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.CAST_SPELL
                && game.getPhase().getType() == TurnPhase.COMBAT) {
            return true;
        }
        return false;
    }
}
