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
package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureTypeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public class BoldwyrIntimidator extends CardImpl {

    public BoldwyrIntimidator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{R}{R}");
        this.subtype.add("Giant");
        this.subtype.add("Warrior");
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Cowards can't block Warriors.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoldwyrIntimidatorEffect()));
        
        // {R}: Target creature becomes a Coward until end of turn.
        Effect effect = new BecomesCreatureTypeTargetEffect(Duration.EndOfTurn, new ArrayList<>(Collections.singletonList("Coward")), true);
        effect.setText("Target creature becomes a Coward until end of turn");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl("{R}"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
        
        // {2}{R}: Target creature becomes a Warrior until end of turn.
        effect = new BecomesCreatureTypeTargetEffect(Duration.EndOfTurn, new ArrayList<>(Collections.singletonList("Warrior")), true);
        effect.setText("Target creature becomes a Warrior until end of turn");
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl("{2}{R}"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    public BoldwyrIntimidator(final BoldwyrIntimidator card) {
        super(card);
    }

    @Override
    public BoldwyrIntimidator copy() {
        return new BoldwyrIntimidator(this);
    }
}

class BoldwyrIntimidatorEffect extends RestrictionEffect {

    BoldwyrIntimidatorEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "Cowards can't block Warriors";
    }

    BoldwyrIntimidatorEffect(final BoldwyrIntimidatorEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (sourcePermanent != null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game) {
        if (attacker != null && blocker != null) {
            Permanent sourcePermanent = game.getPermanent(source.getSourceId());
            if (sourcePermanent != null && attacker.getSubtype(game).contains("Warrior")) {
                return !blocker.getSubtype(game).contains("Coward");
            }
        }
        return true;
    }

    @Override
    public BoldwyrIntimidatorEffect copy() {
        return new BoldwyrIntimidatorEffect(this);
    }
}
