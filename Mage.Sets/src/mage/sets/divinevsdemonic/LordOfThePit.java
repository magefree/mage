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
package mage.sets.divinevsdemonic;

import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.constants.*;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author dustinconrad
 */
public class LordOfThePit extends CardImpl {

    public LordOfThePit(UUID ownerId) {
        super(ownerId, 30, "Lord of the Pit", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{4}{B}{B}{B}");
        this.expansionSetCode = "DDC";
        this.subtype.add("Demon");

        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // At the beginning of your upkeep, sacrifice a creature other than Lord of the Pit. If you can't, Lord of the Pit deals 7 damage to you.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new LordOfThePitEffect(), TargetController.YOU, false));
    }

    public LordOfThePit(final LordOfThePit card) {
        super(card);
    }

    @Override
    public LordOfThePit copy() {
        return new LordOfThePit(this);
    }
}

class LordOfThePitEffect extends OneShotEffect {

    public LordOfThePitEffect() {
        super(Outcome.Damage);
        this.staticText = "Sacrifice a creature other than {this}. If you can't {this} deals 7 damage to you.";
    }

    public LordOfThePitEffect(final LordOfThePitEffect effect) {
        super(effect);
    }

    @Override
    public LordOfThePitEffect copy() {
        return new LordOfThePitEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (sourcePermanent == null) {
            sourcePermanent = (Permanent) game.getLastKnownInformation(source.getSourceId(), Zone.BATTLEFIELD);
        }
        if (player == null || sourcePermanent == null) {
            return false;
        }

        FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("creature other than " + sourcePermanent.getName());
        filter.add(new AnotherPredicate());

        Target target = new TargetControlledCreaturePermanent(1, 1, filter, true);
        if (target.canChoose(source.getSourceId(), player.getId(), game)) {
            player.choose(Outcome.Sacrifice, target, source.getSourceId(), game);
            Permanent permanent = game.getPermanent(target.getFirstTarget());
            if (permanent != null) {
                permanent.sacrifice(source.getSourceId(), game);
                return true;
            }
        } else {
            player.damage(7, source.getSourceId(), game, false, true);
            return true;
        }
        return false;
    }
}
