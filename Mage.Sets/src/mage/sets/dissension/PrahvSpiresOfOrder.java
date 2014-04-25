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
package mage.sets.dissension;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.target.TargetSource;

/**
 *
 * @author LevelX2
 */
public class PrahvSpiresOfOrder extends CardImpl<PrahvSpiresOfOrder> {

    public PrahvSpiresOfOrder(UUID ownerId) {
        super(ownerId, 177, "Prahv, Spires of Order", Rarity.UNCOMMON, new CardType[]{CardType.LAND}, "");
        this.expansionSetCode = "DIS";

        // {tap}: Add {1} to your mana pool.
        this.addAbility(new ColorlessManaAbility());
        // {4}{W}{U}, {tap}: Prevent all damage a source of your choice would deal this turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PrahvSpiresOfOrderPreventionEffect(), new ManaCostsImpl("{4}{W}{U}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

    }

    public PrahvSpiresOfOrder(final PrahvSpiresOfOrder card) {
        super(card);
    }

    @Override
    public PrahvSpiresOfOrder copy() {
        return new PrahvSpiresOfOrder(this);
    }
}

class PrahvSpiresOfOrderPreventionEffect extends PreventionEffectImpl<PrahvSpiresOfOrderPreventionEffect> {
    private TargetSource target = new TargetSource();

    public PrahvSpiresOfOrderPreventionEffect() {
        super(Duration.EndOfTurn);
        staticText = "Prevent all damage a source of your choice would deal to you this turn";
    }

    public PrahvSpiresOfOrderPreventionEffect(final PrahvSpiresOfOrderPreventionEffect effect) {
        super(effect);
        this.target = effect.target.copy();
    }

    @Override
    public PrahvSpiresOfOrderPreventionEffect copy() {
        return new PrahvSpiresOfOrderPreventionEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        this.target.choose(Outcome.PreventDamage, source.getControllerId(), source.getSourceId(), game);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event instanceof DamageEvent && super.applies(event, source, game)) {
            if (event.getSourceId().equals(target.getFirstTarget())) {
                return true;
            }
        }
        return false;
    }

}