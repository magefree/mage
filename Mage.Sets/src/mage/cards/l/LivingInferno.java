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
package mage.cards.l;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanentAmount;

/**
 *
 * @author LevelX2 & L_J
 */
public class LivingInferno extends CardImpl {

    private final UUID originalId;

    public LivingInferno(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{6}{R}{R}");
        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(8);
        this.toughness = new MageInt(5);

        // {T}: Living Inferno deals damage equal to its power divided as you choose among any number of target creatures. Each of those creatures deals damage equal to its power to Living Inferno.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new LivingInfernoEffect(), new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanentAmount(1));
        this.addAbility(ability);
        originalId = ability.getOriginalId();
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if(ability.getOriginalId().equals(originalId)) {
            Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(ability.getSourceId());
            if (sourcePermanent != null) {
                int xValue = sourcePermanent.getPower().getValue();
                ability.getTargets().clear();
                ability.addTarget(new TargetCreaturePermanentAmount(xValue));
            }
        }
    }

    public LivingInferno(final LivingInferno card) {
        super(card);
        this.originalId = card.originalId;
    }

    @Override
    public LivingInferno copy() {
        return new LivingInferno(this);
    }
}

class LivingInfernoEffect extends OneShotEffect {

    public LivingInfernoEffect() {
        super(Outcome.Benefit);
        this.staticText = "{this} deals damage equal to its power divided as you choose among any number of target creatures. Each of those creatures deals damage equal to its power to {this}";
    }

    public LivingInfernoEffect(final LivingInfernoEffect effect) {
        super(effect);
    }

    @Override
    public LivingInfernoEffect copy() {
        return new LivingInfernoEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (!source.getTargets().isEmpty()) {
            Target multiTarget = source.getTargets().get(0);
            Set<Permanent> permanents = new HashSet<>();
            for (UUID target : multiTarget.getTargets()) {
                Permanent permanent = game.getPermanent(target);
                if (permanent != null) {
                    permanents.add(permanent);
                    permanent.damage(multiTarget.getTargetAmount(target), source.getSourceId(), game, false, true);
                }
            }
            // Each of those creatures deals damage equal to its power to Living Inferno
            Permanent sourceCreature = game.getPermanent(source.getSourceId());
            if (sourceCreature != null) {
                for (Permanent permanent : permanents) {
                    sourceCreature.damage(permanent.getPower().getValue(), permanent.getId(), game, false, true);
                }
            }
            return true;
        }
        return false;
    }
}
