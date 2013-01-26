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
package mage.sets.gatecrash;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.common.UnblockableAllEffect;
import mage.abilities.effects.common.continious.GainAbilityControlledEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * Gatecrash FAQ 21.01.2013
 *
 * Creatures your opponents control don't actually lose hexproof, although you will 
 * ignore hexproof for purposes of choosing targets of spells and abilities you control.
 *
 * Creatures that come under your control after Glaring Spotlight's last ability 
 * resolves won't have hexproof but will be unblockable that turn.
 *
 * @author LevelX2
 */
public class GlaringSpotlight extends CardImpl<GlaringSpotlight> {

    public GlaringSpotlight(UUID ownerId) {
        super(ownerId, 229, "Glaring Spotlight", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{1}");
        this.expansionSetCode = "GTC";

        // Creatures your opponents control with hexproof can be the targets of spells and abilities you control as though they didn't have hexproof.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GlaringSpotlightEffect()));

        // {3}, Sacrifice Glaring Spotlight: Creatures you control gain hexproof until end of turn and are unblockable this turn.
        Ability ability = new SimpleActivatedAbility(
                Zone.BATTLEFIELD, new GainAbilityControlledEffect(HexproofAbility.getInstance(), Constants.Duration.EndOfTurn, new FilterCreaturePermanent(),false),
                new GenericManaCost(3));
        ability.addEffect(new UnblockableAllEffect(new FilterControlledCreaturePermanent(), Constants.Duration.EndOfTurn));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    public GlaringSpotlight(final GlaringSpotlight card) {
        super(card);
    }

    @Override
    public GlaringSpotlight copy() {
        return new GlaringSpotlight(this);
    }
}

class GlaringSpotlightEffect extends AsThoughEffectImpl<GlaringSpotlightEffect> {

    public GlaringSpotlightEffect() {
        super(Constants.AsThoughEffectType.HEXPROOF, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "Creatures your opponents control with hexproof can be the targets of spells and abilities you control as though they didn't have hexproof";
    }

    public GlaringSpotlightEffect(final GlaringSpotlightEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public GlaringSpotlightEffect copy() {
        return new GlaringSpotlightEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, Game game) {
        Permanent creature = game.getPermanent(sourceId);
        if (creature != null) {
            if (game.getOpponents(source.getControllerId()).contains(creature.getControllerId())) {
                return true;
            }
        }
        return false;
    }
}
