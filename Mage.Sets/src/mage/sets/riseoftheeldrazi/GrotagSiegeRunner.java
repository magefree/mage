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
package mage.sets.riseoftheeldrazi;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public class GrotagSiegeRunner extends CardImpl<GrotagSiegeRunner> {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with defender");

    static {
        filter.getAbilities().add(DefenderAbility.getInstance());
    }

    public GrotagSiegeRunner(UUID ownerId) {
        super(ownerId, 149, "Grotag Siege-Runner", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{1}{R}");
        this.expansionSetCode = "ROE";
        this.subtype.add("Goblin");
        this.subtype.add("Rogue");

        this.color.setRed(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // {R}, Sacrifice Grotag Siege-Runner: Destroy target creature with defender. Grotag Siege-Runner deals 2 damage to that creature's controller.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DestroyTargetEffect(), new ManaCostsImpl("{R}"));
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetCreaturePermanent(filter));
        ability.addEffect(new GrotageSiegeRunnerEffect());
        this.addAbility(ability);
    }

    public GrotagSiegeRunner(final GrotagSiegeRunner card) {
        super(card);
    }

    @Override
    public GrotagSiegeRunner copy() {
        return new GrotagSiegeRunner(this);
    }
}

class GrotageSiegeRunnerEffect extends OneShotEffect<GrotageSiegeRunnerEffect> {

    public GrotageSiegeRunnerEffect() {
        super(Outcome.Damage);
        this.staticText = "{this} deals 2 damage to that creature's controller";
    }

    public GrotageSiegeRunnerEffect(final GrotageSiegeRunnerEffect effect) {
        super(effect);
    }

    @Override
    public GrotageSiegeRunnerEffect copy() {
        return new GrotageSiegeRunnerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = (Permanent) game.getLastKnownInformation(source.getFirstTarget(), Zone.BATTLEFIELD);
        if (permanent != null) {
            Player controller = game.getPlayer(permanent.getControllerId());
            controller.damage(2, source.getSourceId(), game, false, true);
            return true;
        }
        return false;
    }
}
