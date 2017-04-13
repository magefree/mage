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
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import java.util.UUID;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;
import mage.watchers.common.CreaturesDiedWatcher;

/**
 *
 * @author jeffwadsworth
 */
public class BontuTheGlorified extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent();

    static {
        filter.add(new AnotherPredicate());
    }

    public BontuTheGlorified(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add("God");
        this.power = new MageInt(4);
        this.toughness = new MageInt(6);

        //Menace
        this.addAbility(new MenaceAbility());

        //Indestructible
        this.addAbility(IndestructibleAbility.getInstance());

        //Bontu the Glorified can't attack or block unless a creature died under your control this turn.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BontuTheGlorifiedRestrictionEffect()), new CreaturesDiedWatcher());

        //{1}{B}, Sacrifice another creature: Scry 1.  Each opponent loses 1 life and you gain 1 life.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ScryEffect(1), new ManaCostsImpl("{1}{B}"));
        ability.addEffect(new LoseLifeOpponentsEffect(1));
        Effect effect = new GainLifeEffect(1);
        effect.setText("and you gain 1 life");
        ability.addEffect(effect);
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(filter)));
        this.addAbility(ability);

    }

    public BontuTheGlorified(final BontuTheGlorified card) {
        super(card);
    }

    @Override
    public BontuTheGlorified copy() {
        return new BontuTheGlorified(this);
    }
}

class BontuTheGlorifiedRestrictionEffect extends RestrictionEffect {

    public BontuTheGlorifiedRestrictionEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "{this} can't attack or block unless a creature died under your control this turn";
    }

    public BontuTheGlorifiedRestrictionEffect(final BontuTheGlorifiedRestrictionEffect effect) {
        super(effect);
    }

    @Override
    public BontuTheGlorifiedRestrictionEffect copy() {
        return new BontuTheGlorifiedRestrictionEffect(this);
    }

    @Override
    public boolean canAttack(Game game) {
        return false;
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game) {
        return false;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (permanent.getId().equals(source.getSourceId())) {
            Player controller = game.getPlayer(source.getControllerId());
            CreaturesDiedWatcher watcher = (CreaturesDiedWatcher) game.getState().getWatchers().get("CreaturesDiedWatcher");
            if (controller != null
                    && watcher != null) {
                return (watcher.getAmountOfCreaturesDiesThisTurn(controller.getId()) == 0);
            }
            return true;
        }  // do not apply to other creatures.
        return false;
    }
}
