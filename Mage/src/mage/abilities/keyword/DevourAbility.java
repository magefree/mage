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
package mage.abilities.keyword;

import mage.Constants.Zone;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DevourEffect;
import mage.abilities.effects.common.DevourEffect.DevourFactor;

/**
 * 502.82. Devour
 *
 * 502.82a Devour is a static ability. "Devour N" means "As this object comes into play,
 * you may sacrifice any number of creatures. This permanent comes into play with N +1/+1
 * counters on it for each creature sacrificed this way."
 *
 * 502.82b Some objects have abilities that refer to the number of creatures the permanent
 * devoured. "It devoured" means "sacrificed as a result of its devour ability as it came
 * into play."
 *
 * Devour appears only on creature cards.
 *
 * A creature with devour can devour other creatures no matter how it comes into play.
 *
 * You may choose to not sacrifice any creatures.
 *
 * If you play a creature with devour as a spell, you choose how many and which creatures
 * to devour as part of the resolution of that spell. (It can't be countered at this point.)
 * The same is true of a spell or ability that lets you put a creature with devour into play.
 *
 * You may sacrifice only creatures that are already in play. If a creature with devour and
 * another creature are coming into play under your control at the same time, the creature
 * with devour can't devour that other creature. The creature with devour also can't devour
 * itself.
 *
 * If multiple creatures with devour are coming into play under your control at the same time,
 * you may use each one's devour ability. A creature you already control can be devoured by
 * only one of them, however. (In other words, you can't sacrifice the same creature to satisfy
 * multiple devour abilities.) All creatures devoured this way are sacrificed at the same time.
 *
 * @author LevelX2
 */

 public class DevourAbility extends SimpleStaticAbility {



   public DevourAbility(DevourFactor devourFactor) {
        super(Zone.BATTLEFIELD, new DevourEffect(devourFactor));
    }

    public DevourAbility(final DevourAbility ability) {
        super(ability);
    }

    @Override
    public DevourAbility copy() {
        return new DevourAbility(this);
    }
}