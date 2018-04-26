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
package mage.cards.m;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.RandomUtil;
import mage.watchers.Watcher;
import mage.watchers.common.AttackedThisTurnWatcher;

/**
 *
 * @author LevelX2
 */
public class MetzaliTowerOfTriumph extends CardImpl {

    public MetzaliTowerOfTriumph(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.addSuperType(SuperType.LEGENDARY);
        this.nightCard = true;

        // <i>(Transforms from Path of Mettle.)</i>
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new InfoEffect("<i>(Transforms from Path of Mettle.)</i>"));
        ability.setRuleAtTheTop(true);
        this.addAbility(ability);

        // {t}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());

        // {1}{R}, {T}: Metzali, Tower of Triumph deals 2 damage to each opponent.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamagePlayersEffect(2, TargetController.OPPONENT), new ManaCostsImpl("{1}{R}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // {2}{W}, {T}: Choose a creature at random that attacked this turn. Destroy that creature.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new MetzaliTowerOfTriumphEffect(), new ManaCostsImpl("{2}{W}"));
        ability.addCost(new TapSourceCost());
        ability.addWatcher(new AttackedThisTurnWatcher());
        this.addAbility(ability);

    }

    public MetzaliTowerOfTriumph(final MetzaliTowerOfTriumph card) {
        super(card);
    }

    @Override
    public MetzaliTowerOfTriumph copy() {
        return new MetzaliTowerOfTriumph(this);
    }

}

class MetzaliTowerOfTriumphEffect extends OneShotEffect {

    public MetzaliTowerOfTriumphEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Choose a creature at random that attacked this turn. Destroy that creature";
    }

    public MetzaliTowerOfTriumphEffect(final MetzaliTowerOfTriumphEffect effect) {
        super(effect);
    }

    @Override
    public MetzaliTowerOfTriumphEffect copy() {
        return new MetzaliTowerOfTriumphEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Watcher watcher = game.getState().getWatchers().get(AttackedThisTurnWatcher.class.getSimpleName());
        if (watcher != null && watcher instanceof AttackedThisTurnWatcher) {
            Set<MageObjectReference> attackedThisTurn = ((AttackedThisTurnWatcher) watcher).getAttackedThisTurnCreatures();
            List<Permanent> available = new ArrayList<>();
            for (MageObjectReference mor : attackedThisTurn) {
                Permanent permanent = mor.getPermanent(game);
                if (permanent != null && permanent.isCreature()) {
                    available.add(permanent);
                }
            }
            if (!available.isEmpty()) {
                Permanent permanent = available.get(RandomUtil.nextInt(available.size()));
                if (permanent != null) {
                    permanent.destroy(source.getSourceId(), game, false);
                }
            }
            return true;
        }
        return false;
    }
}
