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
package mage.sets.lorwyn;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class SoulbrightFlamekin extends CardImpl {

    public SoulbrightFlamekin(UUID ownerId) {
        super(ownerId, 190, "Soulbright Flamekin", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{1}{R}");
        this.expansionSetCode = "LRW";
        this.subtype.add("Elemental");
        this.subtype.add("Shaman");
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // {2}: Target creature gains trample until end of turn. If this is the third time this ability has resolved this turn, you may add {R}{R}{R}{R}{R}{R}{R}{R} to your mana pool.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilityTargetEffect(TrampleAbility.getInstance(), Duration.EndOfTurn), new ManaCostsImpl("{2}"));
        ability.addEffect(new SoulbrightFlamekinEffect());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);            
    }

    public SoulbrightFlamekin(final SoulbrightFlamekin card) {
        super(card);
    }

    @Override
    public SoulbrightFlamekin copy() {
        return new SoulbrightFlamekin(this);
    }
}

class SoulbrightFlamekinEffect extends OneShotEffect {

    class ActivationInfo {
        public int zoneChangeCounter;
        public int turn;
        public int activations;
    }

    public SoulbrightFlamekinEffect() {
        super(Outcome.Damage);
        this.staticText = "If this is the third time this ability has resolved this turn, you may add {R}{R}{R}{R}{R}{R}{R}{R} to your mana pool";
    }

    public SoulbrightFlamekinEffect(final SoulbrightFlamekinEffect effect) {
        super(effect);        
    }

    @Override
    public SoulbrightFlamekinEffect copy() {
        return new SoulbrightFlamekinEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (controller != null && sourcePermanent != null) {
            ActivationInfo info;
            Object object = game.getState().getValue(source.getSourceId() + "ActivationInfo");
            if (object instanceof ActivationInfo) {
                info = (ActivationInfo) object;
                if (info.turn != game.getTurnNum() || sourcePermanent.getZoneChangeCounter(game) != info.zoneChangeCounter) {
                    info.turn = game.getTurnNum();
                    info.zoneChangeCounter = sourcePermanent.getZoneChangeCounter(game);
                    info.activations = 0;
                }
            } else {
                info = new ActivationInfo();
                info.turn = game.getTurnNum();
                info.zoneChangeCounter = sourcePermanent.getZoneChangeCounter(game);
                game.getState().setValue(source.getSourceId() + "ActivationInfo", info);
            }
            info.activations++;
            if (info.activations == 3) {
                controller.getManaPool().addMana(new Mana(8,0,0,0,0,0,0), game, source);
            }
            return true;
        }
        return false;
    }
}
