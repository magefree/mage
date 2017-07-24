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
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ExileSourceEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterAttackingCreature;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public class HuntingKavu extends CardImpl {

    public HuntingKavu(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{G}");

        this.subtype.add("Kavu");
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // {1}{R}{G}, {tap}: Exile Hunting Kavu and target creature without flying that's attacking you.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ExileSourceEffect(), new ManaCostsImpl("{1}{R}{G}"));
        ability.addCost(new TapSourceCost());
        Effect effect = new ExileTargetEffect();
        effect.setText("Exile Hunting Kavu and target creature that's attacking you.");
        ability.addEffect(new ExileTargetEffect());
        ability.addTarget(new TargetCreaturePermanent(new HuntingKavuFilter()));
        this.addAbility(ability);
    }

    public HuntingKavu(final HuntingKavu card) {
        super(card);
    }

    @Override
    public HuntingKavu copy() {
        return new HuntingKavu(this);
    }
}

class HuntingKavuFilter extends FilterAttackingCreature {

    public HuntingKavuFilter() {
        super("creature that's attacking you");
    }

    public HuntingKavuFilter(final HuntingKavuFilter filter) {
        super(filter);
    }

    @Override
    public HuntingKavuFilter copy() {
        return new HuntingKavuFilter(this);
    }

    @Override
    public boolean match(Permanent permanent, UUID sourceId, UUID playerId, Game game) {
        if (!super.match(permanent, sourceId, playerId, game)) {
            return false;
        }

        for (CombatGroup group : game.getCombat().getGroups()) {
            for (UUID attacker : group.getAttackers()) {
                if (attacker.equals(permanent.getId())) {
                    UUID defenderId = group.getDefenderId();
                    if (defenderId.equals(playerId)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }
}
