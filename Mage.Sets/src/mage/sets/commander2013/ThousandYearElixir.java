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
package mage.sets.commander2013;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.cards.CardImpl;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class ThousandYearElixir extends CardImpl<ThousandYearElixir> {

    public ThousandYearElixir(UUID ownerId) {
        super(ownerId, 266, "Thousand-Year Elixir", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{3}");
        this.expansionSetCode = "C13";

        // You may activate abilities of creatures you control as though those creatures had haste.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ThousandYearElixirEffect()));

        // {1}, {tap}: Untap target creature.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new UntapTargetEffect(), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent(true));
        this.addAbility(ability);

    }

    public ThousandYearElixir(final ThousandYearElixir card) {
        super(card);
    }

    @Override
    public ThousandYearElixir copy() {
        return new ThousandYearElixir(this);
    }
}

class ThousandYearElixirEffect extends AsThoughEffectImpl<ThousandYearElixirEffect> {

    public ThousandYearElixirEffect() {
        super(AsThoughEffectType.ACTIVATE_HASTE, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "You may activate abilities of creatures you control as though those creatures had haste";
    }

    public ThousandYearElixirEffect(final ThousandYearElixirEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public ThousandYearElixirEffect copy() {
        return new ThousandYearElixirEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, Game game) {
        Permanent permanent = game.getPermanent(sourceId);
        return permanent != null
                && permanent.getCardType().contains(CardType.CREATURE)
                && permanent.getControllerId().equals(source.getControllerId());
    }
}
