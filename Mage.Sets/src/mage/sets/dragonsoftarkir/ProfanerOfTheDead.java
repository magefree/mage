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
package mage.sets.dragonsoftarkir;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ExploitCreatureTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.ExploitAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SetTargetPointer;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.Filter;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ToughnessPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class ProfanerOfTheDead extends CardImpl {

    public ProfanerOfTheDead(UUID ownerId) {
        super(ownerId, 70, "Profaner of the Dead", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{U}");
        this.expansionSetCode = "DTK";
        this.subtype.add("Naga");
        this.subtype.add("Wizard");
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Exploit
        this.addAbility(new ExploitAbility());

        // When Profaner of the Dead exploits a creature, return to their owners' hands all creatures your opponents control with toughness less than the exploited creature's toughness.
        this.addAbility(new ExploitCreatureTriggeredAbility(new ProfanerOfTheDeadReturnEffect(), false, SetTargetPointer.PERMANENT));

    }

    public ProfanerOfTheDead(final ProfanerOfTheDead card) {
        super(card);
    }

    @Override
    public ProfanerOfTheDead copy() {
        return new ProfanerOfTheDead(this);
    }
}

class ProfanerOfTheDeadReturnEffect extends OneShotEffect {

    public ProfanerOfTheDeadReturnEffect() {
        super(Outcome.ReturnToHand);
        staticText = "return to their owners' hands all creatures your opponents control with toughness less than the exploited creature's toughness";
    }

    public ProfanerOfTheDeadReturnEffect(final ProfanerOfTheDeadReturnEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent exploitedCreature = game.getPermanentOrLKIBattlefield(getTargetPointer().getFirst(game, source));
        if (controller != null && exploitedCreature != null) {
            FilterCreaturePermanent filter = new FilterCreaturePermanent();
            filter.add(new ControllerPredicate(TargetController.OPPONENT));
            filter.add(new ToughnessPredicate(Filter.ComparisonType.LessThan, exploitedCreature.getToughness().getValue()));
            for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game)) {
                controller.moveCardToHandWithInfo(permanent, source.getSourceId(), game, Zone.BATTLEFIELD);
            }
            return true;
        }
        return false;

    }

    @Override
    public ProfanerOfTheDeadReturnEffect copy() {
        return new ProfanerOfTheDeadReturnEffect(this);
    }
}
